package cloudapp.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cloudapp.component.Table;

public class TableRowMapper implements RowMapper<Table> {

	@Override
	public Table mapRow(ResultSet rs, int rowNum) throws SQLException {
		Table table = new Table();
		table.setId(rs.getLong("ID"));
		table.setKey(rs.getString("TABLE_KEY"));
		table.setName(rs.getString("NAME"));
		table.setDisplayName(rs.getString("DISPLAYNAME"));
		table.setIcon(rs.getString("ICON"));
		table.setDescription(rs.getString("DESCRIPTION"));
		table.setCreatedBy(rs.getString("CREATEDBY"));
		table.setCreatedAt(rs.getDate("CREATEDAT"));
		table.setLastUpdatedBy(rs.getString("LASTUPDATEDBY"));
		table.setLastUpdatedAt(rs.getDate("LASTUPDATEDAT"));
		return table;
	}
}