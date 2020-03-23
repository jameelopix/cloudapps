package cloudapp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cloudapp.common.ConversionUtility;

@Component
public class MainComponent {

	@Autowired
	ConversionUtility conversionUtility;

	public void createTable() {

	}
}