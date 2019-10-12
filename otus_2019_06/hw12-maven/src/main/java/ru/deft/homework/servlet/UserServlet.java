package ru.deft.homework.servlet;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.processor.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * Created by sgolitsyn on 10/7/19
 */
@RequiredArgsConstructor
public class UserServlet extends HttpServlet {
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private static final String TIMER_PAGE_TEMPLATE = "hello.ftl";
    private static final String USERS_PAGE_TEMPLATE = "usersAll.ftl";
    private static final String USERS_EDIT_PAGE_TEMPLATE = "usersEdit.ftl";
    private static final String USERS_SAVE_PAGE_TEMPLATE = "userSave.ftl";
    private final DbUserService userService;
    private final Gson gson;
    private final TemplateProcessor templateProcessor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().contains("/all")) {
            List<User> users = userService.findAll();
            Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("users", users);
            prepareResponce(resp, pageParams, USERS_PAGE_TEMPLATE);
        } else if (req.getRequestURI().contains("/edit_profile") && req.getParameter("userId") != null) {
            Long userId = Long.valueOf(req.getParameter("userId"));
            User user = userService.getById(userId);
            Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("user", user);

            prepareResponce(resp, pageParams, USERS_EDIT_PAGE_TEMPLATE);

        } else if(req.getRequestURI().contains("/create_profile")) {
            prepareResponce(resp, null, USERS_SAVE_PAGE_TEMPLATE);
        }

        else {
            System.out.println("in UserServlet -- doGet ");
            Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("name", "deft");

            prepareResponce(resp, pageParams, TIMER_PAGE_TEMPLATE);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in UserServlet -- doPost");
        if (req.getRequestURI().contains("/update_profile")) {
            User user = userService.getById(Long.valueOf(req.getParameter("userId"))); //todo add id

            user.setName(req.getParameter("userName"));
            user.setPassword(req.getParameter("userPassword"));
            userService.save(user);
            List<User> users = userService.findAll();
            Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("users", users);
            prepareResponce(resp, pageParams, USERS_PAGE_TEMPLATE);
        } else if(req.getRequestURI().contains("/create_profile")){
            User user = new User(req.getParameter("userName"), req.getParameter("userPassword"));
            userService.save(user);
            List<User> users = userService.findAll();
            Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("users", users);
            prepareResponce(resp, pageParams, USERS_PAGE_TEMPLATE);
        }
    }

    private void prepareResponce(HttpServletResponse resp, Map<String, Object> pageParams, String usersPageTemplate) throws IOException {
        if (pageParams != null && !pageParams.isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage(usersPageTemplate, pageParams));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(templateProcessor.getPage(usersPageTemplate));
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

}
