package cloudapp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cloudapp.common.ConversionUtility;
import cloudapp.repository.AppRepo;
import cloudapp.repository.DbRepo;

@Component
public class MainComponent {
	@Autowired
	AppRepo appRepo;
	@Autowired
	DbRepo dbRepo;
	@Autowired
	ConversionUtility conversionUtility;

	public void createTable() {

	}
}