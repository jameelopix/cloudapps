package cloudapp.component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cloudapp.common.ConversionUtility;
import cloudapp.dto.AppDTO;
import cloudapp.dto.AppRequest;
import cloudapp.dto.AppResponse;
import cloudapp.dto.AppSearchDTO;
import cloudapp.entity.App;
import cloudapp.jpa.Filter;
import cloudapp.jpa.FilterUtils;
import cloudapp.jpa.SearchObject;
import cloudapp.repository.AppRepo;
import cloudapp.repository.DbRepo;
import cloudapp.service.RandomService;
import cloudapp.service.StringUtils;

@Component
public class AppComponent {

	@Autowired
	AppRepo appRepo;
	@Autowired
	DbRepo dbRepo;
	@Autowired
	ConversionUtility conversionUtility;

	public AppResponse createApp(AppRequest appRequest) {
		AppDTO appDTO = appRequest.getAppDTO();
		App app = (App) conversionUtility.convert(appDTO, AppDTO.class, App.class);

		AppResponse appResponse = new AppResponse();
		try {
			dbRepo.createSchema(app.getName());
//			app = appRepo.save(app);
			appDTO = (AppDTO) conversionUtility.convert(app, App.class, AppDTO.class);
			appResponse.setAppDTO(appDTO);
		} catch (SQLException e) {
			e.printStackTrace();
			appResponse.setErrorMessage(e.getMessage());
		}
		return appResponse;
	}

	public AppResponse deleteApp(AppRequest appRequest) {
		AppDTO appDTO = appRequest.getAppDTO();
		AppResponse appResponse = new AppResponse();
		try {
			App app = appRepo.findOne(appDTO.getId());
			dbRepo.deleteSchema(app.getName());
			appRepo.delete(app.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			appResponse.setErrorMessage(e.getMessage());
		}
		return appResponse;
	}

	public AppRequest preprocessAppRequest(AppRequest appRequest) {
		AppDTO appDTO = appRequest.getAppDTO();
		if (appDTO == null) {
			appDTO = new AppDTO();
			appRequest.setAppDTO(appDTO);
		}

		if (appDTO.getKey() == null) {
			appDTO.setKey(RandomService.generateKey(RandomService.PREFIX_APP));
		}
		if (appDTO.getDisplayName() == null || StringUtils.BLANK.equals(appDTO.getDisplayName().trim())) {
			appDTO.setDisplayName("Untitled_" + new Date().getTime());
		}
		String name = appDTO.getDisplayName().toUpperCase().replace(StringUtils.SPACE, StringUtils.UNDERSCORE);
		appDTO.setName(name);
		return null;
	}

	public AppResponse updateApp(AppRequest appRequest) {
		AppDTO source = appRequest.getAppDTO();

		App target = appRepo.findOne(source.getId());

		// target.setName(source.getName());
		target.setDisplayName(source.getDisplayName());
		target.setDescription(source.getDescription());
		target.setIcon(source.getIcon());
//		target = appRepo.save(target);

		source = (AppDTO) conversionUtility.convert(target, App.class, AppDTO.class);
		AppResponse appResponse = new AppResponse();
		appResponse.setAppDTO(source);
		return appResponse;
	}

	public AppResponse getApp(AppRequest appRequest) {
		AppResponse appResponse = new AppResponse();

		System.out.println("AppComponent.getApp()");
		List<Filter> filters = new ArrayList<>();
		// List<Association> associations = new ArrayList<>();
		SearchObject searchObject = new SearchObject();

		AppSearchDTO appSearchDTO = appRequest.getAppSearchDTO();
		int count = 0;

		if (appSearchDTO != null) {
			List<Long> idList = appSearchDTO.getIdList();
			List<String> nameList = appSearchDTO.getNameList();
			List<String> keyList = appSearchDTO.getKeyList();
			List<String> displayNameList = appSearchDTO.getDisplayNameList();
			List<String> iconList = appSearchDTO.getIconList();
			List<String> descriptionList = appSearchDTO.getDescriptionList();

			FilterUtils.createEqualFilter(filters, AppSearchDTO.ID, idList);
			FilterUtils.createEqualFilter(filters, AppSearchDTO.NAME, nameList);
			FilterUtils.createEqualFilter(filters, AppSearchDTO.KEY, keyList);
			FilterUtils.createEqualFilter(filters, AppSearchDTO.DISPLAYNAME, displayNameList);
			FilterUtils.createEqualFilter(filters, AppSearchDTO.DESCRIPTION, descriptionList);
			FilterUtils.createEqualFilter(filters, AppSearchDTO.ICON, iconList);

			if (filters.size() != 0) {
				searchObject.setFilters(filters);
			}

			count = appRepo.count(searchObject).intValue();

			// AssociationUtils.createAssociation(associations, ProjectSearchDTO.PORTFOLIO,
			// appSearchDTO.isPortfolioNeeded());
			//
			// if (associations.size() != 0) {
			// searchObject.setAssociations(associations);
			// }
		}

		List<App> appList = appRepo.search(searchObject);
		List<AppDTO> appDTOList = conversionUtility.convert(appList, App.class, AppDTO.class);
		appResponse.setAppDTOList(appDTOList);
		appResponse.setTotalRecordCount(count);
		return appResponse;
	}
}