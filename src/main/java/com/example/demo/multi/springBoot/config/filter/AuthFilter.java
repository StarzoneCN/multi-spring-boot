package com.example.demo.multi.springBoot.config.filter;

import cn.com.bluemoon.common.user.po.UserInfo;
import com.example.demo.multi.springBoot.util.StringUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.example.demo.multi.springBoot.constant.StringConstants.SESSION_PREFIX_OF_REDIS_KEY;

@Component
@PropertySource("classpath:application.properties")
public class AuthFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @Value("${spring.profiles.active}")
    private String environment;
    @Autowired
    @Qualifier("stringValueOperations")
    private ValueOperations valueOperations;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession session = httpServletRequest.getSession();
        if (StringUtils.isBlank(environment) || "dev".equals(environment)){
            /*dev环境*/
            if (session != null && session.getAttribute("user") != null){
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            } else if (httpServletRequest.getHeader("x-requested-with") != null
                    && httpServletRequest.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                /*Ajax异步请求*/
                LOGGER.warn("---session超时,请重新登录---");
                httpServletResponse.setHeader("sessionstatus", "timeout");
                return;
            }
            httpServletResponse.setStatus(401);
            return;
        }


        final String requestUri = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getQueryString() != null
                && httpServletRequest.getQueryString().indexOf("token") != -1 ?
                httpServletRequest.getParameter("token") : null;
        if (StringUtils.isBlank(token) && session != null
                && StringUtils.isNotBlank((String)(session.getAttribute("token")))){
            token = (String)session.getAttribute("token");
        }
        boolean isLogin = false;
        if (StringUtils.isBlank(token)){
            /*从cookie中获取token*/
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null && cookies.length > 0){
                Optional<String> cookieTokenOptional = Arrays.stream(cookies)
                        .filter(c -> "token".equals(c.getName()) && StringUtils.isNotBlank(c.getValue()))
                        .map(c -> c.getValue())
                        .findFirst();
                if (cookieTokenOptional.isPresent()){
                    token = cookieTokenOptional.get();
                    isLogin = true;
                }
            }
        }
        if (StringUtils.isBlank(token)){
            final String refererUri = httpServletRequest.getHeader("Referer");
            if (StringUtils.isNotBlank(refererUri) && refererUri.indexOf("token") > -1){
                final String afterParamTokenStr = refererUri.substring(refererUri.indexOf("token")+6);
                if (afterParamTokenStr.indexOf("&") > -1) {
                    token = afterParamTokenStr.substring(0, afterParamTokenStr.indexOf("&"));
                } else {
                    token = afterParamTokenStr;
                }
            }
        }
        if (StringUtils.isBlank(token)){
            if (httpServletRequest.getHeader("x-requested-with") != null
                    && httpServletRequest.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                /*Ajax异步请求*/
                LOGGER.warn("---session超时,请重新登录---");
                httpServletResponse.setHeader("sessionstatus", "timeout");
            } else {
                httpServletResponse.setStatus(401);
            }
            return;
        }
        LOGGER.info("Token = " + token);
        if (redisTemplate.hasKey(SESSION_PREFIX_OF_REDIS_KEY + token)){
            if (isLogin) {
                redisTemplate.expire(SESSION_PREFIX_OF_REDIS_KEY + token, 604800L, TimeUnit.SECONDS);
            } else {
                redisTemplate.expire(SESSION_PREFIX_OF_REDIS_KEY + token, 7200L, TimeUnit.SECONDS);
            }

            JSONObject jsonToken = JSONObject.fromObject(
                    (String) valueOperations.get(SESSION_PREFIX_OF_REDIS_KEY + token));
            UserInfo user = (UserInfo)JSONObject.toBean(jsonToken.getJSONObject("user"), UserInfo.class);
            session.setAttribute("user", user);
            session.setAttribute("roleCode", jsonToken.getString("roleCode"));
            session.setAttribute("companyNum", jsonToken.getString("companyNum"));
            session.setAttribute("token", token);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }else {
            if (httpServletRequest.getHeader("x-requested-with") != null
                    && httpServletRequest.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                /*Ajax异步请求*/
                LOGGER.warn("---session超时,请重新登录---");
                httpServletResponse.setHeader("sessionstatus", "timeout");
            } else {
                httpServletResponse.setStatus(401);
            }

            return;
        }
    }

    @Override
    public void destroy() {

    }
}
