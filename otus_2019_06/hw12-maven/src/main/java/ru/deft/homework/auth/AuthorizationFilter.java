package ru.deft.homework.auth;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * Created by sgolitsyn on 10/9/19
 */
@WebFilter("/admin/*")
public class AuthorizationFilter implements Filter {

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("userAdmin") != null);
        String loginUrl =  req.getContextPath() + "/admin/login";
        boolean isLoginRequest = req.getRequestURI().equals(loginUrl);
        boolean isLoginPage = req.getRequestURI().endsWith("login.ftl");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the admin is already logged in and he's trying to login again
            // then forwards to the admin's homepage
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/");
            dispatcher.forward(req, resp);

        } else if (isLoggedIn || isLoginRequest) {
            // continues the filter chain
            // allows the request to reach the destination
            filterChain.doFilter(req, resp);

        } else {
            // the admin is not logged in, so authentication is required
            // forwards to the Login page
            RequestDispatcher dispatcher = req.getRequestDispatcher("login");
            dispatcher.forward(req, resp);

        }

        String requestURI = req.getRequestURI();
        System.out.println("Requested Resource:" + requestURI);

        if (session == null) {
            resp.setStatus(403);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
