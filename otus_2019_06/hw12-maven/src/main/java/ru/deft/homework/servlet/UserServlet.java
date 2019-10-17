package ru.deft.homework.servlet;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.chain.Handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

/*
 * Created by sgolitsyn on 10/7/19
 */
@Log
@RequiredArgsConstructor
public class UserServlet extends HttpServlet {
    private final Handler doGetHandler;
    private final Handler doPostHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.log(Level.INFO, "in UserServlet -- doGet");
        doGetHandler.handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.log(Level.INFO, "in UserServlet -- doPost");
        doPostHandler.handle(req, resp);
    }
}
