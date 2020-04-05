package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-04-05T13:27:39.049-0400")
@StaticMetamodel(PhonePojo.class)
public class PhonePojo_ extends PojoBase_ {
	public static volatile SingularAttribute<PhonePojo, String> areacode;
	public static volatile SingularAttribute<PhonePojo, String> phoneNumber;
	public static volatile SingularAttribute<PhonePojo, String> phone_type;
	public static volatile SingularAttribute<PhonePojo, EmployeePojo> owingEmployee;
}
