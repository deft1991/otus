package ru.deft.homework6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.repository.dao.BookDao;


@SpringBootApplication
public class Homework6Application {

  public static void main(String[] args) {
	ApplicationContext applicationContext = SpringApplication.run(Homework6Application.class, args);
	JdbcTemplate jdbc = applicationContext.getBean(JdbcTemplate.class);
	jdbc.execute("DROP TABLE IF EXISTS BOOK");
	jdbc.execute("DROP TABLE IF EXISTS GENRE");
	jdbc.execute("DROP TABLE IF EXISTS AUTHOR");
	jdbc.execute("create table BOOK(ID INT PRIMARY KEY, NAME VARCHAR(255))");
	jdbc.execute("CREATE TABLE GENRE(ID INT PRIMARY KEY, NAME VARCHAR(255))");
	jdbc.execute("CREATE TABLE AUTHOR(ID INT PRIMARY KEY, NAME VARCHAR(255))");
	BookDao bookDao = applicationContext.getBean(BookDao.class);
	bookDao.insert(new Book() {{
	  setId(1);
	  setName("firsName");
	}});
	bookDao.insert(new Book() {{
	  setId(2);
	  setName("Тру тру тру");
	}});
	bookDao.insert(new Book() {{
	  setId(3);
	  setName("This is author name");
	}});
	System.out.println(bookDao.count());
//	Console.main(args);
  }
}
