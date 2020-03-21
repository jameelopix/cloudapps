package cloudapp.dto;

import java.io.Serializable;
import java.util.List;

public class TableSearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Long> idList;
	private List<String> keyList;
	private List<String> nameList;
	private List<String> displayNameList;
	private List<String> iconList;
	private List<String> descriptionList;

	public static String ID = "id";
	public static String KEY = "key";
	public static String NAME = "name";
	public static String DISPLAYNAME = "displayName";
	public static String ICON = "icon";
	public static String DESCRIPTION = "description";

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public List<String> getDisplayNameList() {
		return displayNameList;
	}

	public void setDisplayNameList(List<String> displayNameList) {
		this.displayNameList = displayNameList;
	}

	public List<String> getIconList() {
		return iconList;
	}

	public void setIconList(List<String> iconList) {
		this.iconList = iconList;
	}

	public List<String> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<String> descriptionList) {
		this.descriptionList = descriptionList;
	}

}