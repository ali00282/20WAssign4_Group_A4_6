package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-04-05T15:52:29.270-0400")
@StaticMetamodel(SecurityUser.class)
public class SecurityUser_ {
	public static volatile SingularAttribute<SecurityUser, Integer> id;
	public static volatile SetAttribute<SecurityUser, SecurityRole> roles;
	public static volatile SingularAttribute<SecurityUser, EmployeePojo> employee;
	public static volatile SingularAttribute<SecurityUser, String> username;
	public static volatile SingularAttribute<SecurityUser, String> pwHash;
}
