package cloudapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudapp.dto.MainRequest;
import cloudapp.dto.TableResponse;
import cloudapp.service.MainService;

@Controller
public class SimpleController {
	@Autowired
	MainService mainService;

	@RequestMapping(value = "createTable", method = RequestMethod.POST)
	@ResponseBody
	public TableResponse createTable(@RequestBody MainRequest mainRequest) {
		return mainService.createTable(mainRequest);
	}

	@RequestMapping(value = "renameTable", method = RequestMethod.POST)
	@ResponseBody
	public TableResponse renameTable(@RequestBody MainRequest mainRequest) {
		return mainService.renameTable(mainRequest);
	}
}