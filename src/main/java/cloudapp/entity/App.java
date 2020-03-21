package cloudapp.entity;

import java.io.Serializable;
import java.util.Set;

//@Entity
//@Table(name = "APP")
public class App implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@Column(name = "APP_KEY")
	private String key;
	private String name;
	private String displayName;
	private String icon;
	private String description;

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	@JoinColumn(name = "app_id")
	private Set<TableMaster> tableMasters;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Set<TableMaster> getTableMasters() {
		return tableMasters;
	}

	public void setTableMasters(Set<TableMaster> tableMasters) {
		this.tableMasters = tableMasters;
	}
}