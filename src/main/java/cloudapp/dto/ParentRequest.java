package cloudapp.dto;

import java.io.Serializable;
import java.util.List;

public class ParentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

//	private Long appId;
//
//	private String appKey;
//
//	private String appName;

	private List<Long> idsToDelete;

	public List<Long> getIdsToDelete() {
		return idsToDelete;
	}

	public void setIdsToDelete(List<Long> idsToDelete) {
		this.idsToDelete = idsToDelete;
	}
}