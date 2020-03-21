package cloudapp.jpa;

import cloudapp.component.Column;
import cloudapp.component.Table;

public interface DbRepo {

    public Table save(Table table);

    public void create(Table table);

    public Table findTableById(Long id);

    public void deleteTableById(Long id);

    public Column save(Column column);

    public void create(Column column);
}