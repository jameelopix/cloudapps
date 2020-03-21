package cloudapp.dto;

import cloudapp.entity.TableMaster;

public class TableRequest extends ParentRequest {

	private static final long serialVersionUID = 1L;

	private TableMaster table;
	private TableSearchDTO tableSearchDTO;

	public TableSearchDTO getTableSearchDTO() {
		return tableSearchDTO;
	}

	public void setTableSearchDTO(TableSearchDTO tableSearchDTO) {
		this.tableSearchDTO = tableSearchDTO;
	}

	public TableMaster getTable() {
		return table;
	}

	public void setTable(TableMaster table) {
		this.table = table;
	}
}