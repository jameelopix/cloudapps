package com.japps.cloudapps;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cloudapp.common.ColumnName;
import cloudapp.configuration.JdbcConfig;
import cloudapp.entity.Column;
import cloudapp.entity.ColumnType;
import cloudapp.entity.NumberColumn;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;
import cloudapp.jpa.DbRepo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
// @Ignore("Should be tested with proper MySQL setup only")
public class DbRepoTest {

	private final static String DB_NAME = "DELETENOW";
	private final static String TABLE_NAME = "TESTTABLE";

	@Autowired
	DbRepo dbRepo;

	@Test
	public void testCreateTable() {
		System.out.println("DbRepoTest.testCreateTable()");

		Table table = new Table();
		table.setDatabaseName(DB_NAME);
		table.setName(TABLE_NAME);

		Map<String, Column> columnMap = new HashMap<String, Column>();
		StringColumn nameColumn = new StringColumn();
		nameColumn.setColumnType(ColumnType.SINGLELINE);
		nameColumn.setName(ColumnName.NAME);

		StringColumn descColumn = new StringColumn();
		descColumn.setColumnType(ColumnType.MULTILINE);
		descColumn.setName(ColumnName.DESCRIPTION);

		NumberColumn numberColumn = new NumberColumn();
		numberColumn.setColumnType(ColumnType.CURRENCY);
		numberColumn.setName("CURR");

		columnMap.put(nameColumn.getName(), nameColumn);
		columnMap.put(descColumn.getName(), descColumn);
		columnMap.put(numberColumn.getName(), numberColumn);
		table.setColumnMap(columnMap);

		dbRepo.create(table);
	}
}
