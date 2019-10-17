package ru.deft.homework.chain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Created by sgolitsyn on 10/12/19
 */
public interface Handler {
    /**
     * add new handler into chain of responsibility
     *
     * @param handler new handler
     */
    void setNext(Handler handler);

    /**
     * handle action
     */
    void handle(HttpServletRequest req, HttpServletResponse resp);
}
