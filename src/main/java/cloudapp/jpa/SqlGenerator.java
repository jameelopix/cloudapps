package cloudapp.jpa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cloudapp.common.ColumnName;
import cloudapp.common.StringUtils;
import cloudapp.common.Symbol;
import cloudapp.entity.Column;
import cloudapp.entity.ColumnType;
import cloudapp.entity.Record;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;

public class SqlGenerator {

	private static final int PRIMARY_KEY_DEFAULT_SIZE = 17;
	private static final int SINGLE_LINE_DEFAULT_SIZE = 255;
	private static final int MULTI_LINE_DEFAULT_SIZE = 4096;
	private static final int INT_DEFAULT_SIZE = 11;
	private static final int RATING_DEFAULT_SIZE = 1;

	private static String CREATE_TABLE = "CREATE TABLE";
	private static String DELETE_FROM = "DELETE FROM";
	private static String INSERT_INTO = "INSERT INTO";
	private static String UPDATE = "UPDATE";
	private static String ENGINE = "ENGINE";
	private static String DEFAULT_CHARSET = "DEFAULT CHARSET";
	private static String NOT_NULL = "NOT NULL";
	private static String PRIMARY_KEY = "PRIMARY KEY";
	private static String UNIQUE_INDEX = "UNIQUE INDEX";
	private static String UNIQUE = "UNIQUE";
	private static String ASC = "ASC";
	private static String SET = "SET";
	private static String WHERE = "WHERE";
	private static String VALUES = "VALUES";
	private static String SELECT_ALL_FORM = "SELECT * FROM";

	private static String VARCHAR = "VARCHAR";
	private static String INT = "INT";
	private static String DOUBLE = "DOUBLE";
	private static String DECIMAL = "DECIMAL";
	private static String DATETIME = "DATETIME";

	private static String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

	public String findTableEntryByID(Table table, String id) {
		String idString = String.join(Symbol.EQUALS, ColumnName.ID, getStringValue(id));

		List<String> tokens = new LinkedList<String>();
		tokens.add(SELECT_ALL_FORM);
		tokens.add(getFullTableName(table));
		tokens.add(WHERE);
		tokens.add(idString);

		return String.join(Symbol.SPACE, tokens) + Symbol.SEMI_COLON;
	}

	public String deleteTableEntry(Table table, String id) {
		String idString = String.join(Symbol.EQUALS, ColumnName.ID, getStringValue(id));

		List<String> tokens = new LinkedList<String>();
		tokens.add(DELETE_FROM);
		tokens.add(getFullTableName(table));
		tokens.add(WHERE);
		tokens.add(idString);

		return String.join(Symbol.SPACE, tokens) + Symbol.SEMI_COLON;
	}

	public String createTable(Table table) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(CREATE_TABLE);
		tokens.add(getFullTableName(table));

		tokens.add(Symbol.OPEN_PARENTHESES);
		tokens.add(getColumnLines(table.getColumnMap()));
		tokens.add(Symbol.CLOSE_PARENTHESES);
		tokens.add(getDatabaseEngine(table));
		tokens.add(getCharacterSet(table));

		return String.join(Symbol.SPACE, tokens) + Symbol.SEMI_COLON;
	}

	public String updateTableEntry(Table table, Record record) {
		// UPDATE `deletenow`.`testtable`
		// SET
		// `NAME` = 'Asraf Abd',
		// `DESCRIPTION` = 'this is test ddfdfd message',
		// `LAST_UPDATED_BY` = 'Jameel Md',
		// `LAST_UPDATED_AT` = '2020-06-15 03:04:05'
		// WHERE
		// `ID` = 'REC_102';

		String idString = String.join(Symbol.EQUALS, ColumnName.ID, getStringValue(record.getId()));
		String lastUpdatedBy = String.join(Symbol.EQUALS, ColumnName.LAST_UPDATED_BY,
				getStringValue(record.getLastUpdatedBy()));
		String lastUpdatedAt = String.join(Symbol.EQUALS, ColumnName.LAST_UPDATED_AT,
				getDateValue(record.getLastUpdatedAt()));

		List<String> updateList = new ArrayList<>();
		Map<String, Column> columnMap = table.getColumnMap();
		Map<String, Object> valueMap = record.getValues();
		for (Entry<String, Object> valueEntry : valueMap.entrySet()) {
			String columnName = valueEntry.getKey();
			Column column = columnMap.get(columnName);
			updateList.add(String.join(Symbol.EQUALS, column.getName(), getValue(valueEntry.getValue(), column)));
			// columnList.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
			// valueList.add(getValue(valueEntry.getValue(), column));
		}
		updateList.add(lastUpdatedAt);
		updateList.add(lastUpdatedBy);

		String updateString = String.join(Symbol.COMMA, updateList);

		List<String> tokens = new LinkedList<String>();
		tokens.add(UPDATE);
		tokens.add(getFullTableName(table));
		tokens.add(SET);
		tokens.add(updateString);
		tokens.add(WHERE);
		tokens.add(idString);

		return String.join(Symbol.SPACE, tokens) + Symbol.SEMI_COLON;
	}

	public String insertTableEntry(Table table, Record record) {
		StringBuilder columnString = new StringBuilder();
		StringBuilder valueString = new StringBuilder();

		List<String> columnList = new ArrayList<>();
		List<String> valueList = new ArrayList<>();

		columnList.add(StringUtils.enclose(ColumnName.ID, Symbol.BACK_QUOTE));
		columnList.add(StringUtils.enclose(ColumnName.LAST_UPDATED_BY, Symbol.BACK_QUOTE));
		columnList.add(StringUtils.enclose(ColumnName.LAST_UPDATED_AT, Symbol.BACK_QUOTE));

		String id = getStringValue(record.getId());
		String lastUpdatedBy = getStringValue(record.getLastUpdatedBy());
		String lastUpdatedAt = getDateValue(record.getLastUpdatedAt());
		valueList.add(id);
		valueList.add(lastUpdatedBy);
		valueList.add(lastUpdatedAt);

		Map<String, Column> columnMap = table.getColumnMap();
		Map<String, Object> valueMap = record.getValues();
		for (Entry<String, Object> valueEntry : valueMap.entrySet()) {
			String columnName = valueEntry.getKey();
			Column column = columnMap.get(columnName);
			columnList.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
			valueList.add(getValue(valueEntry.getValue(), column));
		}
		columnString.append(String.join(Symbol.COMMA, columnList));
		valueString.append(String.join(Symbol.COMMA, valueList));

		List<String> tokens = new LinkedList<String>();
		tokens.add(INSERT_INTO);
		tokens.add(getFullTableName(table));
		tokens.add(Symbol.OPEN_PARENTHESES);
		tokens.add(columnString.toString());
		tokens.add(Symbol.CLOSE_PARENTHESES);
		tokens.add(VALUES);
		tokens.add(Symbol.OPEN_PARENTHESES);
		tokens.add(valueString.toString());
		tokens.add(Symbol.CLOSE_PARENTHESES);

		return String.join(Symbol.SPACE, tokens) + Symbol.SEMI_COLON;
	}

	private String getStringValue(String value) {
		return StringUtils.enclose(value, Symbol.SINGLE_QUOTE);
	}

	private String getNumberValue(Integer value) {
		return Integer.toString(value);
	}

	private String getDateValue(Date value) {
		return StringUtils.enclose(simpleDateFormat.format(value), Symbol.SINGLE_QUOTE);
	}

	private String getValue(Object value, Column column) {
		ColumnType columnType = column.getColumnType();
		switch (columnType) {
		case SINGLELINE:
		case MULTILINE:
		case PHONE_NO:
		case EMAIL:
			return getStringValue((String) value);
		case RATING:
			return getNumberValue((Integer) value);
		case DATE_TIME:
		case DATE:
			return getDateValue((Date) value);
		default:
			break;
		}
		return null;
	}

	private String addPrimaryKey() {
		List<String> tokens = new LinkedList<String>();
		tokens.add(PRIMARY_KEY);
		tokens.add(StringUtils.enclose(ColumnName.ID, Symbol.OPEN_PARENTHESES, Symbol.CLOSE_PARENTHESES));
		return String.join(Symbol.SPACE, tokens);
	}

	private String getColumnLines(Map<String, Column> columnMap) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(getPrimaryKeyLine());
		for (Entry<String, Column> columnEntry : columnMap.entrySet()) {
			String columnLine = getColumnLine(columnEntry.getValue());
			if (columnLine != null) {
				tokens.add(columnLine);
			}
		}
		tokens.add(getLastUpdatedByLine());
		tokens.add(getLastUpdatedAtLine());
		tokens.add(addPrimaryKey());
		for (Entry<String, Column> columnEntry : columnMap.entrySet()) {
			String indexLine = getIndexLine(columnEntry.getValue());
			if (indexLine != null) {
				tokens.add(indexLine);
			}
		}
		return String.join(Symbol.COMMA, tokens);
	}

	// CREATE TABLE `deletenow`.`new_table` (
	// `idnew_table` INT NOT NULL,
	// `newnew` VARCHAR(45) NULL,
	// PRIMARY KEY (`idnew_table`),
	// UNIQUE INDEX `newnew_UNIQUE` (`newnew` ASC));

	private String getIndexLine(Column column) {
		String result = null;
		if (column.isUnique()) {
			List<String> tokens = new LinkedList<String>();
			tokens.add(UNIQUE_INDEX);

			String indexName = StringUtils.enclose(String.join(Symbol.UNDER_SCORE, column.getName(), UNIQUE),
					Symbol.BACK_QUOTE);
			tokens.add(indexName);

			String indexType = StringUtils.enclose(
					String.join(Symbol.SPACE, StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE), ASC),
					Symbol.OPEN_PARENTHESES, Symbol.CLOSE_PARENTHESES);

			tokens.add(indexType);

			result = String.join(Symbol.SPACE, tokens);
		}
		return result;
	}

	private String getLastUpdatedAtLine() {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(ColumnName.LAST_UPDATED_AT, Symbol.BACK_QUOTE));
		tokens.add(DATETIME);
		tokens.add(NOT_NULL);
		return String.join(Symbol.SPACE, tokens);
	}

	private void addColumn(Column column) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(column.getName());
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(getColumnLine(column));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
	}

	public String getColumnLine(Column column) {
		switch (column.getColumnType()) {
		case SINGLELINE:
			return getSingleLineType(column);
		case MULTILINE:
			return getMultiLineType(column);
		case CURRENCY:
			return getCurrencyType(column);
		case RATING:
			return getRatingType(column);
		case PHONE_NO:
			return getPhoneNoType(column);
		case EMAIL:
			return getEmailType(column);
		default:
			return null;
		}
	}

	private String getCurrencyType(Column column) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
		tokens.add(getColumnType(DECIMAL, 10, 2));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	private String getPrimaryKeyLine() {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(ColumnName.ID, Symbol.BACK_QUOTE));
		tokens.add(String.join(Symbol.EMPTY_QUOTE, VARCHAR, Symbol.OPEN_PARENTHESES,
				Integer.toString(PRIMARY_KEY_DEFAULT_SIZE), Symbol.CLOSE_PARENTHESES));
		tokens.add(NOT_NULL);
		return String.join(Symbol.SPACE, tokens);
	}

	private String getLastUpdatedByLine() {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(ColumnName.LAST_UPDATED_BY, Symbol.BACK_QUOTE));
		tokens.add(String.join(Symbol.EMPTY_QUOTE, VARCHAR, Symbol.OPEN_PARENTHESES,
				Integer.toString(PRIMARY_KEY_DEFAULT_SIZE), Symbol.CLOSE_PARENTHESES));
		tokens.add(NOT_NULL);
		return String.join(Symbol.SPACE, tokens);
	}

	private String getEmailType(Column column) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
		tokens.add(getColumnType(VARCHAR, SINGLE_LINE_DEFAULT_SIZE));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	private String getPhoneNoType(Column column) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
		tokens.add(getColumnType(VARCHAR, SINGLE_LINE_DEFAULT_SIZE));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	private String getRatingType(Column column) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
		tokens.add(getColumnType(INT, RATING_DEFAULT_SIZE));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	private String getMultiLineType(Column column) {
		List<String> tokens = new LinkedList<String>();

		tokens.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
		Integer maxLengthAllowed = ((StringColumn) column).getMaxLengthAllowed();
		if (maxLengthAllowed == null) {
			maxLengthAllowed = new Integer(MULTI_LINE_DEFAULT_SIZE);
		}

		tokens.add(getColumnType(VARCHAR, maxLengthAllowed));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
		return String.join(Symbol.SPACE, tokens);
	}

	private String getSingleLineType(Column column) {
		List<String> tokens = new LinkedList<String>();

		tokens.add(StringUtils.enclose(column.getName(), Symbol.BACK_QUOTE));
		Integer maxLengthAllowed = ((StringColumn) column).getMaxLengthAllowed();
		if (maxLengthAllowed == null) {
			maxLengthAllowed = new Integer(SINGLE_LINE_DEFAULT_SIZE);
		}

		tokens.add(getColumnType(VARCHAR, maxLengthAllowed));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
		return String.join(Symbol.SPACE, tokens);
	}

	private String getColumnType(String type, int length) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(type);
		tokens.add(StringUtils.enclose(String.valueOf(length), Symbol.OPEN_PARENTHESES, Symbol.CLOSE_PARENTHESES));
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	private String getColumnType(String type, int precision, int scale) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(type);
		String lenString = String.join(Symbol.COMMA, String.valueOf(precision), String.valueOf(scale));
		tokens.add(StringUtils.enclose(String.valueOf(lenString), Symbol.OPEN_PARENTHESES, Symbol.CLOSE_PARENTHESES));
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public String getCharacterSet(Table table) {
		String charset = table.getCharset() != null ? table.getCharset() : "UTF8";
		List<String> tokens = new LinkedList<String>();
		tokens.add(DEFAULT_CHARSET);
		tokens.add(Symbol.EQUALS);
		tokens.add(charset);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public String getDatabaseEngine(Table table) {
		String engine = table.getDatabaseEngine() != null ? table.getDatabaseEngine().toString()
				: DATABASE_ENGINE.INNODB.toString();
		List<String> tokens = new LinkedList<String>();
		tokens.add(ENGINE);
		tokens.add(Symbol.EQUALS);
		tokens.add(engine);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public String getFullTableName(Table table) {
		List<String> tokens = new LinkedList<String>();
		if (table.getDatabaseName() != null) {
			tokens.add(Symbol.BACK_QUOTE);
			tokens.add(table.getDatabaseName());
			tokens.add(Symbol.BACK_QUOTE);
			tokens.add(Symbol.DOT);
		}
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(table.getName());
		tokens.add(Symbol.BACK_QUOTE);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public String getTableName(Table table) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(table.getName());
		tokens.add(Symbol.BACK_QUOTE);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}
}