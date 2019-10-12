package ru.deft.homework.chain.impl.doget;

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
public class BaseUserHandler extends BaseHandler {
    private static final String BASE_USER_PAGE_TEMPLATE = "successLoginPage.ftl";


    public BaseUserHandler(TemplateProcessor templateProcessor) {
        super(templateProcessor);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) {

        if (req.getRequestURI().contains("/user")) {
            try {
                System.out.println("in UserServlet -- doGet ");
                Map<String, Object> pageParams = new HashMap<>();
                pageParams.put("name", "deft");

                prepareResponce(resp, pageParams, BASE_USER_PAGE_TEMPLATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            checkNext(req, resp);
        }
    }
}
