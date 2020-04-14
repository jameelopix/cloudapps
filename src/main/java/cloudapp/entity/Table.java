package cloudapp.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloudapp.jpa.DATABASE_ENGINE;

public class Table {

	private String id;
	private String name;
	private String displayName;
	private String icon;
	private String description;
	private String lastUpdatedBy;
	private Date lastUpdatedAt;

	private String databaseName;
	private DATABASE_ENGINE databaseEngine;
	private String charset;

	// private List<Column> columns = new LinkedList<Column>();
	private Map<String, Column> columnMap = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public DATABASE_ENGINE getDatabaseEngine() {
		return databaseEngine;
	}

	public void setDatabaseEngine(DATABASE_ENGINE databaseEngine) {
		this.databaseEngine = databaseEngine;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, Column> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, Column> columnMap) {
		this.columnMap = columnMap;
	}

	// public List<Column> getColumns() {
	// return columns;
	// }

}