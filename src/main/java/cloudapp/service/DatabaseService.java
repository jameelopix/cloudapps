package cloudapp.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cloudapp.common.CONFIG;
import cloudapp.dto.DatabaseRequest;
import cloudapp.dto.DatabaseResponse;
import cloudapp.entity.Column;
import cloudapp.entity.ColumnType;
import cloudapp.entity.ColumnView;
import cloudapp.entity.DefaultColumn;
import cloudapp.entity.NumberColumn;
import cloudapp.entity.RequestFieldName;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;
import cloudapp.entity.TableView;
import cloudapp.jpa.DATABASE_ENGINE;
import cloudapp.jpa.DbRepo;
import cloudapp.validator.ObjectValidator;
import cloudapp.validator.StringValidator;

@Service
public class DatabaseService {

	Map<String, Table> tableIDMap = new HashMap<>();
	Map<String, Table> tableNameMap = new HashMap<>();

	@Autowired
	ObjectValidator objectValidator;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	StringValidator stringValidator;

	@Autowired
	DbRepo dbRepo;

	// Not implmeneted
	public DatabaseResponse renameTable(DatabaseRequest databaseRequest) {
		// Map<String, Object> valueMap = databaseRequest.getValues();
		// long tableId = (long) valueMap.get(FieldName.ID);
		// String tableName = ((String) valueMap.get(FieldName.NAME)).trim();

		return null;
	}

	public DatabaseResponse addColumn(DatabaseRequest databaseRequest) {
		DatabaseResponse databaseResponse = new DatabaseResponse();
		Map<String, Object> valueMap = databaseRequest.getValues();

		String displayColumnName = ((String) valueMap.get("columnName")).trim();
		String columnType = (String) valueMap.get("columnType");

		// List<Error> errorResponses = new ArrayList<Error>();
		// Error errorRes1 = stringValidator.checkMaxLength(displayColumnName,
		// CONFIG.COLUMN_NAME_MAX_LENGTH,
		// "Column Name can't be more than " + CONFIG.COLUMN_NAME_MAX_LENGTH);
		// if (errorRes1 != null) {
		// errorResponses.add(errorRes1);
		// }
		// Error errorRes2 = stringValidator.checkMinLength(displayColumnName,
		// CONFIG.COLUMN_NAME_MIN_LENGTH,
		// "Column Name can't be less than " + CONFIG.COLUMN_NAME_MIN_LENGTH);
		// if (errorRes2 != null) {
		// errorResponses.add(errorRes2);
		// }

		Column column = null;
		// if (errorResponses.isEmpty()) {
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
		// }
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
			String tableId = (String) valueMap.get(RequestFieldName.ID);
			String displayColumnName = ((String) valueMap.get(RequestFieldName.NAME)).trim();
			String columnName = displayColumnName.replace(" ", "_").toUpperCase();
			String columnKey = RandomService.generateKey(RandomService.PREFIX_COLUMN);

			Table table = getTableById(tableId);

			numberColumn.setId(columnKey);
			numberColumn.setName(columnName);
			numberColumn.setDisplayName(displayColumnName);
			populateColumnValue(numberColumn);

//			dbRepo.create(numberColumn);
//			column = dbRepo.save(numberColumn);

//			addColumn(table, column);
		}
		return numberColumn;
	}

	private void addColumn(Table table, Column column) {
		table.getColumnMap().put(column.getDisplayName(), column);
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

		String id = valueMap.get(RequestFieldName.ID) != null ? ((String) valueMap.get(RequestFieldName.ID)).trim()
				: RandomService.generateKey(RandomService.PREFIX_TABLE);
		String displayTableName = valueMap.get(RequestFieldName.DISPLAY_NAME) != null
				? ((String) valueMap.get(RequestFieldName.DISPLAY_NAME)).trim()
				: "UNTITLED_" + new Date().getTime();
//		if (displayTableName == null) {
//			displayTableName = valueMap.get(RequestFieldName.NAME) != null
//					? ((String) valueMap.get(RequestFieldName.NAME)).trim()
//					: "UNTITLED_" + new Date().getTime();
//		}

		stringValidator.checkNotBlank(displayTableName, "Table name is missing.");
		String tableName = displayTableName.replace(" ", "_").toUpperCase();

		stringValidator.checkMaxLength(displayTableName, CONFIG.TABLE_NAME_MAX_LENGTH,
				"Table Name can't be more than " + CONFIG.TABLE_NAME_MAX_LENGTH);
		stringValidator.checkMinLength(displayTableName, CONFIG.TABLE_NAME_MIN_LENGTH,
				"Table Name can't be less than " + CONFIG.TABLE_NAME_MIN_LENGTH);
		// TODO check duplicate for Table name

		Table table = getSampleTable(displayTableName, tableName);
		TableView tableView = getTableView(table);

		dbRepo.create(table);

		writeTableDetails(table);

		addTable(table);

		databaseResponse.setTable(table);
		return databaseResponse;
	}

	private TableView getTableView(Table table) {
		TableView tableView = new TableView();
		String tableViewKey = RandomService.generateKey(RandomService.PREFIX_VIEW);
		tableView.setId(tableViewKey);
		tableView.setDisplayName("DEFAULT_VIEW_" + tableViewKey);
		tableView.setTableId(table.getId());
		tableView.setDescription("");
		tableView.setIcon("");
		tableView.setLastUpdatedAt(new Date());
		tableView.setLastUpdatedBy(getLoggedInUser());

		Map<String, ColumnView> columnViewMap = new HashMap<String, ColumnView>();

		Map<String, Column> columnMap = table.getColumnMap();
//		columnViewMap.put(columnMap.get(DefaultColumn.NAME).getId());
//		columnViewMap.add(columnMap.get(DefaultColumn.DESCRIPTION).getId());

		return null;
	}

	private void writeTableDetails(Table table) {
		try {
			String jsonString;
			jsonString = objectMapper.writeValueAsString(table);
//			System.out.println(jsonString);

			String filePath = "C:\\jameel works\\sourceCode\\cloudApps_app_config\\";
			try (FileWriter file = new FileWriter(filePath + table.getId() + ".json")) {

				file.write(jsonString);
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private Table getSampleTable(String displayTableName, String tableName) {
		StringColumn nameColumn = new StringColumn();
		String nameColumnKey = RandomService.generateKey(RandomService.PREFIX_COLUMN);
		nameColumn.setId(nameColumnKey);
		nameColumn.setName(DefaultColumn.NAME);
		nameColumn.setDisplayName(DefaultColumn.NAME);
		nameColumn.setColumnType(ColumnType.SINGLELINE);

		StringColumn descColumn = new StringColumn();
		String descColumnKey = RandomService.generateKey(RandomService.PREFIX_COLUMN);
		descColumn.setId(descColumnKey);
		descColumn.setName(DefaultColumn.DESCRIPTION);
		descColumn.setDisplayName(DefaultColumn.DESCRIPTION);
		descColumn.setColumnType(ColumnType.MULTILINE);

		String tableKey = RandomService.generateKey(RandomService.PREFIX_TABLE);
		Table table = new Table();
		table.setId(tableKey);
		table.setDisplayName(displayTableName);
		table.setName(tableName);
		table.setLastUpdatedAt(new Date());
		table.setLastUpdatedBy(getLoggedInUser());
		table.getColumnMap().put(nameColumn.getDisplayName(), nameColumn);
		table.getColumnMap().put(descColumn.getDisplayName(), descColumn);

		table.setDatabaseEngine(DATABASE_ENGINE.INNODB);
		table.setCharset("UTF8");
		table.setDatabaseName(CONFIG.BASE_DB_NAME);
		return table;
	}

	private void addTable(Table table) {
		tableIDMap.put(table.getId(), table);
		tableNameMap.put(table.getDisplayName(), table);
	}

	private Table getTableById(String id) {
		return tableIDMap.get(id);
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

		String tableId = valueMap.get(RequestFieldName.ID) != null ? (String) valueMap.get(RequestFieldName.ID) : null;
		String icon = valueMap.get(RequestFieldName.ICON) != null ? (String) valueMap.get(RequestFieldName.ICON) : null;
		String description = valueMap.get(RequestFieldName.DESCRIPTION) != null
				? (String) valueMap.get(RequestFieldName.DESCRIPTION)
				: null;

		objectValidator.checkNotNull(tableId, "Table Id is missing.");

		Table table = getTableById(tableId);
		// table.setIcon(icon);
		// table.setDescription(description);
		// table.setLastUpdatedAt(new Date());
		// table.setLastUpdatedBy(getLoggedInUser());

//		table = dbRepo.update(table);
		databaseResponse.setTable(table);
		return databaseResponse;
	}
}