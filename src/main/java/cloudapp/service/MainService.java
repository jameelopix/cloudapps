package cloudapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloudapp.common.CONFIG;
import cloudapp.component.Column;
import cloudapp.component.NumberColumn;
import cloudapp.component.StringColumn;
import cloudapp.component.Table;
import cloudapp.dto.ErrorResponse;
import cloudapp.dto.MainRequest;
import cloudapp.dto.TableResponse;
import cloudapp.jpa.DbRepo;
import cloudapp.validator.StringValidator;

@Service
public class MainService {

    Map<Long, Table> tableIDMap = new HashMap<>();
    Map<String, Table> tableKeyMap = new HashMap<>();
    Map<String, Table> tableNameMap = new HashMap<>();

    @Autowired
    StringValidator stringValidator;

    @Autowired
    DbRepo dbRepo;

    // Not implmeneted
    public TableResponse renameTable(MainRequest mainRequest) {
	Map<String, Object> valueMap = mainRequest.getStringifiedValues();
	long tableId = (long) valueMap.get("tableId");
	String tableName = ((String) valueMap.get("tableName")).trim();

	return null;
    }

    public TableResponse addColumn(MainRequest mainRequest) {
	Map<String, Object> valueMap = mainRequest.getStringifiedValues();

	long tableId = (long) valueMap.get("tableId");
	String displayColumnName = ((String) valueMap.get("columnName")).trim();
	String columnType = (String) valueMap.get("columnType");
	String columnName = displayColumnName.replace(" ", "_").toUpperCase();
	String columnKey = RandomService.generateKey(RandomService.PREFIX_COLUMN);

	List<ErrorResponse> errorResponses = new ArrayList<ErrorResponse>();
	ErrorResponse errorRes1 = stringValidator.checkMaxLength(displayColumnName, CONFIG.COLUMN_NAME_MAX_LENGTH,
		"Column Name can't be more than " + CONFIG.COLUMN_NAME_MAX_LENGTH);
	if (errorRes1 != null) {
	    errorResponses.add(errorRes1);
	}
	ErrorResponse errorRes2 = stringValidator.checkMinLength(displayColumnName, CONFIG.COLUMN_NAME_MIN_LENGTH,
		"Column Name can't be less than " + CONFIG.COLUMN_NAME_MIN_LENGTH);
	if (errorRes2 != null) {
	    errorResponses.add(errorRes2);
	}

	if (errorResponses.isEmpty()) {
	    Column column = null;
	    switch (columnType) {
	    case "NUMBER":
		NumberColumn numberColumn = new NumberColumn();
		Integer minAllowed = (Integer) valueMap.get("minAllowed");
		Integer maxAllowed = (Integer) valueMap.get("maxAllowed");
		Boolean negativeAllowed = (Boolean) valueMap.get("negativeAllowed");

		minAllowed = minAllowed != null ? minAllowed : Integer.MIN_VALUE;
		maxAllowed = maxAllowed != null ? maxAllowed : Integer.MAX_VALUE;
		negativeAllowed = negativeAllowed != null ? negativeAllowed : false;

		numberColumn.setMinAllowed(minAllowed);
		numberColumn.setMaxAllowed(maxAllowed);
		numberColumn.setNegativeAllowed(negativeAllowed);

		column = numberColumn;
		break;
	    case "SINGLE_LINE":
		StringColumn stringColumn = new StringColumn();
		Integer minLengthAllowed = (Integer) valueMap.get("minLengthAllowed");
		Integer maxLengthAllowed = (Integer) valueMap.get("maxLengthAllowed");

		minLengthAllowed = minLengthAllowed != null ? minLengthAllowed : 0;
		maxLengthAllowed = maxLengthAllowed != null ? maxLengthAllowed : 4096;

		stringColumn.setMinLengthAllowed(minLengthAllowed);
		stringColumn.setMaxLengthAllowed(maxLengthAllowed);

		column = stringColumn;
		break;
	    case "MULTI_LINE":

		break;
	    default:
		break;
	    }

	    if (column != null) {
		Table table = getTableById(tableId);

		column.setKey(columnKey);
		column.setName(columnName);
		column.setDisplayName(displayColumnName);
		populateColumnValue(column);

		dbRepo.create(column);
		column = dbRepo.save(column);

		addColumn(table, column);
	    }

	    // dbRepo.create(table);
	    //
	    // table = dbRepo.save(table);
	    //
	    // addTable(table);
	}
	return null;
    }

    private void addColumn(Table table, Column column) {
	table.getColumns().add(column);
    }

    private void populateColumnValue(Column column) {
	column.setCreatedAt(new Date());
	column.setCreatedBy(getLoggedInUser());
	column.setLastUpdatedAt(new Date());
	column.setLastUpdatedBy(getLoggedInUser());
    }

    public TableResponse createTable(MainRequest mainRequest) {
	Map<String, Object> valueMap = mainRequest.getStringifiedValues();

	String displayTableName = ((String) valueMap.get("tableName")).trim();
	String tableName = displayTableName.replace(" ", "_").toUpperCase();
	String tableKey = RandomService.generateKey(RandomService.PREFIX_TABLE);

	List<ErrorResponse> errorResponses = new ArrayList<ErrorResponse>();
	ErrorResponse errorRes1 = stringValidator.checkMaxLength(displayTableName, CONFIG.TABLE_NAME_MAX_LENGTH,
		"Table Name can't be more than " + CONFIG.TABLE_NAME_MAX_LENGTH);
	if (errorRes1 != null) {
	    errorResponses.add(errorRes1);
	}
	ErrorResponse errorRes2 = stringValidator.checkMinLength(displayTableName, CONFIG.TABLE_NAME_MIN_LENGTH,
		"Table Name can't be less than " + CONFIG.TABLE_NAME_MIN_LENGTH);
	if (errorRes2 != null) {
	    errorResponses.add(errorRes2);
	}
	// TODO check duplicate for Table name

	if (errorResponses.isEmpty()) {
	    Table table = new Table();
	    table.setKey(tableKey);
	    table.setDisplayName(displayTableName);
	    table.setName(tableName);
	    table.setCreatedAt(new Date());
	    table.setCreatedBy(getLoggedInUser());
	    table.setLastUpdatedAt(new Date());
	    table.setLastUpdatedBy(getLoggedInUser());

	    dbRepo.create(table);

	    table = dbRepo.save(table);

	    addTable(table);
	}
	return null;
    }

    private void addTable(Table table) {
	tableIDMap.put(table.getId(), table);
	tableKeyMap.put(table.getKey(), table);
	tableNameMap.put(table.getName(), table);
    }

    private Table getTableById(Long id) {
	return tableIDMap.get(id);
    }

    private Table getTableByKey(String key) {
	return tableKeyMap.get(key);
    }

    private Table getTableByName(String name) {
	return tableNameMap.get(name);
    }

    private String getLoggedInUser() {
	return "JAMEEL";
    }
}