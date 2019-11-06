package ru.deft.homework.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.service.impl.DbUserServiceImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;
import ru.deft.homework.service.impl.UserDetailsServiceImpl;

/*
 * Created by sgolitsyn on 10/14/19
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ru.deft.homework"})
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    @Bean
    public SessionManager sessionManager() {
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoHibernate(sessionManager());
    }

    @Bean
    public DbUserService dbUserService() {
        return new DbUserServiceImpl(userDao());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {

        var resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("/");
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {

        var freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates/");
        return freeMarkerConfigurer;
    }
}
