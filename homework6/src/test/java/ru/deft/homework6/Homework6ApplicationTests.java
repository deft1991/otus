package ru.deft.homework6;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;
import ru.deft.homework6.repository.BookRepositoryJpa;
import ru.deft.homework6.repository.dao.BookRepository;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Homework6ApplicationTests {

  @Mock
  private JdbcOperations jdbc;
  @Mock
  private NamedParameterJdbcOperations namedJdbc;

  @Before
  public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getBookCountTest() {
	when(jdbc.queryForObject(anyString(), eq(Integer.class), anyObject())).thenReturn(1);
	BookRepository bookDao = new BookRepositoryJpa(jdbc, namedJdbc);
	bookDao.count();
	assertEquals(1, bookDao.count());
  }


}
