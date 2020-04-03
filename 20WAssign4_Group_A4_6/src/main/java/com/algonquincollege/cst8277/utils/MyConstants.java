/**************************************************************************************************
 * File: MyConstants.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.utils;

/**
 * <p>
 * This class holds various constants used by this App's artifacts
 * <p>
 * The key idea here is that often an annotation contains String-based parameters that <b><u>must be an exact match</u></b> <br/>
 * to a string used elsewhere. Use of this type of 'Contants' Interface class prevents errors such as:
<blockquote><pre>
{@literal @}GET
{@literal @}Path("{<b><u>emailID</u></b>}/project")  //accidently capitalized <b><u>ID</u></b>, instead of camel-case <b><u>Id</u></b>
public List<Project> getProjects({@literal @}PathParam("<b><u>emailId</u></b>") String emailId) ...  // path parameter does not match Annotation
</pre></blockquote>
 *
 * @author mwnorman
 */
public interface MyConstants {

    // constants on Interfaces are 'public static final' by default,
    // but I leave 'em in case I move a constant to a Class

    //REST constants
    public static final String SLASH = "/";
    public static final String APPLICATION_API_VERSION = SLASH + "api" + SLASH + "v1";
    public static final String APPLICATION_CONTEXT_ROOT = SLASH + "rest-employeeSystem";
    public static final String EMPLOYEE_RESOURCE_PATH_ID_ELEMENT =  "id";
    public static final String EMPLOYEE_RESOURCE_PATH_ID_PATH =  "/{" + EMPLOYEE_RESOURCE_PATH_ID_ELEMENT + "}";
    public static final String EMPLOYEE_RESOURCE_NAME =  "employee";

    //Security constants
    public static final String USER_ROLE = "USER_ROLE";
    public static final String ADMIN_ROLE = "ADMIN_ROLE";

    //JPA constants
    public static final String PU_NAME = "assignment4-PU";

}