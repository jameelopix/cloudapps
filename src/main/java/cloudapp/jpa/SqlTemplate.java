package cloudapp.jpa;

public class SqlTemplate {

	public static String ID = "{{ID}}";
	public static String TABLE_ID = "{{TABLE_ID}}";
	public static String TABLE_NAME = "{{TABLE_NAME}}";
	public static String DB_NAME = "{{DB_NAME}}";

	public static class Table {

		public final static String INSERT = "INSERT INTO `{{DB_NAME}}`.`TABLE_MASTER`\r\n" + "(" + "`NAME`,\r\n"
				+ "`TABLE_KEY`,\r\n" + "`DISPLAY_NAME`,\r\n" + "`ICON`,\r\n" + "`DESCRIPTION`,\r\n"
				+ "`CREATED_BY`,\r\n" + "`CREATED_AT`,\r\n" + "`LAST_UPDATED_BY`,\r\n" + "`LAST_UPDATED_AT`)\r\n"
				+ "VALUES\r\n" + "(?,?,?,?,?,?,?,?,?);";

		public final static String SELECT_ALL = "SELECT `TABLE_MASTER`.`ID`,\r\n" + "    `TABLE_MASTER`.`NAME`,\r\n"
				+ "    `TABLE_MASTER`.`TABLE_KEY`,\r\n" + "    `TABLE_MASTER`.`DISPLAY_NAME`,\r\n"
				+ "    `TABLE_MASTER`.`ICON`,\r\n" + "    `TABLE_MASTER`.`DESCRIPTION`,\r\n"
				+ "    `TABLE_MASTER`.`CREATED_BY`,\r\n" + "    `TABLE_MASTER`.`CREATED_AT`,\r\n"
				+ "    `TABLE_MASTER`.`LAST_UPDATED_BY`,\r\n" + "    `TABLE_MASTER`.`LAST_UPDATED_AT`\r\n"
				+ "FROM `{{DB_NAME}}`.`TABLE_MASTER`;";

		public final static String UPDATE = "UPDATE `{{DB_NAME}}`.`TABLE_MASTER`\r\n" + "SET\r\n" + "`NAME` = ?,\r\n"
				+ "`TABLE_KEY` = ?,\r\n" + "`DISPLAY_NAME` = ?,\r\n" + "`ICON` = ?,\r\n" + "`DESCRIPTION` = ?,\r\n"
				+ "`CREATED_BY` = ?,\r\n" + "`CREATED_AT` = ?,\r\n" + "`LAST_UPDATED_BY` = ?,\r\n"
				+ "`LAST_UPDATED_AT` = ?\r\n" + "WHERE `ID` = ?;";

		public final static String FIND_BY_ID = "SELECT * FROM `{{DB_NAME}}`.`TABLE_MASTER` WHERE ID = {{ID}};";

		public final static String DELETE_BY_ID = "DELETE FROM `{{DB_NAME}}`.`TABLE_MASTER` WHERE ID = {{ID}};";

		public final static String SAMPLE_TABLE = "CREATE TABLE `{{DB_NAME}}`.`{{TABLE_NAME}}` (\r\n"
				+ "    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,\r\n" + "    `NAME` VARCHAR(255) NOT NULL,\r\n"
				+ "    `DESCRIPTION` VARCHAR(4095) DEFAULT NULL,\r\n" + "    `CREATEDBY` VARCHAR(45) DEFAULT NULL,\r\n"
				+ "    `CREATEDAT` DATETIME DEFAULT NULL,\r\n" + "    `LASTUPDATEDBY` VARCHAR(45) DEFAULT NULL,\r\n"
				+ "    `LASTUPDATEDAT` DATETIME DEFAULT NULL,\r\n" + "    PRIMARY KEY (`ID`)\r\n"
				+ ")  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;";
	}

	public static class NumberColumn {
		public final static String FIND_BY_ID = "SELECT * FROM `{{DB_NAME}}`.`NUMBER_COLUMN_MASTER` WHERE ID = {{ID}};";

		public final static String DELETE_BY_ID = "DELETE FROM `{{DB_NAME}}`.`NUMBER_COLUMN_MASTER` WHERE ID = {{ID}};";

		public final static String INSERT = "INSERT INTO `{{DB_NAME}}`.`NUMBER_COLUMN_MASTER`\r\n" + "(`ID`,\r\n"
				+ "`NAME`,\r\n" + "`COLUMN_KEY`,\r\n" + "`DISPLAY_NAME`,\r\n" + "`ICON`,\r\n" + "`DESCRIPTION`,\r\n"
				+ "`CREATED_BY`,\r\n" + "`CREATED_AT`,\r\n" + "`LAST_UPDATED_BY`,\r\n" + "`LAST_UPDATED_AT`,\r\n"
				+ "`TABLE_ID`,\r\n" + "`IS_NEGATIVE_ALLOWED`,\r\n" + "`MIN_ALLOWED`,\r\n" + "`MAX_ALLOWED`)\r\n"
				+ "VALUES\r\n" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		public final static String SELECT_ALL = "SELECT * FROM `{{DB_NAME}}`.`NUMBER_COLUMN_MASTER`;";

		public final static String UPDATE = "UPDATE `{{DB_NAME}}`.`NUMBER_COLUMN_MASTER`\r\n" + "SET\r\n"
				+ "`NAME` = ?,\r\n" + "`COLUMN_KEY` = ?,\r\n" + "`DISPLAY_NAME` = ?,\r\n" + "`ICON` = ?,\r\n"
				+ "`DESCRIPTION` = ?,\r\n" + "`CREATED_BY` = ?,\r\n" + "`CREATED_AT` = ?,\r\n"
				+ "`LAST_UPDATED_BY` = ?,\r\n" + "`LAST_UPDATED_AT` = ?,\r\n" + "`TABLE_ID` = ?,\r\n"
				+ "`IS_NEGATIVE_ALLOWED` = ?,\r\n" + "`MIN_ALLOWED` = ?,\r\n" + "`MAX_ALLOWED` = ?\r\n"
				+ "WHERE `ID` = ?;";
	}
}