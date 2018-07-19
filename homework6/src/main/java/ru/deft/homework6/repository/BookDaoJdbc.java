package ru.deft.homework6.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.BookDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
public class BookDaoJdbc implements BookDao {

  private final JdbcOperations jdbc;

  public BookDaoJdbc(JdbcOperations jdbcOperations) {
	this.jdbc = jdbcOperations;
  }

  @Override
  public int count() {
	return jdbc.queryForObject("select count(*) from book", Integer.class);
  }

  @Override
  public Book getById(int id) {
	Book book = jdbc.queryForObject("select * from book where id = ?", new Object[]{id}, new BookMapper());
	jdbc.queryForList("select * from author a join book_to_author ba on a.ID = ba.AUTHOR_ID  where ba.BOOK_ID = ?", new Object[]{id}, new RowMapper(){
	  @Override
	  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		return null;
	  }
	});
	return book;
  }

  @Override
  public List<Book> findAll() {
	return jdbc.queryForList("select * from book", Book.class, new BookMapper());
  }

  @Override
  public void insert(Book book) {
	jdbc.update("insert into book (id, `name`) values (?, ?)", book.getId(), book.getName());
  }

  private static class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
	  int id = rs.getInt("id");
	  String name = rs.getString("name");
	  Book book = new Book(id, name);
	  Author author = new Author(rs.getInt("id"), rs.getString("name"));
	  Genre genre = new Genre(rs.getInt("id"), rs.getString("name"));
	  book.setAuthor(author);
	  book.setGenre(genre);
	  return book;
	}
  }
}
