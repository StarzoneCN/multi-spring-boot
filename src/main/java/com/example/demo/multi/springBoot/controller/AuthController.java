package com.example.demo.multi.springBoot.controller;

import cn.com.bluemoon.common.user.po.UserInfo;
import cn.com.bluemoon.service.user.service.SsoService;
import com.example.demo.multi.springBoot.dto.LoginDto;
import com.example.demo.multi.springBoot.util.AuthUtils;
import com.example.demo.multi.springBoot.util.RedisUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public Map<String, Object> login(HttpSession session, @RequestBody LoginDto loginDto){
        String responseMsg = "网络繁忙，请稍后再试";
        int responseCode = -1;
        Boolean isSuccess = Boolean.FALSE;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("responseMsg", responseMsg);
        result.put("responseCode", responseCode);
        result.put("isSuccess", isSuccess);

        if (StringUtils.isBlank(loginDto.getAccount())) {
            result.put("responseMsg", "用户名不能为空");
            result.put("responseCode", 2202);
            return result;
        }

        if (StringUtils.isBlank(loginDto.getPassword())) {
            result.put("responseMsg", "密码不能为空");
            result.put("responseCode", 2203);
            return result;
        }

        if (StringUtils.isBlank(loginDto.getRand())) {
            result.put("responseMsg", "验证码不能为空");
            result.put("responseCode", 2204);
            return result;
        }

        if (!RedisUtil.exists("key_rand_" + session.getId())) {
            result.put("responseMsg", "验证码已失效，请重新登录");
            result.put("responseCode", 1302);
            return result;
        }

        // 从redis中获取当前回话的验证码
        String randCode = RedisUtil.get("key_rand_" + session.getId());
        // String randCode = (String) session.getAttribute("rand");
        if (!loginDto.getRand().equals(randCode)) {
            result.put("responseMsg", "验证码错误");
            result.put("responseCode", 1301);
            return result;
        }

        try {
            JSONObject obj = new JSONObject();
            obj.put("account", loginDto.getAccount());
            obj.put("password", loginDto.getPassword());
            String res = ssoService.ssoLogin(obj);
            JSONObject param = JSONObject.fromObject(res);
            if ((Boolean) param.get("isSuccess") == true) {
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
            }
            result.put("isSuccess", param.get("isSuccess"));
            result.put("responseMsg", param.get("responseMsg"));
            result.put("responseCode", param.get("responseCode"));

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
