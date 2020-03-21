package cloudapp.entity;

import java.io.Serializable;

//@Entity
//@Table(name = "COLUMN_MASTER")
public class ColumnMaster implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@Column(name = "COLUMN_KEY")
	private String key;
	private String name;
	private String displayName;
	private String icon;
	private String description;

//	@ManyToOne(fetch = FetchType.LAZY)
	private TableMaster tableMaster;

//	@Column(insertable = false, updatable = false, name = "tableMaster_id")
	private Long tableMasterId;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TableMaster getTableMaster() {
		return tableMaster;
	}

	public void setTableMaster(TableMaster tableMaster) {
		this.tableMaster = tableMaster;
	}
}