package com.imooc.mall.fillter;

import com.imooc.mall.common.Constant;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理员校验过滤器
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 */
public class AdminFilter implements Filter {
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest  request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute(Constant.IMOOC_MALL_USER);
        if(currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": {" + null +"}\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }
        boolean adminRole = userService.checkAdminRole(currentUser);
        //校验是否是管理员
        if(!adminRole){
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10009,\n" +
                    "    \"msg\": \"NEED_ADMIN\",\n" +
                    "    \"data\": {null}\n" +
                    "}");
            out.flush();
            out.close();
        }
        else{
            filterChain.doFilter(servletRequest , servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
