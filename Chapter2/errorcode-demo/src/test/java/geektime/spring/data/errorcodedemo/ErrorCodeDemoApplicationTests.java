package geektime.spring.data.errorcodedemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorCodeDemoApplicationTests {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test(expected = CustomDuplicatedKeyException.class)
	@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testThrowingCustomException() {
		jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'a')");
		jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'b')");
	}
}

