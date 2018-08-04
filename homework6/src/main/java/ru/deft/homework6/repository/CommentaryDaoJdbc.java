package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Commentary;
import ru.deft.homework6.repository.dao.CommentaryDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sergey Golitsyn (deft) on 28.07.2018
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class CommentaryDaoJdbc implements CommentaryDao {
  private final NamedParameterJdbcOperations namedJdbc;

  @Override
  public Commentary getById(UUID id) {
	return null;
  }

  @Override
  public List<Commentary> findAll() {
	return null;
  }

  @Override
  public void insert(String commentary, String bookId) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("id", UUID.randomUUID().toString());
	params.put("commentary", commentary);
	params.put("bookId", bookId);
	namedJdbc.update("insert into commentary (id, message, book_id) values (:id, :commentary, :bookId)", params);
  }

  @Override
  public List<Commentary> findAllComments(String bookId) {
	final HashMap<String, Object> params = new HashMap<>();
	params.put("bookId", bookId);
	String FIND_ALL_BOOK_COMMENTS = "select c.message from commentary c join book b on c.book_id = b.id where b.id = :bookId ";
	return namedJdbc.query(FIND_ALL_BOOK_COMMENTS, params, new CommentaryMapper());
  }

  private static class CommentaryMapper implements RowMapper<Commentary>{

	@Override
	public Commentary mapRow(ResultSet rs, int rowNum) throws SQLException {
	  String name = rs.getString("message");
	  return new Commentary(name);
	}
  }
}
