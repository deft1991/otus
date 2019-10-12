package ru.deft.homework.servlet.login;

import lombok.RequiredArgsConstructor;
import ru.deft.homework.processor.TemplateProcessor;
import ru.deft.homework.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private static final String HELLO_PAGE_TEMPLATE = "hello.ftl";
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

        if (userService.authenticate(name, password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(60);

            Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("name", "deft");
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage(HELLO_PAGE_TEMPLATE, pageParams));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(403);
            resp.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE));
        }
    }
}
