package com.japps.cloudapps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cloudapp.common.ColumnName;
import cloudapp.entity.Column;
import cloudapp.entity.ColumnType;
import cloudapp.entity.NumberColumn;
import cloudapp.entity.Record;
import cloudapp.entity.StringColumn;
import cloudapp.entity.Table;
import cloudapp.jpa.SqlGenerator;

public class SqlGeneratorTest {

	SqlGenerator sqlGenerator = new SqlGenerator();

	@Test
	public void testFindTableEntryByID() {
		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		System.out.println(sqlGenerator.findTableEntryByID(table, "REC_102"));
	}

	@Test
	public void testDeleteTableEntry() {
		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		Record record = new Record();
		record.setId("REC_102");

		// System.out.println(sqlGenerator.deleteTableEntry(table, "REC_102"));
		String expectedSql = "DELETE FROM `DELETENOW`.`TESTTABLE` WHERE ID='REC_102';";
		assertEquals(expectedSql, sqlGenerator.deleteTableEntry(table, "REC_102"), "Mismatch in Insert Sql creation.");
	}

	@Test
	public void testUpdateTableEntry() {
		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		Map<String, Column> columnMap = new HashMap<String, Column>();
		StringColumn nameColumn = new StringColumn();
		nameColumn.setColumnType(ColumnType.SINGLELINE);
		nameColumn.setName(ColumnName.NAME);

		StringColumn descColumn = new StringColumn();
		descColumn.setColumnType(ColumnType.MULTILINE);
		descColumn.setName(ColumnName.DESCRIPTION);

		columnMap.put(nameColumn.getName(), nameColumn);
		columnMap.put(descColumn.getName(), descColumn);
		table.setColumnMap(columnMap);

		Date updatedDate = new Date(120, 5, 25);
		Record record = new Record();
		record.setId("REC_102");
		record.setLastUpdatedBy("Mohamed Jameel");
		record.setLastUpdatedAt(updatedDate);

		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(ColumnName.NAME, "Asraf Abdullah");
		valueMap.put(ColumnName.DESCRIPTION, "test message");
		record.setValues(valueMap);

		// System.out.println(sqlGenerator.updateTableEntry(table, record));

		String expectedSql = "UPDATE `DELETENOW`.`TESTTABLE` SET DESCRIPTION='test message',NAME='Asraf Abdullah',LAST_UPDATED_AT='2020-06-25 00:00:00',LAST_UPDATED_BY='Mohamed Jameel' WHERE ID='REC_102';";
		assertEquals(expectedSql, sqlGenerator.updateTableEntry(table, record), "Mismatch in Insert Sql creation.");
	}

	@Test
	public void testCreateTableEntry() {

		// String pattern = "YYYY-MM-DD HH:mm:ss";
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		// String date = simpleDateFormat.format(new Date());
		// System.out.println(date);

		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		Map<String, Column> columnMap = new HashMap<String, Column>();
		StringColumn nameColumn = new StringColumn();
		nameColumn.setColumnType(ColumnType.SINGLELINE);
		nameColumn.setName(ColumnName.NAME);

		StringColumn descColumn = new StringColumn();
		descColumn.setColumnType(ColumnType.MULTILINE);
		descColumn.setName(ColumnName.DESCRIPTION);

		columnMap.put(nameColumn.getName(), nameColumn);
		columnMap.put(descColumn.getName(), descColumn);
		table.setColumnMap(columnMap);

		Date updatedDate = new Date(120, 5, 15);
		Record record = new Record();
		record.setId("REC_102903904");
		record.setLastUpdatedBy("Jameel");
		record.setLastUpdatedAt(updatedDate);

		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(ColumnName.NAME, "Asraf");
		valueMap.put(ColumnName.DESCRIPTION, "this is test message");
		record.setValues(valueMap);

		String expectedSql = "INSERT INTO `DELETENOW`.`TESTTABLE` ( `ID`,`LAST_UPDATED_BY`,`LAST_UPDATED_AT`,`DESCRIPTION`,`NAME` ) VALUES ( 'REC_102903904','Jameel','2020-06-15 00:00:00','this is test message','Asraf' );";
		assertEquals(expectedSql, sqlGenerator.insertTableEntry(table, record), "Mismatch in Insert Sql creation.");
		// System.out.println(sqlGenerator.createTableEntry(table, record));
	}

	@Test
	public void testCreateTableSingleLine() {
		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		Map<String, Column> columnMap = new HashMap<String, Column>();
		StringColumn nameColumn = new StringColumn();
		nameColumn.setColumnType(ColumnType.SINGLELINE);
		nameColumn.setName("NAME");

		columnMap.put(nameColumn.getName(), nameColumn);
		table.setColumnMap(columnMap);

		String expectedSql = "CREATE TABLE `DELETENOW`.`TESTTABLE` ( `ID` VARCHAR(17) NOT NULL,`NAME` VARCHAR(255),`LAST_UPDATED_BY` VARCHAR(17) NOT NULL,`LAST_UPDATED_AT` DATETIME NOT NULL,PRIMARY KEY (ID) ) ENGINE=INNODB DEFAULT CHARSET=UTF8;";
		assertEquals(expectedSql, sqlGenerator.createTable(table), "Mismatch in Single Line Table Creation.");
	}

	@Test
	public void testCreateTableMultiLine() {
		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		Map<String, Column> columnMap = new HashMap<String, Column>();
		StringColumn descColumn = new StringColumn();
		descColumn.setColumnType(ColumnType.MULTILINE);
		descColumn.setName("DESC");

		columnMap.put(descColumn.getName(), descColumn);
		table.setColumnMap(columnMap);

		String expectedSql = "CREATE TABLE `DELETENOW`.`TESTTABLE` ( `ID` VARCHAR(17) NOT NULL,`DESC` VARCHAR(4096),`LAST_UPDATED_BY` VARCHAR(17) NOT NULL,`LAST_UPDATED_AT` DATETIME NOT NULL,PRIMARY KEY (ID) ) ENGINE=INNODB DEFAULT CHARSET=UTF8;";
		assertEquals(expectedSql, sqlGenerator.createTable(table), "Mismatch in Multi Line Table Creation.");
	}

	@Test
	public void testCreateTable() {
		Table table = new Table();
		table.setDatabaseName("DELETENOW");
		table.setName("TESTTABLE");

		Map<String, Column> columnMap = new HashMap<String, Column>();
		StringColumn nameColumn = new StringColumn();
		nameColumn.setColumnType(ColumnType.SINGLELINE);
		nameColumn.setName("NAME");
		// nameColumn.setUnique(true);
		StringColumn descColumn = new StringColumn();
		descColumn.setColumnType(ColumnType.MULTILINE);
		descColumn.setName("DESC");
		// descColumn.setUnique(true);
		Column ratingColumn = new Column();
		ratingColumn.setColumnType(ColumnType.RATING);
		ratingColumn.setName("RATING");
		// ratingColumn.setUnique(true);
		Column emailColumn = new Column();
		emailColumn.setColumnType(ColumnType.EMAIL);
		emailColumn.setName("EMAIL");
		// emailColumn.setUnique(true);
		Column phoneNoColumn = new Column();
		phoneNoColumn.setColumnType(ColumnType.PHONE_NO);
		phoneNoColumn.setName("PHONE_NO");
		// phoneNoColumn.setUnique(true);

		columnMap.put("NAME", nameColumn);
		columnMap.put("DESC", descColumn);
		columnMap.put("RATING", ratingColumn);
		columnMap.put("EMAIL", emailColumn);
		columnMap.put("PHONE_NO", phoneNoColumn);

		table.setColumnMap(columnMap);

		// System.out.println(SqlGenerator.createTable(table));
	}

	@Test
	public void testGetFullTableName() {
		Table table = new Table();
		table.setDatabaseName("TESTDB");
		table.setName("TESTTABLE");

		assertEquals("`TESTDB`.`TESTTABLE`", sqlGenerator.getFullTableName(table), "Mismatch in Table Full name");

		table.setDatabaseName(null);
		assertEquals("`TESTTABLE`", sqlGenerator.getFullTableName(table), "Mismatch in Table Full name");
	}

	@Test
	public void testGetTableName() {
		Table table = new Table();
		table.setDatabaseName("TESTDB");
		table.setName("TESTTABLE");

		assertEquals("`TESTTABLE`", sqlGenerator.getTableName(table), "Mismatch in Table Full name");
	}

	@Test
	public void testStringColumnType() {
		StringColumn stringColumn = new StringColumn();
		stringColumn.setColumnType(ColumnType.SINGLELINE);
		stringColumn.setMaxLengthAllowed(100);
		stringColumn.setName("full name");
		stringColumn.setNotNull(true);
		// System.out.println(SqlGenerator.getColumnLine(stringColumn));
		// assertEquals("VARCHAR(100)", SqlGenerator.getColumnLine(stringColumn),
		// "Mismatch in Column Size");

		stringColumn.setName("country name");
		stringColumn.setNotNull(true);
		stringColumn.setMaxLengthAllowed(null);
		// System.out.println(SqlGenerator.getColumnLine(stringColumn));
		// assertEquals("VARCHAR(255)", SqlGenerator.getColumnLine(stringColumn),
		// "Mismatch in Column Size");
	}

	@Test
	public void testDerivedStringColumnType() {
		StringColumn stringColumn = new StringColumn();

		stringColumn.setName("office email");
		stringColumn.setColumnType(ColumnType.EMAIL);
		stringColumn.setNotNull(true);

		// System.out.println(SqlGenerator.getColumnLine(stringColumn));
		// assertEquals("VARCHAR(255)", SqlGenerator.getColumnLine(stringColumn),
		// "Mismatch in Email Column Size");
		stringColumn.setName("office phone no");
		stringColumn.setColumnType(ColumnType.PHONE_NO);
		stringColumn.setNotNull(true);
		// System.out.println(SqlGenerator.getColumnLine(stringColumn));
		// assertEquals("VARCHAR(255)", SqlGenerator.getColumnLine(stringColumn),
		// "Mismatch in Phone Column Size");
	}

	@Test
	public void testDerivedNumberColumnType() {
		NumberColumn numberColumn = new NumberColumn();

		numberColumn.setName("Item Rating");
		numberColumn.setColumnType(ColumnType.RATING);
		numberColumn.setNotNull(true);
		// System.out.println(SqlGenerator.getColumnLine(numberColumn));
		// assertEquals("INT(11)", SqlGenerator.getColumnLine(numberColumn), "Mismatch
		// in Rating Column Size");
		// stringColumn.setColumnType(ColumnType.PHONE_NO);
		// assertEquals("VARCHAR(255)", SqlGenerator.getColumnLine(stringColumn),
		// "Mismatch in Phone Column Size");
	}

	@Test
	public void testGetPrimaryLine() {
		NumberColumn numberColumn = new NumberColumn();

		numberColumn.setName("Item Rating");
		numberColumn.setColumnType(ColumnType.RATING);
		numberColumn.setNotNull(true);
		// System.out.println(SqlGenerator.getColumnLine(numberColumn));
		// assertEquals("INT(11)", SqlGenerator.getColumnLine(numberColumn), "Mismatch
		// in Rating Column Size");
		// stringColumn.setColumnType(ColumnType.PHONE_NO);
		// assertEquals("VARCHAR(255)", SqlGenerator.getColumnLine(stringColumn),
		// "Mismatch in Phone Column Size");
	}
}
