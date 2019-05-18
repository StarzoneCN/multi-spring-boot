package com.example.demo.multi.springBoot.controller;

import cn.com.bluemoon.common.user.po.UserInfo;
import cn.com.bluemoon.service.user.service.SsoService;
import com.example.demo.multi.springBoot.config.CustomizedMessageSource;
import com.example.demo.multi.springBoot.constant.ResponseCodeEnum;
import com.example.demo.multi.springBoot.dto.AccountAndMobileDto;
import com.example.demo.multi.springBoot.dto.LoginDto;
import com.example.demo.multi.springBoot.dto.ResetPassDto;
import com.example.demo.multi.springBoot.util.AuthUtils;
import com.example.demo.multi.springBoot.util.RedisUtil;
import com.example.demo.multi.springBoot.util.StringUtils;
import com.example.demo.multi.springBoot.vo.CommonResponseVo;
import net.sf.json.JSONObject;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;

import static com.example.demo.multi.springBoot.constant.StringConstants.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Reference(url = "192.168.240.18:20881")
    private SsoService ssoService;
    @Autowired
    private CustomizedMessageSource messageSource;


    @GetMapping("variCode")
    public void varificationCode(HttpServletRequest request, HttpServletResponse response) {

        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        AuthUtils.VarificationCodeAndImageVo vo = AuthUtils.createVarificationCode();

        // 将认证码存入SESSION
        HttpSession session = request.getSession();
        //将生成的验证码根据sessionid存入到redis的缓存中，避免由于nginx轮询造成验证码不匹配的问题
        if(RedisUtil.exists(RAND_PREFIX_OF_REDIS_KEY + session.getId())){
            RedisUtil.del(RAND_PREFIX_OF_REDIS_KEY + session.getId());
        }
        RedisUtil.set(RAND_PREFIX_OF_REDIS_KEY + session.getId(), vo.getCode(), 300);

        // 输出图象到页面
        try {
            ImageIO.write(vo.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error(SYS_EXCEPTION_WITH_COLON + e.getMessage());
        }
    }

    @PostMapping("login")
    public CommonResponseVo login(HttpSession session, @RequestBody LoginDto loginDto){
        CommonResponseVo responseVo = new CommonResponseVo();
        if (StringUtils.isBlank(loginDto.getAccount())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(messageSource.getMessage("param.cannot.null", "account"));
            return responseVo;
        }

        if (StringUtils.isBlank(loginDto.getPassword())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(messageSource.getMessage("param.cannot.null", "password"));
            return responseVo;
        }

        if (StringUtils.isBlank(loginDto.getRand())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(messageSource.getMessage("param.cannot.null", "rand"));
            return responseVo;
        }

        if (!RedisUtil.exists(RAND_PREFIX_OF_REDIS_KEY + session.getId())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(messageSource.getMessage("expired.verify.code"));
            return responseVo;
        }

        // 从redis中获取当前回话的验证码
        String randCode = RedisUtil.get(RAND_PREFIX_OF_REDIS_KEY + session.getId());
        if (!loginDto.getRand().equals(randCode)) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(messageSource.getMessage("bad.verify.code"));
            return responseVo;
        }

        try {
            JSONObject obj = new JSONObject();
            obj.put("account", loginDto.getAccount());
            obj.put("password", loginDto.getPassword());
            String res = ssoService.ssoLogin(obj);
            JSONObject param = JSONObject.fromObject(res);
            if ((Boolean) param.get(SSO_RESPONSE_SUCCESS_FLAG) == true) {
                // 用户信息存入session
                String userStr = ssoService.getUserInfo(obj);
                JSONObject userJson = JSONObject.fromObject(userStr);
                System.out.println(userJson);
                UserInfo user = (UserInfo) JSONObject.toBean(userJson.getJSONObject("user"), UserInfo.class);
                session.setAttribute("user", user);

                JSONObject staffNumObj = new JSONObject();
                staffNumObj.put("staffNum", user.getAccount());
                // 角色信息存入session TODO 本地角色管理
                /*Set<Integer> roleSet = authService.getRoleListByUserId(user.getAccount());
                session.setAttribute("roles", roleSet == null ? new HashSet<Integer>() : roleSet);

                String roleCode = authService.getRoleCodeList(user.getAccount());
                session.setAttribute("roleCode", roleCode == null ? "" : roleCode);*/
                responseVo.setCode(ResponseCodeEnum.SUCCESS);
                return responseVo;
            }
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            responseVo.setMsg(param.getString(SSO_RESPONSE_MSG_FLAG));
            return responseVo;

        } catch (Exception e) {
            e.printStackTrace();
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            return responseVo;
        }
    }

    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.POST)
    public CommonResponseVo<Integer> getVerifyCode(@RequestBody AccountAndMobileDto aamDto) {
        if (StringUtils.isBlank(aamDto.getAccount()) || StringUtils.isBlank(aamDto.getMobileNo())){
            CommonResponseVo<Integer> responseVo = new CommonResponseVo<Integer>(){{
                this.setCode(ResponseCodeEnum.BAD_PARAMS);
                this.setMsg(ResponseCodeEnum.BAD_PARAMS.msg());
            }};
            return responseVo;
        }
        JSONObject params = new JSONObject();
        params.put("account", aamDto.getAccount());
        params.put("mobileNo", aamDto.getMobileNo());
        CommonResponseVo responseVo = new CommonResponseVo();
        try {
            String res = ssoService.getVerifyCode(params);
            JSONObject param = JSONObject.fromObject(res);
            if (param.getBoolean(SSO_RESPONSE_SUCCESS_FLAG)){
                responseVo.setCode(ResponseCodeEnum.SUCCESS);
                responseVo.setData(param.getInt("time"));
                return responseVo;
            }
            ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.instance(Integer.parseInt(params.getString(SSO_RESPONSE_CODE_FLAG)));
            responseVo.setCode(responseCodeEnum);
            responseVo.setMsg(responseCodeEnum.msg());
        } catch (EnumConstantNotPresentException e) {
            LOGGER.warn(MessageFormat.format(THE_RESPONESE_CODE_IS_NOT_EXISTS, params.getString(SSO_RESPONSE_CODE_FLAG)), e);
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            responseVo.setMsg(params.getString(SSO_RESPONSE_SUCCESS_FLAG));
        } catch (Exception e) {
            LOGGER.error(SYS_EXCEPTION_WITH_COLON, e);
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            responseVo.setMsg(ResponseCodeEnum.FAIL_2_EXE.msg());
        }
        return responseVo;
    }

    @PostMapping("resetPass")
    public CommonResponseVo resetPass(@RequestBody ResetPassDto resetPassDto){
        CommonResponseVo responseVo = new CommonResponseVo();
        if (StringUtils.isBlank(resetPassDto.getMobileNO())
                || StringUtils.isBlank(resetPassDto.getVerifyCode())
                || StringUtils.isBlank(resetPassDto.getNewPassword())) {
            responseVo.setCode( ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg( ResponseCodeEnum.BAD_PARAMS.msg());
            return responseVo;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("verifyCode", resetPassDto.getVerifyCode());
            params.put("mobileNo", resetPassDto.getMobileNO());
            params.put("newPassword", resetPassDto.getNewPassword());
            String res = ssoService.resetPassword(params);

            JSONObject param = JSONObject.fromObject(res);
            if (param.getBoolean(SSO_RESPONSE_SUCCESS_FLAG)){
                responseVo.setCode(ResponseCodeEnum.SUCCESS);
                return responseVo;
            }

            try {
                ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.instance(param.getInt(SSO_RESPONSE_CODE_FLAG));
                responseVo.setCode(responseCodeEnum);
                responseVo.setMsg(responseCodeEnum.msg());
            } catch (EnumConstantNotPresentException e) {
                responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
                responseVo.setMsg(param.getString(SSO_RESPONSE_MSG_FLAG));
            }
        } catch (Exception e) {
            LOGGER.error(SYS_EXCEPTION_WITH_COLON, e);
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            responseVo.setMsg(ResponseCodeEnum.FAIL_2_EXE.msg());
        }
        return responseVo;
    }
}
