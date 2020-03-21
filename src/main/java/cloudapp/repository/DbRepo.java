package cloudapp.repository;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DbRepo {
	private static String DROP_DATABASE = "DROP DATABASE";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void createSchema(String schemaName) throws SQLException {
		String sql = "CREATE SCHEMA `" + schemaName + "` DEFAULT CHARACTER SET utf8";
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			Throwable throwable = e.getMostSpecificCause();
			if (throwable instanceof SQLException) {
				throw new SQLException(throwable);
			} else {
				e.printStackTrace();
			}
		}
	}

	public void deleteSchema(String schemaName) throws SQLException {
		try {
			jdbcTemplate.execute(DROP_DATABASE + " " + schemaName);
		} catch (DataAccessException e) {
			Throwable throwable = e.getMostSpecificCause();
			if (throwable instanceof SQLException) {
				throw new SQLException(throwable);
			} else {
				e.printStackTrace();
			}
		}
	}
}