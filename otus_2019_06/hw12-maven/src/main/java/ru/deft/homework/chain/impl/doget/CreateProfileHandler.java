package ru.deft.homework.chain.impl.doget;

import ru.deft.homework.chain.BaseHandler;
import ru.deft.homework.processor.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Created by sgolitsyn on 10/12/19
 */
public class CreateProfileHandler extends BaseHandler {
    private static final String USERS_SAVE_PAGE_TEMPLATE = "userSave.ftl";

    public CreateProfileHandler(TemplateProcessor templateProcessor) {
        super(templateProcessor);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getRequestURI().contains("/create_profile")) {
            try {
                prepareResponce(resp, null, USERS_SAVE_PAGE_TEMPLATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            checkNext(req, resp);
        }
    }
}
