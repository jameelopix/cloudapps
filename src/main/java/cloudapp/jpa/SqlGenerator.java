package cloudapp.jpa;

import java.util.LinkedList;
import java.util.List;

import cloudapp.common.StringUtils;
import cloudapp.common.Symbol;
import cloudapp.entity.Column;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;

public class SqlGenerator {

	private static String CREATE_TABLE = "CREATE TABLE";
	private static String ENGINE = "ENGINE";
	private static String DEFAULT_CHARSET = "DEFAULT CHARSET";
	private static String NOT_NULL = "NOT NULL";

	private static String VARCHAR = "VARCHAR";

	public static String createTable(Table table) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(CREATE_TABLE);
		tokens.add(getFullTableName(table));

		tokens.add(Symbol.OPEN_PARENTHESES);
		tokens.add(Symbol.CLOSE_PARENTHESES);
		tokens.add(getDatabaseEngine(table));
		tokens.add(getCharacterSet(table));

		return String.join(Symbol.SPACE, tokens) + Symbol.SEMI_COLON;
	}

	private void addPrimaryKey(Table table) {

	}

	private void addColumn(Column column) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(column.getName());
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(getColumnSize(column));
		if (column.isNotNull()) {
			tokens.add(NOT_NULL);
		}
	}

	public static String getColumnSize(Column column) {
		List<String> tokens = new LinkedList<String>();

		switch (column.getColumnType()) {
		case SINGLELINE:
			Integer maxLengthAllowed = ((StringColumn) column).getMaxLengthAllowed();
			if (maxLengthAllowed == null) {
				maxLengthAllowed = new Integer(255);
			}

			tokens.add(VARCHAR);
			tokens.add(Symbol.OPEN_PARENTHESES);
			tokens.add(maxLengthAllowed.toString());
			tokens.add(Symbol.CLOSE_PARENTHESES);
//			tokens.add(StringUtils.enclose(maxLengthAllowed.toString(), Symbol.OPEN_PARENTHESES,
//					Symbol.CLOSE_PARENTHESES));
			break;
		default:
			break;
		}
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public static String getCharacterSet(Table table) {
		String charset = table.getCharset() != null ? table.getCharset() : "UTF8";
		List<String> tokens = new LinkedList<String>();
		tokens.add(DEFAULT_CHARSET);
		tokens.add(Symbol.EQUALS);
		tokens.add(charset);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public static String getDatabaseEngine(Table table) {
		String engine = table.getDatabaseEngine() != null ? table.getDatabaseEngine().toString()
				: DATABASE_ENGINE.INNODB.toString();
		List<String> tokens = new LinkedList<String>();
		tokens.add(ENGINE);
		tokens.add(Symbol.EQUALS);
		tokens.add(engine);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}

	public static String getFullTableName(Table table) {
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

	public static String getTableName(Table table) {
		List<String> tokens = new LinkedList<String>();
		tokens.add(Symbol.BACK_QUOTE);
		tokens.add(table.getName());
		tokens.add(Symbol.BACK_QUOTE);
		return String.join(Symbol.EMPTY_QUOTE, tokens);
	}
}