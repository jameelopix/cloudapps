package cloudapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import cloudapp.common.ConversionUtility;
import cloudapp.dto.AppDTO;
import cloudapp.entity.App;

@Component
public class AppGenericConvertor implements GenericConverter {

	@Autowired
	ConversionUtility conversionUtility;

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> convertiblePairs = new HashSet<>();
		convertiblePairs.add(new ConvertiblePair(App.class, AppDTO.class));
		convertiblePairs.add(new ConvertiblePair(AppDTO.class, App.class));
		return convertiblePairs;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		Object target = null;
		if (sourceType.getType() == App.class && targetType.getType() == AppDTO.class) {
			target = this.convertToAppDTO((App) source);
		}
		if (sourceType.getType() == AppDTO.class && targetType.getType() == App.class) {
			target = this.convertToApp((AppDTO) source);
		}
		return target;
	}

	private Object convertToApp(AppDTO source) {
		App target = new App();
		target.setId(source.getId());
		target.setName(source.getName());
		target.setKey(source.getKey());
		target.setDisplayName(source.getDisplayName());
		target.setDescription(source.getDescription());
		target.setIcon(source.getIcon());
		return target;
	}

	private Object convertToAppDTO(App source) {
		AppDTO target = new AppDTO();
		target.setId(source.getId());
		target.setName(source.getName());
		target.setKey(source.getKey());
		target.setDisplayName(source.getDisplayName());
		target.setDescription(source.getDescription());
		target.setIcon(source.getIcon());
		return target;
	}
}