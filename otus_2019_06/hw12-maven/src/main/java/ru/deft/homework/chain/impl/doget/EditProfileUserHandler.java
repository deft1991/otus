package ru.deft.homework.chain.impl.doget;

import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.chain.BaseHandler;
import ru.deft.homework.processor.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/12/19
 */
public class EditProfileUserHandler extends BaseHandler {
    private static final String USERS_EDIT_PAGE_TEMPLATE = "usersEdit.ftl";
    private final DbUserService userService;

    public EditProfileUserHandler(DbUserService userService, TemplateProcessor templateProcessor) {
        super(templateProcessor);
        this.userService = userService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getRequestURI().contains("/edit_profile") && req.getParameter("userId") != null) {
            try {
                Long userId = Long.valueOf(req.getParameter("userId"));
                User user = userService.getById(userId);
                Map<String, Object> pageParams = new HashMap<>();
                pageParams.put("user", user);

                prepareResponce(resp, pageParams, USERS_EDIT_PAGE_TEMPLATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            checkNext(req, resp);
        }
    }
}
