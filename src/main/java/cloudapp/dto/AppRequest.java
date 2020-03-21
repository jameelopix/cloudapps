package cloudapp.dto;

public class AppRequest extends ParentRequest {

	private static final long serialVersionUID = 1L;

	private AppDTO appDTO;
	private AppSearchDTO appSearchDTO;

	public AppSearchDTO getAppSearchDTO() {
		return appSearchDTO;
	}

	public void setAppSearchDTO(AppSearchDTO appSearchDTO) {
		this.appSearchDTO = appSearchDTO;
	}

	public AppDTO getAppDTO() {
		return appDTO;
	}

	public void setAppDTO(AppDTO appDTO) {
		this.appDTO = appDTO;
	}
}