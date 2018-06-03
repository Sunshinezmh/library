package com.dmsdbj.library.controller.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ApiOriginFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        //授权给指定的应用；当图书馆前端工程发布时，建议将此处改为图书馆前端工程应用地址
        //res.addHeader("Access-Control-Allow-Origin","http://www.example.com");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTION");
        res.addHeader("Access-Control-Allow-Headers", "Accept, Origin,X-Requested-With, Content-Type, Last-Modified");
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
}