package cloudapp.dto;

import java.util.List;

import cloudapp.entity.Column;
import cloudapp.entity.Record;
import cloudapp.entity.Table;

public class DatabaseResponse extends ParentResponse {

	private static final long serialVersionUID = 1L;

	private Table table;

	private List<Record> records;

	private List<Column> columns;

	private int totalRecordCount;

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
}