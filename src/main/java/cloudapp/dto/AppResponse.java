package cloudapp.dto;

import java.util.List;

public class AppResponse extends ParentResponse {

    private static final long serialVersionUID = 1L;

    private AppDTO appDTO;

    private List<AppDTO> appDTOList;

    public List<AppDTO> getAppDTOList() {
	return appDTOList;
    }

    public void setAppDTOList(List<AppDTO> appDTOList) {
	this.appDTOList = appDTOList;
    }

    public AppDTO getAppDTO() {
	return appDTO;
    }

    public void setAppDTO(AppDTO appDTO) {
	this.appDTO = appDTO;
    }
}