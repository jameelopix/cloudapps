package cloudapp.jpa;

import cloudapp.entity.Column;
import cloudapp.entity.NumberColumn;
import cloudapp.entity.Table;

public interface DbRepo {

	public Table save(Table table);

	public Table update(Table table);

	public void create(Table table);

	public Table findTableById(Long id);

	public void deleteTableById(Long id);

	//
	public Column save(NumberColumn numberColumn);

	public void create(NumberColumn numberColumn);
}