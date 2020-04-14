package cloudapp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.RowMapper;

import cloudapp.entity.Column;
import cloudapp.entity.Record;
import cloudapp.entity.Table;

public class RecordRowMapper implements RowMapper<Record> {

    private static String ID = "ID";
    // private static String TABLE_KEY = "TABLE_KEY";
    // private static String NAME = "NAME";
    // private static String DISPLAY_NAME = "DISPLAY_NAME";
    // private static String ICON = "ICON";
    // private static String DESCRIPTION = "DESCRIPTION";
    // private static String CREATED_BY = "CREATED_BY";
    // private static String CREATED_AT = "CREATED_AT";
    private static String LAST_UPDATED_BY = "LAST_UPDATED_BY";
    private static String LAST_UPDATED_AT = "LAST_UPDATED_AT";

    Table table;

    public RecordRowMapper(Table table) {
	this.table = table;
    }

    @Override
    public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
	Record record = new Record();
	record.setId(rs.getString(ID));

	Map<String, Object> columnMap = new HashMap<>();
	for (Entry<String, Column> columnEntry : table.getColumnMap().entrySet()) {
	    Column column = columnEntry.getValue();
	    switch (column.getColumnType()) {
	    case SINGLELINE:
	    case MULTILINE:
	    case EMAIL:
	    case PHONE_NO:
	    case URL:
		columnMap.put(columnEntry.getKey(), rs.getString(column.getName()));
		break;
	    case DATE:
	    case DATE_TIME:
		columnMap.put(columnEntry.getKey(), rs.getDate(column.getName()));
		break;
	    case RATING:
		columnMap.put(columnEntry.getKey(), rs.getInt(column.getName()));
		break;
	    default:
		break;
	    }
	}
	record.setLastUpdatedBy(rs.getString(LAST_UPDATED_BY));
	record.setLastUpdatedAt(rs.getDate(LAST_UPDATED_AT));
	return record;
    }
}