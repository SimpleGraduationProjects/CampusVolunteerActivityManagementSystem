package com.collegevol.interceptor;

import com.alibaba.fastjson.JSON;
import com.collegevol.utils.RedisUtils;
import com.collegevol.vo.ReturnData;
import com.collegevol.vo.StatusCode;
import com.collegevol.vo.VariableParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Slf4j
public class RedisSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
//        String sessionId="";
//        Enumeration<String> headers= request.getHeaderNames();
//        while (headers.hasMoreElements()){
//            String name= headers.nextElement();
//            String value=request.getHeader(name);
//            System.out.println(value+":"+name);
//        }

        String sessionId=null;
        String Url=request.getQueryString();
        if(Url!=null&&Url.contains("=")){
            sessionId=Url.substring(Url.lastIndexOf("=")+1);
        }
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        List<MultipartFile> files = multipartRequest.getFiles("files");
        if(sessionId!=null){
            String value = sessionId;
            Map<String, String> loginMap = RedisUtils.getTemplate().opsForHash().entries(VariableParam.LOGIN_HASH);
            if (loginMap != null) {
                for (String key : loginMap.keySet()) {
                    if (loginMap.get(key) != null && loginMap.get(key).equals(value)) {
                        if (key != null) {
                            request.getSession().setAttribute("stuId", key);
                            return true;
                        }
                    }
                }
            }
        }else if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    String value = cookie.getValue();
                    Map<String, String> loginMap = RedisUtils.getTemplate().opsForHash().entries(VariableParam.LOGIN_HASH);
                    if (loginMap != null) {
                        for (String key : loginMap.keySet()) {
                            if (loginMap.get(key) != null && loginMap.get(key).equals(value)) {
                                if (key != null) {
                                    request.getSession().setAttribute("stuId", key);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(new ReturnData(StatusCode.NEED_LOGIN, "用户未登录!", "")));
        } catch (IOException e) {
            log.error("login error" + e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
