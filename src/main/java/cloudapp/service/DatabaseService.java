package cloudapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloudapp.common.CONFIG;
import cloudapp.dto.DatabaseRequest;
import cloudapp.dto.DatabaseResponse;
import cloudapp.entity.Column;
import cloudapp.entity.NumberColumn;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;
import cloudapp.jpa.DbRepo;
import cloudapp.validator.ObjectValidator;
import cloudapp.validator.StringValidator;

@Service
public class DatabaseService {

	private static final String TABLE_NAME = "tableName";
	private static final String DISPLAY_TABLE_NAME = "displayTableName";
	private static final String TABLE_ID = "tableId";
	private static final String ICON = "icon";
	private static final String DESCRIPTION = "description";

	Map<Long, Table> tableIDMap = new HashMap<>();
	Map<String, Table> tableKeyMap = new HashMap<>();
	Map<String, Table> tableNameMap = new HashMap<>();

	@Autowired
	ObjectValidator objectValidator;

	@Autowired
	StringValidator stringValidator;

	@Autowired
	DbRepo dbRepo;

	// Not implmeneted
	public DatabaseResponse renameTable(DatabaseRequest databaseRequest) {
		Map<String, Object> valueMap = databaseRequest.getValues();
		long tableId = (long) valueMap.get(TABLE_ID);
		String tableName = ((String) valueMap.get(TABLE_NAME)).trim();

		return null;
	}

	public DatabaseResponse addColumn(DatabaseRequest databaseRequest) {
		DatabaseResponse databaseResponse = new DatabaseResponse();
		Map<String, Object> valueMap = databaseRequest.getValues();

		String displayColumnName = ((String) valueMap.get("columnName")).trim();
		String columnType = (String) valueMap.get("columnType");

//		List<Error> errorResponses = new ArrayList<Error>();
//		Error errorRes1 = stringValidator.checkMaxLength(displayColumnName, CONFIG.COLUMN_NAME_MAX_LENGTH,
//				"Column Name can't be more than " + CONFIG.COLUMN_NAME_MAX_LENGTH);
//		if (errorRes1 != null) {
//			errorResponses.add(errorRes1);
//		}
//		Error errorRes2 = stringValidator.checkMinLength(displayColumnName, CONFIG.COLUMN_NAME_MIN_LENGTH,
//				"Column Name can't be less than " + CONFIG.COLUMN_NAME_MIN_LENGTH);
//		if (errorRes2 != null) {
//			errorResponses.add(errorRes2);
//		}

		Column column = null;
//		if (errorResponses.isEmpty()) {
		List<Column> columns = new ArrayList<Column>();
		switch (columnType) {
		case "NUMBER":
			columns.add(createNumberColumn(valueMap));
			break;
		case "SINGLE_LINE":
			column = createStringColumn(valueMap);
			break;
		case "MULTI_LINE":

			break;
		default:
			break;
		}
		databaseResponse.setColumns(columns);
//		}
		return databaseResponse;
	}

	private Column createStringColumn(Map<String, Object> valueMap) {
		Column column;
		StringColumn stringColumn = new StringColumn();
		Integer minLengthAllowed = (Integer) valueMap.get("minLengthAllowed");
		Integer maxLengthAllowed = (Integer) valueMap.get("maxLengthAllowed");

		minLengthAllowed = minLengthAllowed != null ? minLengthAllowed : 0;
		maxLengthAllowed = maxLengthAllowed != null ? maxLengthAllowed : 4096;

		stringColumn.setMinLengthAllowed(minLengthAllowed);
		stringColumn.setMaxLengthAllowed(maxLengthAllowed);

		column = stringColumn;
		return column;
	}

	private Column createNumberColumn(Map<String, Object> valueMap) {
		Column column;
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

		if (numberColumn != null) {
			long tableId = (long) valueMap.get(TABLE_ID);
			String displayColumnName = ((String) valueMap.get("columnName")).trim();
			String columnName = displayColumnName.replace(" ", "_").toUpperCase();
			String columnKey = RandomService.generateKey(RandomService.PREFIX_COLUMN);

			Table table = getTableById(tableId);

			numberColumn.setKey(columnKey);
			numberColumn.setName(columnName);
			numberColumn.setDisplayName(displayColumnName);
			populateColumnValue(numberColumn);

			dbRepo.create(numberColumn);
			column = dbRepo.save(numberColumn);

			addColumn(table, column);
		}
		return numberColumn;
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

	public DatabaseResponse createTable(DatabaseRequest databaseRequest) {
		DatabaseResponse databaseResponse = new DatabaseResponse();
		Map<String, Object> valueMap = databaseRequest.getValues();

		String displayTableName = valueMap.get(DISPLAY_TABLE_NAME) != null
				? ((String) valueMap.get(DISPLAY_TABLE_NAME)).trim()
				: null;
		if (displayTableName == null) {
			displayTableName = valueMap.get(TABLE_NAME) != null ? ((String) valueMap.get(TABLE_NAME)).trim() : null;
		}

		// TODO
		stringValidator.checkNotBlank(displayTableName, "Table name is missing.");
		String tableName = displayTableName.replace(" ", "_").toUpperCase();
		String tableKey = RandomService.generateKey(RandomService.PREFIX_TABLE);

		stringValidator.checkMaxLength(displayTableName, CONFIG.TABLE_NAME_MAX_LENGTH,
				"Table Name can't be more than " + CONFIG.TABLE_NAME_MAX_LENGTH);
		stringValidator.checkMinLength(displayTableName, CONFIG.TABLE_NAME_MIN_LENGTH,
				"Table Name can't be less than " + CONFIG.TABLE_NAME_MIN_LENGTH);
		// TODO check duplicate for Table name

//		if (errors.isEmpty()) {
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
//		} else {
//			databaseResponse.setErrors(errors);
//		}
		databaseResponse.setTable(table);
		return databaseResponse;
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

	public DatabaseResponse updateTable(DatabaseRequest databaseRequest) {
		DatabaseResponse databaseResponse = new DatabaseResponse();
		Map<String, Object> valueMap = databaseRequest.getValues();

		Long tableId = valueMap.get(TABLE_ID) != null ? (Long) valueMap.get(TABLE_ID) : null;
		String icon = valueMap.get(ICON) != null ? (String) valueMap.get(ICON) : null;
		String description = valueMap.get(DESCRIPTION) != null ? (String) valueMap.get(DESCRIPTION) : null;

		objectValidator.checkNotNull(tableId, "Table Id is missing.");

		Table table = getTableById(tableId);
		table.setIcon(icon);
		table.setDescription(description);
		table.setLastUpdatedAt(new Date());
		table.setLastUpdatedBy(getLoggedInUser());

		table = dbRepo.update(table);
		databaseResponse.setTable(table);
		return databaseResponse;
	}
}