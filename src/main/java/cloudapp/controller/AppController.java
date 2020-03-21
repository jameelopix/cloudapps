package cloudapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudapp.dto.AppDTO;
import cloudapp.dto.AppRequest;
import cloudapp.dto.AppResponse;
import cloudapp.service.AppService;

@Controller
public class AppController {
	@Autowired
	AppService appService;

	@RequestMapping(value = "createApp", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse createApp(@RequestBody AppRequest appRequest) {
		return appService.createApp(appRequest);
	}

	@RequestMapping(value = "deleteApp", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse deleteApp(@RequestBody AppRequest appRequest) {
		for (Long id : appRequest.getIdsToDelete()) {
			AppRequest request = new AppRequest();
			AppDTO appDTO = new AppDTO();
			appDTO.setId(id);
			request.setAppDTO(appDTO);
			appService.deleteApp(request);
		}
		return null;
	}

	// @RequestMapping(value = "updateApp", method = RequestMethod.POST)
	// @ResponseBody
	// public AppResponse updateApp(@RequestBody AppRequest appRequest) {
	// return appService.updateApp(appRequest);
	// }

	// @RequestMapping(value = "updateDesc", method = RequestMethod.POST)
	// @ResponseBody
	// public AppResponse updateDesc(@RequestBody AppRequest appRequest) {
	// return appService.updateDesc(appRequest);
	// }
	//
	// @RequestMapping(value = "updateIcon", method = RequestMethod.POST)
	// @ResponseBody
	// public AppResponse updateIcon(@RequestBody AppRequest appRequest) {
	// return appService.updateIcon(appRequest);
	// }
	//
	// @RequestMapping(value = "updateName", method = RequestMethod.POST)
	// @ResponseBody
	// public AppResponse updateName(@RequestBody AppRequest appRequest) {
	// return appService.updateName(appRequest);
	// }

	@RequestMapping(value = "getApp", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse getApp(@RequestBody AppRequest appRequest) {
		return appService.getApp(appRequest);
	}
}