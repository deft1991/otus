package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @genre Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

  private final NamedParameterJdbcOperations namedJdbc;

  @Override
  public int count() {
	return namedJdbc.queryForObject("select count(*) from genre", new HashMap<>(), Integer.class);
  }

  @Override
  public Genre getById(UUID id) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", id);
	return namedJdbc.queryForObject("select * from genre where id = :id", params, new GenreDaoJdbc.GenreMapper());
  }

  @Override
  public List<Genre> findAll() {
	return namedJdbc.query("select * from genre", new HashMap<>(), new GenreDaoJdbc.GenreMapper());
  }

  @Override
  public void insert(Genre genre) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", genre.getId().toString());
	params.put("name", genre.getName());
	namedJdbc.update("insert into genre (id, `name`) values (:id, :name)", params);
  }

  private static class GenreMapper implements RowMapper<Genre> {

	@Override
	public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
	  UUID id = UUID.fromString(rs.getString("id"));
	  String name = rs.getString("name");
	  return new Genre(id, name);
	}
  }
}