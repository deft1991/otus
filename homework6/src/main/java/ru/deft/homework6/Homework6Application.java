package ru.deft.homework6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.AuthorDao;
import ru.deft.homework6.repository.dao.BookDao;
import ru.deft.homework6.repository.dao.GenreDao;


@SpringBootApplication
public class Homework6Application {

  public static void main(String[] args) {
	ApplicationContext applicationContext = SpringApplication.run(Homework6Application.class, args);
	JdbcTemplate jdbc = applicationContext.getBean(JdbcTemplate.class);
	jdbc.execute("DROP TABLE IF EXISTS BOOK");
	jdbc.execute("DROP TABLE IF EXISTS GENRE");
	jdbc.execute("DROP TABLE IF EXISTS AUTHOR");
	jdbc.execute("DROP TABLE IF EXISTS BOOK_TO_AUTHOR");
	jdbc.execute("DROP TABLE IF EXISTS BOOK_TO_GENRE");
	jdbc.execute("create table BOOK(ID INT PRIMARY KEY, NAME VARCHAR(255))");
	jdbc.execute("CREATE TABLE GENRE(ID INT PRIMARY KEY, NAME VARCHAR(255))");
	jdbc.execute("CREATE TABLE AUTHOR(ID INT PRIMARY KEY, NAME VARCHAR(255))");
	jdbc.execute("CREATE TABLE BOOK_TO_AUTHOR(ID INT PRIMARY KEY, BOOK_ID INT, AUTHOR_ID INT)");
	jdbc.execute("CREATE TABLE BOOK_TO_GENRE(ID INT PRIMARY KEY, BOOK_ID INT, GENRE_ID INT)");
	BookDao bookDao = applicationContext.getBean(BookDao.class);
	AuthorDao authorDao = applicationContext.getBean(AuthorDao.class);
	GenreDao genreDao = applicationContext.getBean(GenreDao.class);
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
	  setName("This is book name");
	}});
	authorDao.insert(new Author() {{
	  setId(1);
	  setName("This is authors 1");
	}});
	authorDao.insert(new Author() {{
	  setId(2);
	  setName("This is authors 2");
	}});
	authorDao.insert(new Author() {{
	  setId(3);
	  setName("This is authors 3");
	}});
	genreDao.insert(new Genre() {{
	  setId(1);
	  setName("This is genres 1");
	}});
	genreDao.insert(new Genre() {{
	  setId(2);
	  setName("This is genres 2");
	}});
	genreDao.insert(new Genre() {{
	  setId(3);
	  setName("This is genres 3");
	}});

	jdbc.execute("INSERT into book_to_author values (1,1,2)");
	jdbc.execute("INSERT into book_to_author values (2,2,2)");
	jdbc.execute("INSERT into book_to_author values (3,3,1)");
	jdbc.execute("INSERT into book_to_author values (4,1,1)");
	jdbc.execute("INSERT into book_to_author values (5,2,3)");
	jdbc.execute("INSERT into book_to_author values (6,3,2)");
	jdbc.execute("INSERT into book_to_author values (7,1,3)");

	jdbc.execute("INSERT into book_to_genre values (1,1,2)");
	jdbc.execute("INSERT into book_to_genre values (2,2,2)");
	jdbc.execute("INSERT into book_to_genre values (3,3,1)");
	jdbc.execute("INSERT into book_to_genre values (4,1,1)");
	jdbc.execute("INSERT into book_to_genre values (5,2,3)");
	jdbc.execute("INSERT into book_to_genre values (6,3,2)");
	jdbc.execute("INSERT into book_to_genre values (7,1,3)");

	bookDao.getById(1);
	System.out.println(bookDao.count());
//	Console.main(args);
  }
}
