package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

  private final NamedParameterJdbcOperations namedJdbc;

  @Override
  public int count() {
	return namedJdbc.queryForObject("select count(*) from book", new HashMap<>(), Integer.class);
  }

  /**
   * Тут использую разные сбособы. Просто самому интересно как работает
   */
  @Override
  public Book getById(UUID id) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", id);
	Book book = namedJdbc.queryForObject("select * from book where id = ?", params, new BookMapper());
	List<Author> authors = namedJdbc
			.query("select * from author a join book_to_author ba on a.ID = ba.AUTHOR_ID  where ba.BOOK_ID = :id",
					params,
					(rs, rowNum) -> new Author(UUID.fromString(rs.getString("id")), rs.getString("name")));
	List<Genre> genres = namedJdbc
			.query("select * from genre g join book_to_genre bg on g.ID = bg.GENRE_ID  where bg.BOOK_ID = :id",
					params,
					(rs, rowNum) -> new Genre(UUID.fromString(rs.getString("id")), rs.getString("name")));

//	book.get.setAuthors(authors);
//	book.setGenres(genres);
	return book;
  }

  @Override
  public List<Book> findAll() {
	return namedJdbc.query("select * from book", new HashMap<>(), new BookMapper());
  }

  @Override
  public void insert(Book book) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", book.getId().toString());
	params.put("name", book.getName());
	namedJdbc.update("insert into book (id, `name`) values (:id, :name)", params);
  }

  public void addBook(String bookName, String authorId, String genreId) {

  }

  private static class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
	  UUID id = UUID.fromString(rs.getString("id"));
	  String name = rs.getString("name");
	  Book book = new Book(id, name);
//	  Author author = new Author(UUID.fromString(rs.getString("id")), rs.getString("name"));
//	  Genre genre = new Genre(UUID.fromString(rs.getString("id")), rs.getString("name"));
//	  book.getAuthors().add(author);
//	  book.getGenres().add(genre);
	  return book;
	}
  }
}
