package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.BookDao;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

  private final NamedParameterJdbcOperations namedJdbc;

  final String INSERT_QUERY = "insert into book (id, name) values (:bookId, :name)";
  final String INSERT_BOOK_AUTHOR_QUERY = "insert into book_to_author (id, book_Id, author_Id) values (:id, :bookId, :authorId)";
  final String INSERT_BOOK_GENRE_QUERY = "insert into book_to_genre (id, book_Id, genre_Id) values (:id, :bookId, :genreId)";
  final String DELETE_QUERY = "delete from book where id = :id";

  @Override
  public int count() {
	return namedJdbc.queryForObject("select count(*) from book", new HashMap<>(), Integer.class);
  }

  /**
   * Тут использую разные сбособы. Просто самому интересно как работает
   */
  @Override
  public Book getById(String id) {
	final HashMap<String, String> params = new HashMap<>();
	params.put("id", id);
	Book book = namedJdbc.queryForObject("select * from book where id = :id", params, new BookMapper());
	List<Author> authors = getAuthors(params);
	List<Genre> genres = getGenres(params);

	book.setAuthors(authors);
	book.setGenres(genres);
	return book;
  }

  @Override
  public List<Book> findAll() {
	List<Book> books = namedJdbc.query("select * from book", new HashMap<>(), new BookMapper());
	books.forEach(book -> {
	  final HashMap<String, String> params = new HashMap<>();
	  params.put("id", book.getId().toString());
	  List<Author> authors = getAuthors(params);
	  List<Genre> genres = getGenres(params);
	  book.setAuthors(authors);
	  book.setGenres(genres);
	});
	return books;
  }

  private List<Author> getAuthors(HashMap<String, String> params) {
	return namedJdbc
			.query("select a.id, a.name from author a join book_to_author ba on a.ID = ba.AUTHOR_ID  where ba.BOOK_ID = :id",
					params,
					(rs, rowNum) -> new Author(UUID.fromString(rs.getString("id")), rs.getString("name")));
  }

  private List<Genre> getGenres(HashMap<String, String> params) {
	return namedJdbc
				.query("select g.id, g.name from genre g join book_to_genre bg on g.ID = bg.GENRE_ID  where bg.BOOK_ID = :id",
						params,
						(rs, rowNum) -> new Genre(UUID.fromString(rs.getString("id")), rs.getString("name")));
  }

  public void addBook(String bookName, String authorId, String genreId) {
	if (StringUtils.isEmpty(bookName)){
	  throw new RuntimeException("Book name can`t be empty");
	}
	final HashMap<String, Object> params = new HashMap<>();
	String bookId = UUID.randomUUID().toString();
	params.put("bookId", bookId);
	params.put("name", bookName);
	namedJdbc.update(INSERT_QUERY, params);
	if (!StringUtils.isEmpty(authorId)){
	  params.clear();
	  params.put("id", UUID.randomUUID().toString());
	  params.put("bookId", bookId);
	  params.put("authorId", authorId);
	  namedJdbc.update(INSERT_BOOK_AUTHOR_QUERY, params);
	}
	if (!StringUtils.isEmpty(genreId)){
	  params.clear();
	  params.put("id", UUID.randomUUID().toString());
	  params.put("bookId", bookId);
	  params.put("genreId", genreId);
	  namedJdbc.update(INSERT_BOOK_GENRE_QUERY, params);
	}
  }

  private static class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
	  UUID id = UUID.fromString(rs.getString("id"));
	  String name = rs.getString("name");
	  return new Book(id, name);
	}
  }
}
