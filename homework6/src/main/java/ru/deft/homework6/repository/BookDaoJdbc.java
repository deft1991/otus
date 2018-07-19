package ru.deft.homework6.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.BookDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
public class BookDaoJdbc implements BookDao {

  private final JdbcOperations jdbc;
  private final NamedParameterJdbcOperations namedJdbc;

  public BookDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedJdbc) {
	this.jdbc = jdbcOperations;
	this.namedJdbc = namedJdbc;
  }

  @Override
  public int count() {
	return jdbc.queryForObject("select count(*) from book", Integer.class);
  }

  /**
   * Тут использую разные сбособы. Просто самому интересно как работает
   */
  @Override
  public Book getById(int id) {
	Book book = jdbc.queryForObject("select * from book where id = ?", new Object[]{id}, new BookMapper());
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", id);
	List<Author> authors = namedJdbc
			.query("select * from author a join book_to_author ba on a.ID = ba.AUTHOR_ID  where ba.BOOK_ID = :id",
					params,
					(rs, rowNum) -> new Author(rs.getInt("id"), rs.getString("name")));
	List<Genre> genres = namedJdbc
			.query("select * from genre g join book_to_genre bg on g.ID = bg.GENRE_ID  where bg.BOOK_ID = :id",
					params,
					(rs, rowNum) -> new Genre(rs.getInt("id"), rs.getString("name")));

	book.setAuthors(authors);
	book.setGenres(genres);
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
	  book.addAuthor(author);
	  book.addGenre(genre);
	  return book;
	}
  }
}
