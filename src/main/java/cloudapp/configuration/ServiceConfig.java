package cloudapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ServiceConfig {

	@Bean
	public DefaultConversionService conversionService() {
		return new DefaultConversionService();
	}
}