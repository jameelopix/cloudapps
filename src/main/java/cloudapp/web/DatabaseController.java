package cloudapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudapp.dto.DatabaseRequest;
import cloudapp.dto.DatabaseResponse;
import cloudapp.service.DatabaseService;

@Controller
public class DatabaseController {
	@Autowired
	DatabaseService databaseService;

	@RequestMapping(value = "createTable", method = RequestMethod.POST)
	@ResponseBody
	public DatabaseResponse createTable(@RequestBody DatabaseRequest databaseRequest) {
		return databaseService.createTable(databaseRequest);
	}

	@RequestMapping(value = "addColumn", method = RequestMethod.POST)
	@ResponseBody
	public DatabaseResponse addColumn(@RequestBody DatabaseRequest databaseRequest) {
		return databaseService.addColumn(databaseRequest);
	}

	@RequestMapping(value = "renameTable", method = RequestMethod.POST)
	@ResponseBody
	public DatabaseResponse renameTable(@RequestBody DatabaseRequest databaseRequest) {
		return databaseService.renameTable(databaseRequest);
	}

//	@RequestMapping(value = "updateDesc", method = RequestMethod.POST)
//	@ResponseBody
//	public DatabaseResponse updateDesc(@RequestBody DatabaseRequest databaseRequest) {
//		return databaseService.updateDesc(databaseRequest);
//	}

	@RequestMapping(value = "updateTable", method = RequestMethod.POST)
	@ResponseBody
	public DatabaseResponse updateTable(@RequestBody DatabaseRequest databaseRequest) {
		return databaseService.updateTable(databaseRequest);
	}
}