package ru.deft.homework;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.deft.homework.api.model.User;
import ru.deft.homework.auth.AuthorizationFilter;
import ru.deft.homework.ioc.IoC;
import ru.deft.homework.servlet.UserServlet;
import ru.deft.homework.servlet.login.LoginServlet;

/*
 * Created by sgolitsyn on 10/7/19
 */
public class Main {
    private static final int PORT = 8080;
    public static final String STATIC = "/static";
    private static IoC ioC = new IoC();


    public static void main(String[] args) throws Exception {
        new Main().start();
    }

    private void start() throws Exception {

        initUsers();

        ResourceHandler handler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        handler.setBaseResource(resource);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
// todo add servlets
        context.addServlet(
                new ServletHolder(
                        new UserServlet(ioC.getDbUserService()
                                , ioC.getGson()
                                , ioC.getTemplateProcessor())), "/user/*");
        context.addServlet(new ServletHolder(new LoginServlet(ioC.getUserService(), ioC.getTemplateProcessor())), "/login");
        context.addFilter(new FilterHolder(new AuthorizationFilter()), "/admin/*", null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(handler, context));
        server.start();
        server.join();
    }

    private void initUsers() {
        ioC.getSessionManager().beginSession();
        ioC.getUserDao().save(new User("deft", "qqq"));
        ioC.getUserDao().save(new User("admin", "admin"));
        ioC.getUserDao().save(new User("user", "user"));
        ioC.getSessionManager().commitSession();
    }
}
