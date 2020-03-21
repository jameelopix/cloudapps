package cloudapp.jpa;

import java.util.List;

public class AssociationUtils {
	public static void createAssociation(List<Association> associations, String fieldName, boolean neededFlag) {
		if (neededFlag) {
			Association association = new Association();
			association.setAttribute(fieldName);
			association.setChildless(false);
			associations.add(association);
		}
	}
}