package cloudapp.dto;

import java.io.Serializable;
import java.util.List;

public class ParentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long appId;

	private String appKey;

	private String appName;

	private List<Long> idsToDelete;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<Long> getIdsToDelete() {
		return idsToDelete;
	}

	public void setIdsToDelete(List<Long> idsToDelete) {
		this.idsToDelete = idsToDelete;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
}