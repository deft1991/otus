package ru.deft.homework.chain.impl.doget;

import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.chain.BaseHandler;
import ru.deft.homework.processor.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/12/19
 */
public class AllUsersHandler extends BaseHandler {
    private static final String USERS_PAGE_TEMPLATE = "usersAll.ftl";
    private final DbUserService userService;

    public AllUsersHandler(DbUserService userService, TemplateProcessor templateProcessor) {
        super(templateProcessor);
        this.userService = userService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getRequestURI().contains("/all")) {
            try {
                List<User> users = userService.findAll();
                Map<String, Object> pageParams = new HashMap<>();
                pageParams.put("users", users);
                prepareResponce(resp, pageParams, USERS_PAGE_TEMPLATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            checkNext(req, resp);
        }
    }
}
