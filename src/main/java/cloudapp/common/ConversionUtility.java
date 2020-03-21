package cloudapp.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component
public class ConversionUtility implements ApplicationContextAware {

	ApplicationContext applicationContext;

	ConversionService conversionService;

	public Object convert(Object source, Class sourceType, Class targetType) {
//		if (source instanceof PersistentSet) {
//			List sourceList = new ArrayList<>();
//			PersistentSet persistentSet = (PersistentSet) source;
//			for (Object object : persistentSet) {
//				sourceList.add(object);
//			}
//			return this.convert(sourceList, sourceType, targetType);
//		}

		return conversionService.convert(source, TypeDescriptor.valueOf(sourceType),
				TypeDescriptor.valueOf(targetType));
	}

	public List convert(List sourceList, Class sourceType, Class targetType) {
		List output = new ArrayList<>();
		if (sourceList != null && !sourceList.isEmpty()) {
			output = new ArrayList<>(sourceList.size());

			for (Object source : sourceList) {
				output.add(conversionService.convert(source, TypeDescriptor.valueOf(sourceType),
						TypeDescriptor.valueOf(targetType)));
			}
		}
		return output;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

		DefaultConversionService defaultConversionService = applicationContext.getBean(DefaultConversionService.class);
		this.conversionService = defaultConversionService;

		Map<String, GenericConverter> genericConverters = applicationContext.getBeansOfType(GenericConverter.class);

		for (Entry<String, GenericConverter> entry : genericConverters.entrySet()) {
			defaultConversionService.addConverter(entry.getValue());
		}
	}
}