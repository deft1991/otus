package ru.deft.homework6.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Book;
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
	return jdbc.queryForObject("select * from book where id = ?", new Object[]{id}, new BookMapper());
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
	  return new Book(id, name);
	}
  }
}
