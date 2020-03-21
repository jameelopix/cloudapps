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
import cloudapp.component.Column;
import cloudapp.component.Table;

@Repository
public class DbRepoImpl implements DbRepo {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private final static String INSERT_TABLE_SQL_TEMPLATE = "INSERT INTO `deletenow`.`table_master` (`DESCRIPTION`,`DISPLAYNAME`,`ICON`,`TABLE_KEY`,`NAME`,`CREATEDBY`,`CREATEDAT`,`LASTUPDATEDBY`,`LASTUPDATEDAT`) VALUES (?,?,?,?,?,?,?,?,?);";

	private final static String INSERT_COLUMN_SQL_TEMPLATE = "INSERT INTO `deletenow`.`COLUMN_MASTER` (`DESCRIPTION`,`DISPLAYNAME`,`ICON`,`TABLE_KEY`,`NAME`,`CREATEDBY`,`CREATEDAT`,`LASTUPDATEDBY`,`LASTUPDATEDAT`) VALUES (?,?,?,?,?,?,?,?,?);";

	private final static String CREATE_TABLE_SQL_TEMPLATE = "CREATE TABLE `deletenow`.`{{TABLE_NAME}}` ( `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,    `NAME` VARCHAR(255) NOT NULL,    `DESCRIPTION` VARCHAR(255) DEFAULT NULL,    `CREATEDBY` VARCHAR(45) DEFAULT NULL,    `CREATEDAT` DATETIME DEFAULT NULL,    `LASTUPDATEDBY` VARCHAR(45) DEFAULT NULL,    `LASTUPDATEDAT` DATETIME DEFAULT NULL,    PRIMARY KEY (`ID`))  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;";

	private final static String FIND_BY_ID_SQL_TEMPLATE = "SELECT * FROM `deletenow`.`table_master` WHERE ID = {{ID}};";
	private final static String DELETE_BY_ID_SQL_TEMPLATE = "DELETE FROM `deletenow`.`table_master` WHERE ID = {{ID}};";

	private static final String ID = "{{ID}}";
	private static final String TABLE_NAME = "{{TABLE_NAME}}";

	// INSERT INTO `deletenow`.`table_master` (`ID`,
	// `DESCRIPTION`,
	// `DISPLAYNAME`,
	// `ICON`,
	// `TABLE_KEY`, `NAME`, `CREATEDBY`,
	// `CREATEDAT`,
	// `LASTUPDATEDBY`,
	// `LASTUPDATEDAT`) VALUES (?,?,?,?,?,?,?,?,?,?);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Table save(Table table) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_TABLE_SQL_TEMPLATE,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, table.getDescription());
			ps.setString(2, table.getDisplayName());
			ps.setString(3, table.getIcon());
			ps.setString(4, table.getKey());
			ps.setString(5, table.getName());
			ps.setString(6, table.getCreatedBy());
			ps.setDate(7, new Date(table.getCreatedAt().getTime()));
			ps.setString(8, table.getLastUpdatedBy());
			ps.setDate(9, new Date(table.getLastUpdatedAt().getTime()));
			return ps;
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return findTableById(id);
	}

	@Override
	public Table findTableById(Long id) {
		Map<String, String> valueMap = new HashMap<>();
		valueMap.put(ID, id.toString());

		List<Table> tables = jdbcTemplate.query(StringUtils.replace(FIND_BY_ID_SQL_TEMPLATE, valueMap),
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
		valueMap.put(ID, id.toString());
		try {
			jdbcTemplate.execute(StringUtils.replace(DELETE_BY_ID_SQL_TEMPLATE, valueMap));
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
		valueMap.put(TABLE_NAME, table.getName());

		try {
			jdbcTemplate.execute(StringUtils.replace(CREATE_TABLE_SQL_TEMPLATE, valueMap));
		} catch (DataAccessException e) {
			Throwable throwable = e.getMostSpecificCause();
			if (throwable instanceof SQLException) {
				SQLException sqlException = (SQLException) throwable;
				// return sqlException.getMessage();
			}
		}
	}

	@Override
	public Column save(Column column) {
		// KeyHolder keyHolder = new GeneratedKeyHolder();
		//
		// jdbcTemplate.update(connection -> {
		// PreparedStatement ps = connection.prepareStatement(INSERT_TABLE_SQL_TEMPLATE,
		// Statement.RETURN_GENERATED_KEYS);
		// ps.setString(1, table.getDescription());
		// ps.setString(2, table.getDisplayName());
		// ps.setString(3, table.getIcon());
		// ps.setString(4, table.getKey());
		// ps.setString(5, table.getName());
		// ps.setString(6, table.getCreatedBy());
		// ps.setDate(7, new Date(table.getCreatedAt().getTime()));
		// ps.setString(8, table.getLastUpdatedBy());
		// ps.setDate(9, new Date(table.getLastUpdatedAt().getTime()));
		// return ps;
		// }, keyHolder);
		//
		// long id = keyHolder.getKey().longValue();
		//
		// return findTableById(id);

		return null;
	}

	@Override
	public void create(Column column) {
		// TODO Auto-generated method stub

	}
}

// CREATE SCHEMA `new_schema` DEFAULT CHARACTER SET utf8 ;
// CREATE SCHEMA `new_schema` DEFAULT CHARACTER SET latin7 ;

// DROP DATABASE `"testwithpostman"`;