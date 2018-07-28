package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.repository.dao.AuthorDao;

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
@Repository
@RequiredArgsConstructor
@SuppressWarnings("SqlResolve")
public class AuthorDaoJdbc implements AuthorDao {

  private final NamedParameterJdbcOperations namedJdbc;

  @Override
  public int count() {
	return namedJdbc.queryForObject("select count(*) from author", new HashMap<>(), Integer.class);
  }

  @Override
  public Author getById(String id) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", id);
	return namedJdbc.queryForObject("select * from author where id = :id", params, new AuthorDaoJdbc.AuthorMapper());
  }

  @Override
  public List<Author> findAll() {
	return namedJdbc.query("select * from author", new HashMap<>(), new AuthorDaoJdbc.AuthorMapper());
  }

  @Override
  public void insert(String name) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", UUID.randomUUID().toString());
	params.put("name", name);
	namedJdbc.update("insert into author (id, `name`) values (:id, :name)", params);
  }

  private static class AuthorMapper implements RowMapper<Author> {

	@Override
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
	  UUID id = UUID.fromString(rs.getString("id"));
	  String name = rs.getString("name");
	  return new Author(id, name);
	}
  }
}
