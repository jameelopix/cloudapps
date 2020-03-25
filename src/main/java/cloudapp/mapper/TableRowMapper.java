package cloudapp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cloudapp.entity.Table;

public class TableRowMapper implements RowMapper<Table> {

	private static String ID = "ID";
	private static String TABLE_KEY = "TABLE_KEY";
	private static String NAME = "NAME";
	private static String DISPLAY_NAME = "DISPLAY_NAME";
	private static String ICON = "ICON";
	private static String DESCRIPTION = "DESCRIPTION";
	private static String CREATED_BY = "CREATED_BY";
	private static String CREATED_AT = "CREATED_AT";
	private static String LAST_UPDATED_BY = "LAST_UPDATED_BY";
	private static String LAST_UPDATED_AT = "LAST_UPDATED_AT";

	@Override
	public Table mapRow(ResultSet rs, int rowNum) throws SQLException {
		Table table = new Table();
//		table.setId(rs.getLong(ID));
//		table.setKey(rs.getString(TABLE_KEY));
//		table.setName(rs.getString(NAME));
//		table.setDisplayName(rs.getString(DISPLAY_NAME));
//		table.setIcon(rs.getString(ICON));
//		table.setDescription(rs.getString(DESCRIPTION));
//		table.setCreatedBy(rs.getString(CREATED_BY));
//		table.setCreatedAt(rs.getDate(CREATED_AT));
//		table.setLastUpdatedBy(rs.getString(LAST_UPDATED_BY));
//		table.setLastUpdatedAt(rs.getDate(LAST_UPDATED_AT));
		return table;
	}
}