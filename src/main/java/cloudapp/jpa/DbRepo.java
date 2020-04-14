package cloudapp.jpa;

import cloudapp.entity.Column;
import cloudapp.entity.Record;
import cloudapp.entity.Table;

public interface DbRepo {

	// Generic Table Related
	public void create(Table table);

	public void insert(Table table, Record record);

	public void update(Table table, Record record);

	public Record findById(Table table, String id);

	public void deleteById(Table table, String id);

	// Generic Column Related
	public void addColumn(Table table, Column column);

	public void deleteColumn(Table table, Column column);

	public void alterColumn(Table table, Column column);

	// Metrics Related
	public void addEntry(Table table);
	
	public void addColumnEntry(Table table);

	public void addEntry(Column column);

	public void removeEntry(Table table);

	public void removeEntry(Column column);

	public void updateEntry(Table table);

	public void updateEntry(Column column);

	public void findEntry(Table table);

	public void findEntry(Column column);
}