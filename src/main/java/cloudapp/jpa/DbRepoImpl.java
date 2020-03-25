package cloudapp.jpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cloudapp.common.StringUtils;
import cloudapp.entity.Column;
import cloudapp.entity.NumberColumn;
import cloudapp.entity.Table;
import cloudapp.mapper.NumberColumnRowMapper;
import cloudapp.mapper.TableRowMapper;
import cloudapp.web.DatabaseException;

@Repository
public class DbRepoImpl implements DbRepo {
	private static String DROP_DATABASE = "DROP DATABASE";

	private static String DATABASE_NAME = "deletenow";

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;

	// Insert SQL Template
//	private final static String INSERT_TABLE_ENTRY_SQL_TEMPLATE = "INSERT INTO `{{DB_NAME}}`.`table_master`\r\n" + 
//			"(`ID`,\r\n" + 
//			"`NAME`,\r\n" + 
//			"`TABLE_KEY`,\r\n" + 
//			"`DISPLAY_NAME`,\r\n" + 
//			"`ICON`,\r\n" + 
//			"`DESCRIPTION`,\r\n" + 
//			"`CREATED_BY`,\r\n" + 
//			"`CREATED_AT`,\r\n" + 
//			"`LAST_UPDATED_BY`,\r\n" + 
//			"`LAST_UPDATED_AT`)\r\n" + 
//			"VALUES\r\n" + 
//			"(?,?,?,?,?,?,?,?,?,?);";

	private final static String INSERT_COLUMN_ENTRY_SQL_TEMPLATE = "INSERT INTO `{{DB_NAME}}`.`column_master`\r\n"
			+ "(`ID`,\r\n" + "`NAME`,\r\n" + "`COLUMN_KEY`,\r\n" + "`DISPLAY_NAME`,\r\n" + "`ICON`,\r\n"
			+ "`DESCRIPTION`,\r\n" + "`CREATED_BY`,\r\n" + "`CREATED_AT`,\r\n" + "`LAST_UPDATED_BY`,\r\n"
			+ "`LAST_UPDATED_AT`,\r\n" + "`TABLE_ID`)\r\n" + "VALUES\r\n" + "(?,?,?,?,?,?,?,?,?,?,?);";

	// Create SQL Template

	// INSERT INTO `deletenow`.`table_master` (`ID`,
	// `DESCRIPTION`,
	// `DISPLAYNAME`,
	// `ICON`,
	// `TABLE_KEY`, `NAME`, `CREATEDBY`,
	// `CREATEDAT`,
	// `LASTUPDATEDBY`,
	// `LASTUPDATEDAT`) VALUES (?,?,?,?,?,?,?,?,?,?);

	@Override
	public Table save(Table table) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(SqlTemplate.DB_NAME, DATABASE_NAME);

		String sql = StringUtils.replace(SqlTemplate.Table.INSERT, valueMap);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			ps.setString(1, table.getName());
//			ps.setString(2, table.getKey());
//			ps.setString(3, table.getDisplayName());
//			ps.setString(4, table.getIcon());
//			ps.setString(5, table.getDescription());
//			ps.setString(6, table.getCreatedBy());
//			ps.setDate(7, new Date(table.getCreatedAt().getTime()));
//			ps.setString(8, table.getLastUpdatedBy());
//			ps.setDate(9, new Date(table.getLastUpdatedAt().getTime()));
			return ps;
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return findTableById(id);
	}

	@Override
	public Table update(Table table) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(SqlTemplate.DB_NAME, DATABASE_NAME);

		String sql = StringUtils.replace(SqlTemplate.Table.UPDATE, valueMap);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			ps.setString(1, table.getName());
//			ps.setString(2, table.getKey());
//			ps.setString(3, table.getDisplayName());
//			ps.setString(4, table.getIcon());
//			ps.setString(5, table.getDescription());
//			ps.setString(6, table.getCreatedBy());
//			ps.setDate(7, new Date(table.getCreatedAt().getTime()));
//			ps.setString(8, table.getLastUpdatedBy());
//			ps.setDate(9, new Date(table.getLastUpdatedAt().getTime()));
//			ps.setLong(10, table.getId());
			return ps;
		});

//		return findTableById(table.getId());
		return null;
	}

	@Override
	public Table findTableById(Long id) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(SqlTemplate.ID, id.toString());
		valueMap.put(SqlTemplate.DB_NAME, DATABASE_NAME);

		List<Table> tables = jdbcTemplate.query(StringUtils.replace(SqlTemplate.Table.FIND_BY_ID, valueMap),
				new TableRowMapper());

		if (tables.isEmpty()) {
			return null;
		} else {
			return tables.get(0);
		}
	}

	@Override
	public void deleteTableById(Long id) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(SqlTemplate.ID, id.toString());
		valueMap.put(SqlTemplate.DB_NAME, DATABASE_NAME);
		try {
			jdbcTemplate.execute(StringUtils.replace(SqlTemplate.Table.DELETE_BY_ID, valueMap));
		} catch (DataAccessException e) {
			Throwable throwable = e.getMostSpecificCause();
			if (throwable instanceof SQLException) {
				SQLException sqlException = (SQLException) throwable;
				// return sqlException.getMessage();
			}
		}
	}

	@Override
	public void create(Table table) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(SqlTemplate.DB_NAME, DATABASE_NAME);
		valueMap.put(SqlTemplate.TABLE_NAME, table.getName());

		try {
			jdbcTemplate.execute(StringUtils.replace(SqlTemplate.Table.SAMPLE_TABLE, valueMap));
		} catch (DataAccessException e) {
			Throwable throwable = e.getMostSpecificCause();
			if (throwable instanceof SQLException) {
				SQLException sqlException = (SQLException) throwable;
				throw new DatabaseException(sqlException.getMessage());
			}
		}
	}

	@Override
	public Column save(NumberColumn numberColumn) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(SqlTemplate.NumberColumn.INSERT,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, numberColumn.getDescription());
			ps.setString(2, numberColumn.getDisplayName());
			ps.setString(3, numberColumn.getIcon());
			ps.setString(4, numberColumn.getKey());
			ps.setString(5, numberColumn.getName());
			ps.setString(6, numberColumn.getCreatedBy());
			ps.setDate(7, new Date(numberColumn.getCreatedAt().getTime()));
			ps.setString(8, numberColumn.getLastUpdatedBy());
			ps.setDate(9, new Date(numberColumn.getLastUpdatedAt().getTime()));

			ps.setLong(10, numberColumn.getTableId());
			ps.setBoolean(11, numberColumn.isNegativeAllowed());
			ps.setInt(12, numberColumn.getMinAllowed());
			ps.setInt(13, numberColumn.getMaxAllowed());
			return ps;
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return findNumberColumnById(id);
	}

	private Column findNumberColumnById(Long id) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(SqlTemplate.ID, id.toString());
		valueMap.put(SqlTemplate.DB_NAME, DATABASE_NAME);

		List<NumberColumn> numberColumns = jdbcTemplate
				.query(StringUtils.replace(SqlTemplate.NumberColumn.FIND_BY_ID, valueMap), new NumberColumnRowMapper());

		if (numberColumns.isEmpty()) {
			return null;
		} else {
			return numberColumns.get(0);
		}
	}

	@Override
	public void create(NumberColumn numberColumn) {
		// TODO Auto-generated method stub

	}

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

// CREATE SCHEMA `new_schema` DEFAULT CHARACTER SET utf8 ;
// CREATE SCHEMA `new_schema` DEFAULT CHARACTER SET latin7 ;

// DROP DATABASE `"testwithpostman"`;