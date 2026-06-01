package com.finance.controller;

import com.finance.beans.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/dashboard/*")
public class SecurityFilter implements Filter {
    @Override public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Session expired.");
            return;
        }

        User user = (User) session.getAttribute("authenticatedUser");
        String uri = httpRequest.getRequestURI();

        if (uri.contains("/dashboard/analytics") && user.getRole().name().equals("VIEWER")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/unauthorized.jsp");
            return;
        }

        if (uri.contains("/dashboard/record-create") && !user.getRole().name().equals("ADMIN")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/unauthorized.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override public void destroy() {}
}