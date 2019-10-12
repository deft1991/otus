package ru.deft.homework.servlet.login;

import lombok.RequiredArgsConstructor;
import ru.deft.homework.api.model.User;
import ru.deft.homework.processor.TemplateProcessor;
import ru.deft.homework.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/9/19
 */
@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private static final String LOGIN_PAGE_TEMPLATE = "login.ftl";
    private static final String SUCCESS_LOGIN_PAGE_TEMPLATE = "successLoginPage.ftl";
    private static final int EXPIRE_INTERVAL = 20; // seconds

    private final UserService userService;

    private final TemplateProcessor templateProcessor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageParams = new HashMap<>();
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User authenticateUser = userService.authenticate(name, password);
        if (authenticateUser != null) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(60);

            Map<String, Object> params = new HashMap<>();
            params.put("user", authenticateUser);
            params.put("sessionId", req.getSession().getId());


            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage(SUCCESS_LOGIN_PAGE_TEMPLATE, params));
            resp.setStatus(HttpServletResponse.SC_OK);

            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("adminUser", String.valueOf(authenticateUser.getId()));
            userName.setMaxAge(30 * 60);
            resp.addCookie(userName);
        } else {
            resp.setStatus(403);
            resp.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE));
        }
    }
}
