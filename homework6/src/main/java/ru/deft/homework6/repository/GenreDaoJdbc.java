package ru.deft.homework6.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @genre Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
public class GenreDaoJdbc implements GenreDao {

  private final JdbcOperations jdbc;

  public GenreDaoJdbc(JdbcOperations jdbcOperations) {
	this.jdbc = jdbcOperations;
  }

  @Override
  public int count() {
	return jdbc.queryForObject("select count(*) from genre", Integer.class);
  }

  @Override
  public Genre getById(int id) {
	return jdbc.queryForObject("select * from genre where id = ?", new Object[]{id}, new GenreDaoJdbc.GenreMapper());
  }

  @Override
  public List<Genre> findAll() {
	return jdbc.queryForList("select * from genre", Genre.class, new GenreDaoJdbc.GenreMapper());
  }

  @Override
  public void insert(Genre genre) {
	jdbc.update("insert into genre (id, `name`) values (?, ?)", genre.getId(), genre.getName());
  }

  private static class GenreMapper implements RowMapper<Genre> {

	@Override
	public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
	  int id = rs.getInt("id");
	  String name = rs.getString("name");
	  return new Genre(id, name);
	}
  }
}