package ru.deft.homework6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.repository.dao.BookDao;

import java.util.UUID;


@SpringBootApplication
public class Homework6Application {

  public static void main(String[] args) {
	ApplicationContext applicationContext = SpringApplication.run(Homework6Application.class, args);
	BookDao bookDao = applicationContext.getBean(BookDao.class);
	System.out.println(bookDao.count());
  }
}
