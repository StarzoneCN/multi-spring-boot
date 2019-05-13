//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo.multi.springBoot.filter;

import cn.com.bluemoon.common.user.po.UserInfo;
import cn.com.bluemoon.portalClient.util.HttpProperties;
import com.example.demo.multi.springBoot.util.RedisUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static Logger logger = Logger.getLogger(AuthFilter.class);
    private String excludeUrl;
    private String[] excludeUrlArray;
    private FilterConfig config;

    public AuthFilter() {
    }

    public void destroy() {
        this.config = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session = req.getSession();
        boolean isLogin = false;
        String requestUri = req.getRequestURI();
        String token = req.getQueryString() != null && req.getQueryString().indexOf("token") != -1 ? req.getParameter("token") : null;
        String cookieToken = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for(int i = 0; i < cookies.length; ++i) {
                Cookie cookie = cookies[i];
                if ("token".equals(cookie.getName()) && !StringUtils.isEmpty(cookie.getValue())) {
                    cookieToken = cookie.getValue();
                    isLogin = true;
                }
            }
        }

        if (StringUtils.isEmpty(token)) {
            token = session != null && session.getAttribute("token") != null ? (String)session.getAttribute("token") : "";
            if (StringUtils.isEmpty(token) && req != null && !StringUtils.isEmpty(cookieToken)) {
                token = cookieToken;
            }
        }

        if (StringUtils.isBlank(token)){
            String refererUri = req.getHeader("Referer");
            System.out.println("refererUri = " + refererUri);
            if (StringUtils.isNotBlank(refererUri) && refererUri.indexOf("token") > -1){
                String afterParamTokenStr = refererUri.substring(refererUri.indexOf("token")+6);
                if (afterParamTokenStr.indexOf("&") > -1) {
                    token = afterParamTokenStr.substring(0, afterParamTokenStr.indexOf("&"));
                } else {
                    token = afterParamTokenStr;
                }
            }
        }
        System.out.println("token = " + token);

        String sysPattern = HttpProperties.getVal("sys_pattern");
        if (sysPattern == null || "".equals(sysPattern)) {
            sysPattern = "develop";
        }

        boolean isExcludeUrl;
        if ("develop".equals(sysPattern)) {
            isExcludeUrl = false;

            for(int i = 0; i < this.excludeUrlArray.length; ++i) {
                if (requestUri.indexOf("/js/") >= 0 || requestUri.indexOf("/images/") >= 0 || requestUri.indexOf("/css/") >= 0) {
                    isExcludeUrl = true;
                    break;
                }

                if (requestUri.indexOf(req.getContextPath() + this.excludeUrlArray[i]) >= 0) {
                    isExcludeUrl = true;
                    break;
                }
            }

            if (isExcludeUrl) {
                chain.doFilter(req, response);
            } else if (session != null && session.getAttribute("user") != null) {
                chain.doFilter(req, response);
            }  else if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                logger.warn("---session超时,请重新登录---");
                res.setHeader("sessionstatus", "timeout");
            } else {
                logger.warn("---[" + requestUri + "]  must be login---");
                res.setStatus(401);
            }
        } else {
            isExcludeUrl = false;

            for(int i = 0; i < this.excludeUrlArray.length; ++i) {
                if (requestUri.indexOf("/js/") >= 0 || requestUri.indexOf("/images/") >= 0 || requestUri.indexOf("/css/") >= 0) {
                    isExcludeUrl = true;
                    break;
                }

                if (requestUri.indexOf(req.getContextPath() + this.excludeUrlArray[i]) >= 0 && !"/login.jsp".equals(this.excludeUrlArray[i])) {
                    isExcludeUrl = true;
                    break;
                }
            }

            if (isExcludeUrl) {
                chain.doFilter(req, response);
            } else {
                try {
                    if (token != null && !"".equals(token)) {
                        if (RedisUtil.exists("portal_token_" + token)) {
                            if (isLogin) {
                                RedisUtil.setTimeOut("portal_token_" + token, 604800);
                            } else {
                                RedisUtil.setTimeOut("portal_token_" + token, 7200);
                            }

                            String tokenString = RedisUtil.get("portal_token_" + token);
                            JSONObject jsonToken = JSONObject.fromObject(tokenString);
                            UserInfo user = (UserInfo)JSONObject.toBean(jsonToken.getJSONObject("user"), UserInfo.class);
                            session.setAttribute("user", user);
                            String roleCode = jsonToken.getString("roleCode");
                            session.setAttribute("roleCode", roleCode);
                            String companyNum = jsonToken.getString("companyNum");
                            session.setAttribute("companyNum", companyNum);
                            session.setAttribute("token", token);
                            chain.doFilter(req, response);
                            return;
                        } else {
                            if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                                logger.warn("---session超时,请重新登录---");
                                res.setHeader("sessionstatus", "timeout");
                            } else {
                                res.setStatus(401);
                            }

                            return;
                        }
                    } else {
                        if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                            logger.warn("---session超时,请重新登录---");
                            res.setHeader("sessionstatus", "timeout");
                        } else {
                            res.setStatus(401);
                        }

                        return;
                    }
                } catch (Exception var21) {
                    var21.printStackTrace();
                    throw new ServletException(var21);
                }
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {
        this.excludeUrl = HttpProperties.getVal("excludeUrl");
        if (StringUtils.isNotEmpty(this.excludeUrl)) {
            this.excludeUrlArray = this.excludeUrl.split(",");
        }

        this.config = config;
    }
}
