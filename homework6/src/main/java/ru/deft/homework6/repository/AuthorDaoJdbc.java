package ru.deft.homework6.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.repository.dao.AuthorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public class AuthorDaoJdbc implements AuthorDao {

  private final JdbcOperations jdbc;

  public AuthorDaoJdbc(JdbcOperations jdbcOperations) {
	this.jdbc = jdbcOperations;
  }

  @Override
  public int count() {
	return jdbc.queryForObject("select count(*) from author", Integer.class);
  }

  @Override
  public Author getById(int id) {
	return jdbc.queryForObject("select * from author where id = ?", new Object[]{id}, new AuthorDaoJdbc.AuthorMapper());
  }

  @Override
  public List<Author> findAll() {
	return jdbc.queryForList("select * from author", Author.class, new AuthorDaoJdbc.AuthorMapper());
  }

  @Override
  public void insert(Author author) {
	jdbc.update("insert into author (id, `name`) values (?, ?)", author.getId(), author.getName());
  }

  private static class AuthorMapper implements RowMapper<Author> {

	@Override
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
	  int id = rs.getInt("id");
	  String name = rs.getString("name");
	  return new Author(id, name);
	}
  }
}
