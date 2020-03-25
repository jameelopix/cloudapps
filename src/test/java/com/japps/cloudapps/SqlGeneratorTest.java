package com.japps.cloudapps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cloudapp.entity.ColumnType;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;
import cloudapp.jpa.SqlGenerator;

public class SqlGeneratorTest {

	@Test
	public void testCreateTable() {
		Table table = new Table();
		table.setDatabaseName("TESTDB");
		table.setName("TESTTABLE");

		System.out.println(SqlGenerator.createTable(table));
	}

	@Test
	public void testGetFullTableName() {
		Table table = new Table();
		table.setDatabaseName("TESTDB");
		table.setName("TESTTABLE");

		assertEquals("`TESTDB`.`TESTTABLE`", SqlGenerator.getFullTableName(table), "Mismatch in Table Full name");

		table.setDatabaseName(null);
		assertEquals("`TESTTABLE`", SqlGenerator.getFullTableName(table), "Mismatch in Table Full name");
	}

	@Test
	public void testGetTableName() {
		Table table = new Table();
		table.setDatabaseName("TESTDB");
		table.setName("TESTTABLE");

		assertEquals("`TESTTABLE`", SqlGenerator.getTableName(table), "Mismatch in Table Full name");
	}

	@Test
	public void testGetColumnSize() {
		StringColumn stringColumn = new StringColumn();
		stringColumn.setColumnType(ColumnType.SINGLELINE);
		stringColumn.setMaxLengthAllowed(100);
		assertEquals("VARCHAR(100)", SqlGenerator.getColumnSize(stringColumn), "Mismatch in Column Size");

		stringColumn.setMaxLengthAllowed(null);
		assertEquals("VARCHAR(255)", SqlGenerator.getColumnSize(stringColumn), "Mismatch in Column Size");
	}
}
