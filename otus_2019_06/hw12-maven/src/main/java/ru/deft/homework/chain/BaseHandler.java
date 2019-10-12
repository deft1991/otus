package ru.deft.homework.chain;

import ru.deft.homework.processor.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/12/19
 */
public abstract class BaseHandler implements Handler {
    private final TemplateProcessor templateProcessor;
    private Handler next;

    protected BaseHandler(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void setNext(Handler handler) {
        this.next = handler;
    }

    @Override
    public abstract void handle(HttpServletRequest req, HttpServletResponse resp);

    protected void checkNext(HttpServletRequest req, HttpServletResponse resp) {
        if (next != null) {
            next.handle(req, resp);
        }
    }

    protected void prepareResponce(HttpServletResponse resp, Map<String, Object> pageParams, String usersPageTemplate) throws IOException {
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
