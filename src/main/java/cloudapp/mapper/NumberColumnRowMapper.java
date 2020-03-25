package cloudapp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cloudapp.entity.NumberColumn;

public class NumberColumnRowMapper implements RowMapper<NumberColumn> {

	private static String ID = "ID";
	private static String COLUMN_KEY = "COLUMN_KEY";
	private static String NAME = "NAME";
	private static String DISPLAY_NAME = "DISPLAY_NAME";
	private static String ICON = "ICON";
	private static String DESCRIPTION = "DESCRIPTION";
	private static String CREATED_BY = "CREATED_BY";
	private static String CREATED_AT = "CREATED_AT";
	private static String LAST_UPDATED_BY = "LAST_UPDATED_BY";
	private static String LAST_UPDATED_AT = "LAST_UPDATED_AT";
	private static String TABLE_ID = "TABLE_ID";
	private static String IS_NEGATIVE_ALLOWED = "IS_NEGATIVE_ALLOWED";
	private static String MIN_ALLOWED = "MIN_ALLOWED";
	private static String MAX_ALLOWED = "MAX_ALLOWED";

	@Override
	public NumberColumn mapRow(ResultSet rs, int rowNum) throws SQLException {
		NumberColumn numberColumn = new NumberColumn();
		numberColumn.setId(rs.getLong(ID));
		numberColumn.setKey(rs.getString(COLUMN_KEY));
		numberColumn.setName(rs.getString(NAME));
		numberColumn.setDisplayName(rs.getString(DISPLAY_NAME));
		numberColumn.setIcon(rs.getString(ICON));
		numberColumn.setDescription(rs.getString(DESCRIPTION));
		numberColumn.setCreatedBy(rs.getString(CREATED_BY));
		numberColumn.setCreatedAt(rs.getDate(CREATED_AT));
		numberColumn.setLastUpdatedBy(rs.getString(LAST_UPDATED_BY));
		numberColumn.setLastUpdatedAt(rs.getDate(LAST_UPDATED_AT));
		numberColumn.setTableId(rs.getLong(TABLE_ID));
		numberColumn.setNegativeAllowed(rs.getBoolean(IS_NEGATIVE_ALLOWED));
		numberColumn.setMinAllowed(rs.getInt(MIN_ALLOWED));
		numberColumn.setMaxAllowed(rs.getInt(MAX_ALLOWED));
		return numberColumn;
	}
}