package com.example.demo.multi.springBoot.controller;

import cn.com.bluemoon.common.user.po.UserInfo;
import cn.com.bluemoon.service.user.service.SsoService;
import com.example.demo.multi.springBoot.constant.ResponseCodeEnum;
import com.example.demo.multi.springBoot.dto.AccountAndMobileDto;
import com.example.demo.multi.springBoot.dto.LoginDto;
import com.example.demo.multi.springBoot.dto.ResetPassDto;
import com.example.demo.multi.springBoot.util.AuthUtils;
import com.example.demo.multi.springBoot.util.RedisUtil;
import com.example.demo.multi.springBoot.vo.CommonResponseVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.multi.springBoot.constant.StringConstants.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Reference
    private SsoService ssoService;


    @GetMapping("variCode")
    public void varificationCode(HttpServletRequest request, HttpServletResponse response) {

        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        AuthUtils.VarificationCodeAndImageVo vo = AuthUtils.createVarificationCode();

        // 将认证码存入SESSION
        HttpSession session = request.getSession();
        session.setAttribute("rand", vo.getCode());

        //将生成的验证码根据sessionid存入到redis的缓存中，避免由于nginx轮询造成验证码不匹配的问题
        if(RedisUtil.exists("key_rand_" + session.getId())){
            RedisUtil.del("key_rand_" + session.getId());
        }
        RedisUtil.set("key_rand_" + session.getId(), session.getAttribute("rand").toString(), 300);

        // 输出图象到页面
        try {
            ImageIO.write(vo.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace(); // todo 完善：添加日志
        }
    }

    @PostMapping("login")
    public CommonResponseVo login(HttpSession session, @RequestBody LoginDto loginDto){
        CommonResponseVo responseVo = new CommonResponseVo();
        if (StringUtils.isBlank(loginDto.getAccount())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(" ");  // todo 配置messageResource
            return responseVo;
        }

        if (StringUtils.isBlank(loginDto.getPassword())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(" ");  // todo 配置messageResource
            return responseVo;
        }

        if (StringUtils.isBlank(loginDto.getRand())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg(" ");  // todo 配置messageResource
            return responseVo;
        }

        if (!RedisUtil.exists("key_rand_" + session.getId())) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg("验证码已失效，请重新登录");  // todo 配置messageResource
            return responseVo;
        }

        // 从redis中获取当前回话的验证码
        String randCode = RedisUtil.get("key_rand_" + session.getId());
        // String randCode = (String) session.getAttribute("rand");
        if (!loginDto.getRand().equals(randCode)) {
            responseVo.setCode(ResponseCodeEnum.BAD_PARAMS);
            responseVo.setMsg("验证码错误");
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
            e.printStackTrace(); // todo 添加日志
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            responseVo.setMsg(params.getString(SSO_RESPONSE_SUCCESS_FLAG));
        } catch (Exception e) {
            e.printStackTrace(); // todo 添加日志
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
            e.printStackTrace(); // todo 日志
            responseVo.setCode(ResponseCodeEnum.FAIL_2_EXE);
            responseVo.setMsg(ResponseCodeEnum.FAIL_2_EXE.msg());
        }
        return responseVo;
    }
}
