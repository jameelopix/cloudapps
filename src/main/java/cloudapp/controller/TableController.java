package cloudapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cloudapp.service.TableService;

@Controller
public class TableController {
	@Autowired
	TableService tableService;

	// @RequestMapping(value = "createTable", method = RequestMethod.POST)
	// @ResponseBody
	// public TableResponse createTable(@RequestBody TableRequest tableRequest) {
	// return tableService.createTable(tableRequest);
	// }

	// @RequestMapping(value = "deleteTable", method = RequestMethod.POST)
	// @ResponseBody
	// public TableResponse deleteTable(@RequestBody TableRequest tableRequest) {
	// for (Long id : tableRequest.getIdsToDelete()) {
	// TableRequest request = new TableRequest();
	// TableMaster tableMaster = new TableMaster();
	// tableMaster.setId(id);
	// request.setTable(tableMaster);
	// tableService.deleteTable(request);
	// }
	// return null;
	// }

	// @RequestMapping(value = "updateTable", method = RequestMethod.POST)
	// @ResponseBody
	// public TableResponse updateTable(@RequestBody TableRequest tableRequest) {
	// return tableService.updateTable(tableRequest);
	// }

	// @RequestMapping(value = "getTable", method = RequestMethod.POST)
	// @ResponseBody
	// public TableResponse getTable(@RequestBody TableRequest tableRequest) {
	// return tableService.getTable(tableRequest);
	// }
}