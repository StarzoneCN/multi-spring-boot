package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.util.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("auth")
public class AuthController {


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
        /*if(redisService.exists("key_rand_" + session.getId() )){
            redisService.del("key_rand_" + session.getId());
        }
        redisService.set("key_rand_" + session.getId(), session.getAttribute("rand").toString(), 300);*/

        // 输出图象到页面
        try {
            ImageIO.write(vo.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace(); // todo 完善：添加日志
        }
    }
}
