package cloudapp.dto;

import java.io.Serializable;
import java.util.Set;

import cloudapp.entity.ColumnMaster;

public class TableDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String key;
	private String name;
	private String displayName;
	private String icon;
	private String description;

	private AppDTO appDTO;

	private Long appId;

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

	public Set<ColumnMaster> getColumnMasters() {
		return columnMasters;
	}

	public void setColumnMasters(Set<ColumnMaster> columnMasters) {
		this.columnMasters = columnMasters;
	}

	public AppDTO getAppDTO() {
		return appDTO;
	}

	public void setAppDTO(AppDTO appDTO) {
		this.appDTO = appDTO;
	}
}