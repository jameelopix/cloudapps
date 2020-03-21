package cloudapp.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cloudapp.dto.TableRequest;
import cloudapp.dto.TableResponse;
import cloudapp.dto.TableSearchDTO;
import cloudapp.entity.App;
import cloudapp.entity.TableMaster;
import cloudapp.jpa.Filter;
import cloudapp.jpa.FilterUtils;
import cloudapp.jpa.SearchObject;
import cloudapp.repository.AppRepo;
import cloudapp.repository.TableRepo;
import cloudapp.service.RandomService;
import cloudapp.service.StringUtils;

@Component
public class TableComponent {

	@Autowired
	AppRepo appRepo;

	@Autowired
	TableRepo tableRepo;

	public TableResponse createTable(TableRequest tableRequest) {
		TableMaster table = tableRequest.getTable();
		Long appId = tableRequest.getAppId();
//		App app = appRepo.getOne(appId);

//		table.setApp(app);
//		table = tableRepo.save(table);

		TableResponse tableResponse = new TableResponse();
		tableResponse.setTable(table);
		return tableResponse;
	}

	public TableResponse deleteTable(TableRequest tableRequest) {
		TableMaster table = tableRequest.getTable();
		tableRepo.delete(table.getId());
		return null;
	}

	public TableRequest preprocessTableRequest(TableRequest tableRequest) {
		TableMaster table = tableRequest.getTable();

		if (table == null) {
			table = new TableMaster();
			tableRequest.setTable(table);
		}

		if (table.getKey() == null) {
			table.setKey(RandomService.generateKey(RandomService.PREFIX_APP));
		}
		if (table.getDisplayName() == null || StringUtils.BLANK.equals(table.getDisplayName().trim())) {
			table.setDisplayName("Untitled_" + new Date().getTime());
		}
		String name = table.getDisplayName().toUpperCase().replace(StringUtils.SPACE, StringUtils.UNDERSCORE);
		table.setName(name);
		return null;
	}

	public TableResponse updateTable(TableRequest tableRequest) {
		TableMaster source = tableRequest.getTable();

		TableMaster target = tableRepo.findOne(source.getId());

		target.setName(source.getName());
		target.setDisplayName(source.getDisplayName());
		target.setDescription(source.getDescription());
		target.setIcon(source.getIcon());

		if (source.getAppId() != null && !source.getAppId().equals(target.getAppId())) {
			App app = appRepo.findOne(source.getAppId());
			target.setApp(app);
		}
//		target = tableRepo.save(target);

		TableResponse tableResponse = new TableResponse();
		tableResponse.setTable(target);
		return tableResponse;
	}

	public TableResponse getTable(TableRequest tableRequest) {
		TableResponse tableResponse = new TableResponse();

		List<Filter> filters = new ArrayList<>();
		// List<Association> associations = new ArrayList<>();
		SearchObject searchObject = new SearchObject();

		TableSearchDTO tableSearchDTO = tableRequest.getTableSearchDTO();
		int count = 0;

		if (tableSearchDTO != null) {
			List<Long> idList = tableSearchDTO.getIdList();
			List<String> nameList = tableSearchDTO.getNameList();
			List<String> keyList = tableSearchDTO.getKeyList();
			List<String> displayNameList = tableSearchDTO.getDisplayNameList();
			List<String> iconList = tableSearchDTO.getIconList();
			List<String> descriptionList = tableSearchDTO.getDescriptionList();

			FilterUtils.createEqualFilter(filters, TableSearchDTO.ID, idList);
			FilterUtils.createEqualFilter(filters, TableSearchDTO.NAME, nameList);
			FilterUtils.createEqualFilter(filters, TableSearchDTO.KEY, keyList);
			FilterUtils.createEqualFilter(filters, TableSearchDTO.DISPLAYNAME, displayNameList);
			FilterUtils.createEqualFilter(filters, TableSearchDTO.DESCRIPTION, descriptionList);
			FilterUtils.createEqualFilter(filters, TableSearchDTO.ICON, iconList);

			if (filters.size() != 0) {
				searchObject.setFilters(filters);
			}

			count = tableRepo.count(searchObject).intValue();

			// AssociationUtils.createAssociation(associations, ProjectSearchDTO.PORTFOLIO,
			// tableSearchDTO.isPortfolioNeeded());
			//
			// if (associations.size() != 0) {
			// searchObject.setAssociations(associations);
			// }
		}

		List<TableMaster> tableList = tableRepo.search(searchObject);
		tableResponse.setTableList(tableList);
		tableResponse.setTotalRecordCount(count);
		return tableResponse;
	}
}