package cloudapp.dto;

import java.util.List;

import cloudapp.entity.TableMaster;

public class TableResponse extends ParentResponse {

	private static final long serialVersionUID = 1L;

	private TableMaster table;

	private List<TableMaster> tableList;

	public List<TableMaster> getTableList() {
		return tableList;
	}

	public void setTableList(List<TableMaster> tableList) {
		this.tableList = tableList;
	}

	public TableMaster getTable() {
		return table;
	}

	public void setTable(TableMaster table) {
		this.table = table;
	}
}