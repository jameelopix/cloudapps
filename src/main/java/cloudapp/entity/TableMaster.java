package cloudapp.entity;

import java.io.Serializable;
import java.util.Set;

//@Entity
//@Table(name = "TABLE_MASTER")
public class TableMaster implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@Column(name = "TABLE_KEY")
	private String key;
	private String name;
	private String displayName;
	private String icon;
	private String description;

//	@ManyToOne(fetch = FetchType.LAZY)
	private App app;

//	@Column(insertable = false, updatable = false, name = "app_id")
	private Long appId;

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	@JoinColumn(name = "tableMaster_id")
	private Set<ColumnMaster> columnMasters;

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

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Set<ColumnMaster> getColumnMasters() {
		return columnMasters;
	}

	public void setColumnMasters(Set<ColumnMaster> columnMasters) {
		this.columnMasters = columnMasters;
	}
}