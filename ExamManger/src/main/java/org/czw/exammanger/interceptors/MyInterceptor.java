package org.czw.exammanger.interceptors;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiResponse;
import org.czw.exammanger.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        response.setCharacterEncoding("utf-8");
//        String token = request.getParameter("sessionValue");
//        request.getHeader("tokenheader");
        //Cookie cookie=getCookieByName(request,"_COOKIE_NAME");
        //如果已经登录，不拦截
        String token =request.getHeader("token");
        if (null != token) {
            //验证token是否正确
            boolean result = JwtUtil.verify(token);
            System.out.println("是否通过拦截器:" + result);
            if (!result) {
                return false;
            }
            return true;
        }
        //如果没有登录，则跳转到登录界面
        else {
            //重定向 第一种 调用控制器 方法
//            response.sendRedirect(request.getContextPath() + "/toLogin");
            //重定向 第二种 重定向方法
            //            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            //            System.out.println(request.getContextPath());
            return false;
            /**
             * 以下是为了登录成功后返回到刚刚的操作，不跳到主界面
             * 实现：通过将请求URL保存到session的beforePath中，然后在登录时判断beforePath是否为空
             */
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }
    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 返回信息给客户端
     *
     * @param response
     * @param out
     * @param apiResponse
     */
    private void responseMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter out, ApiResponse apiResponse) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        out.print(JSONObject.toJSONString(apiResponse));
        out.flush();
        out.close();
    }
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        System.out.println("进入拦截器。。。。。");
//        String loginStaus = (String) request.getSession().getAttribute("loginStaus");
//
//        System.out.println("loginStaus:"+loginStaus);
//        if (StrUtil.isNotBlank(loginStaus)&&"1".equals(loginStaus)){
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Methods","*");
    //        // 设置拦截器信息配置
//        // 1、获取远程应用的host信息：localhost:8080
//        String origin = request.getHeader("Origin");
//        // 2、设置这个host信息允许跨域
//        response.setHeader("Access-Control-Allow-Origin", origin);
//        // 3、这些请求类型都是允许跨域的
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        // 4、最大的链接保存时间是3600秒
//        response.setHeader("Access-Control-Max-Age", "3600");
//        // 5、允许使用session，默认跨域不允许使用session
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        // 6、跨域的时候请求头信息允许使用content-type
//        response.setHeader("Access-Control-Allow-Headers", "content-type");
//        return true;
//    }
}
