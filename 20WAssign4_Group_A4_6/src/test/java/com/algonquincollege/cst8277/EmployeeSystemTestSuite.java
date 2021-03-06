/***************************************************************************f******************u************zz*******y**
 * File: EmployeeSystemTestSuite.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 * @date 2020 03
 * @author Tariq Ali - 040811012
 * @author Sirisha Jayampu - 040879561
 * @author Asha Alphonsa kurian - 040921013
 * @author Shahrir Ahmed - 040920464
 * TODO - add to this class for Assignment 4
 */
package com.algonquincollege.cst8277;

import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_CONTEXT_ROOT;
import static com.algonquincollege.cst8277.utils.MyConstants.EMPLOYEE_RESOURCE_NAME;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityListeners;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.EmployeePojo;
import com.algonquincollege.cst8277.models.EmployeeTask;
import com.algonquincollege.cst8277.models.HomePhone;
import com.algonquincollege.cst8277.models.MobilePhone;
import com.algonquincollege.cst8277.models.PhonePojo;
import com.algonquincollege.cst8277.models.ProjectPojo;
import com.algonquincollege.cst8277.models.WorkPhone;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*This is the comment to see commit in github*/
public class EmployeeSystemTestSuite {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    
    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080; //TODO - use your actual Payara port number
    static final String DEFAULT_ADMIN_USER = "admin";
    static final String DEFAULT_ADMIN_USER_PW = "admin";
    static final String DEFAULT_USER = "user1";
    static final String DEFAULT_USER_PW = "user1";
    
    static final String EMP_RESOURCE =
        //some JAX-RS resource the 'admin' user has security privileges to invokd
        EMPLOYEE_RESOURCE_NAME;

    // test fixture(s)
    static HttpAuthenticationFeature feature;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;
    
    static URI uri;
    protected WebTarget webTarget;
    
    

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        feature = HttpAuthenticationFeature.basic("admin", "admin");
        adminAuth = feature;
        userAuth = HttpAuthenticationFeature.basic("user1", "admin");
        
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA)
            .host(HOST).port(PORT).build();
    }

    @AfterClass
    public static void oneTimeTearDown() {
        logger.debug("oneTimeTearDown");
    }
    
    @Before
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }
   
    
    // =============================================================================================
    // SIRISHA JAYAMPU: To write tests easily refer this link https://www.baeldung.com/jersey-jax-rs-client
    // =============================================================================================
    
    @Test
    public void test01_test_admin() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test02_test_getAllEmployees() {
        getAllEmployees();
    }

    private String getAllEmployees() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        
        return response.readEntity(String.class);
    }
    

    
    @Test
    public void test03_test_getEmpById() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        
        int id = employees.get(employees.size() - 1).getId();
        logger.debug("Found Id:" + id);
        
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test04_test_updateEmp() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
        
        logger.debug(addWebTarget
            .request(APPLICATION_JSON).toString());
        
        emp.setFirstName("JunitUserUpdated");
        emp.setFirstName("JunitLastUpdated");
        emp.setEmail("JunitUserUpdated.JunitUserUpdated@test.com");

        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test05_test_addEmp() {
        String suffix = "-1";
        createTestEmployee(suffix);
    }

    private String createTestEmployee(String suffix) {
        logger.debug("Creating emp with suffix:" + suffix);
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/add");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        EmployeePojo emp = new EmployeePojo();
        emp.setFirstName("JunitUser"+suffix);
        emp.setFirstName("JunitLast"+suffix);
        emp.setEmail("JunitUser"+suffix+".JunitLast"+suffix+"@test.com");
        emp.setTitle("Tester");
        emp.setSalary(100.0);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
        return output;
    }
    
    @Test
    public void test06_test_deleteEmpId() {
        
        getFirstEmpId(createTestEmployee("DeleteMe"));
        
        String idStr = getLastEmpId(getAllEmployees());
        
        logger.debug("Delete emp id:" + idStr);
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
       
        
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/delete/" + idStr);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }

    private String getFirstEmpId(String empStr) {
        if (empStr == null || empStr.isEmpty())
        {
            return "0";
        }
        int indexOfId = empStr.indexOf("id")+4;
        if (indexOfId == -1)
        {
            return "0";
        }
        String idStr = empStr.substring(indexOfId, empStr.indexOf(',', indexOfId));
        return idStr;
    }
    
    private String getLastEmpId(String empStr) {
        int lastId = empStr.lastIndexOf("id")+4;
        String idStr = empStr.substring(lastId, empStr.indexOf(',', lastId));
        return idStr;
    }
    
    @Test
    public void test07_all_employees_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget.register(userAuth)
            // .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME).request().get();
        assertThat(response.getStatus(), is(403));
    }

    @Test
    public void test08_employee_by_id_userrole() throws JsonMappingException, JsonProcessingException {
        
        
        /*
         * String id = getLastEmpId(getAllEmployees());
         * logger.debug("Found Id:" + id);
         * Response response = webTarget.register(userAuth)
         * // .register(adminAuth)
         * .path(EMPLOYEE_RESOURCE_NAME + "/" + id).request().get();
         * assertThat(response.getStatus(), is(200));
         * EmployeePojo emps = response.readEntity(new GenericType<EmployeePojo>() {});
         * assertEquals(emps.getId(), 1);
         */
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        String id = getLastEmpId(getAllEmployees());
        logger.debug("Found Id:" + id);
        
        WebTarget webTarget = client
            .register(userAuth)    // Authentication
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }

//    @Ignore
//    public void test09_employee_by_id_userrole() throws JsonMappingException, JsonProcessingException {
//
//        Response response = webTarget.register(userAuth)
//            // .register(adminAuth)
//            .path(EMPLOYEE_RESOURCE_NAME + "/2").request().get();
//        assertThat(response.getStatus(), is(403));
//    }
//
    
    @Test
    public void test10_test_addressOnEmployees() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/add");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        EmployeePojo emp = new EmployeePojo();
        emp.setFirstName("JunitUser");
        emp.setFirstName("JunitLast");
        emp.setEmail("JunitUser.JunitLast@test.com");
        emp.setTitle("Tester");
        emp.setSalary(100.0);
        
        AddressPojo address = new AddressPojo();
        address.setCity("Nepean");
        address.setStreet("123 GOES NOWHERE STREET");
        address.setState("NEWPLACE");
        address.setCountry("Fancylan");
        address.setPostal("ABC 123");
        
        emp.setAddress(address);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test11_test_PhoneListOnEmployees() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/add");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        EmployeePojo emp = new EmployeePojo();
        emp.setFirstName("JunitUser");
        emp.setFirstName("JunitLast");
        emp.setEmail("JunitUser.JunitLast@test.com");
        emp.setTitle("Tester");
        emp.setSalary(100.0);
        
        AddressPojo address = new AddressPojo();
        address.setCity("Nepean");
        address.setStreet("123 GOES NOWHERE STREET");
        address.setState("NEWPLACE");
        address.setCountry("Fancyland");
        address.setPostal("ABC 123");
        
        emp.setAddress(address);
        
        List<PhonePojo> phones = new ArrayList<PhonePojo>();
        PhonePojo home = new HomePhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 4567");
        home.setPhone_type("H");
        
        PhonePojo work = new WorkPhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 4567");
        home.setPhone_type("H");
        
        PhonePojo mobile = new MobilePhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 4567");
        home.setPhone_type("H");
        
        phones.add(home);
        phones.add(work);
        phones.add(mobile);
        
        emp.setPhones(phones);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    

    @Test
    public void test12_test_Delete_Resource() {
        Client adminClient = ClientBuilder.newClient();
      
        WebTarget webTarget2 = adminClient.register(feature).target(uri).path(EMP_RESOURCE);
        Response response2 = webTarget2.request(APPLICATION_JSON).delete();
    
        assertThat(response2.getStatus(), is(405));
    }
    
    @Test
    public void test13_test_User_Roles() {
        Client userClient = ClientBuilder.newClient();
        Client adminClient = ClientBuilder.newClient();

        WebTarget webTarget1 = userClient.register(userAuth).target(uri).path(EMP_RESOURCE);
        WebTarget webTarget2 = adminClient.register(feature).target(uri).path(EMP_RESOURCE);

        Response response1 = webTarget1.request(APPLICATION_JSON).get();
        Response response2 = webTarget2.request(APPLICATION_JSON).get();
        assertNotEquals(response1.readEntity(String.class), response2.readEntity(String.class));

    }
    
    @Test
    public void test14_test_User_default() {
        Client adminClient = ClientBuilder.newClient();

        WebTarget webTarget1 = adminClient.register(adminAuth).target(uri).path(EMP_RESOURCE);
        
        String newName = "This is a new name";
        
        Response response1 = webTarget1.request(APPLICATION_JSON).put(Entity.json(newName));
        assertNotEquals(response1.getStatus(), is(405));
    }
    
    @Test
    public void test15_test_Password_Update_user() throws JsonParseException, JsonMappingException, IOException {
      
        Client adminClient = ClientBuilder.newClient();
        WebTarget adminLogin = adminClient.register(userAuth).target(uri).path(EMP_RESOURCE);
        Response response1 = adminLogin.request(APPLICATION_JSON).put(Entity.json("123456"));
        assertNotEquals(response1.getStatus(), is(405));
    }
    
    @Test
    public void test16_test_Password_Delete_user() throws JsonParseException, JsonMappingException, IOException {
        Client adminClient = ClientBuilder.newClient();
        WebTarget adminLogin = adminClient.register(feature).target(uri).path(EMP_RESOURCE);
        Response response1 = adminLogin.request(APPLICATION_JSON).delete();
        assertNotEquals(response1.getStatus(), is(200));
    }
    
    @Test
    public void test17_test_Multiple_Roles() {
        HttpAuthenticationFeature user = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PW);
        HttpAuthenticationFeature admin = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PW);
        Client userClient = ClientBuilder.newClient();
        Client adminClient = ClientBuilder.newClient();

        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA).host(HOST)
                .port(8080).build();
        WebTarget webTarget1 = userClient.register(user).target(uri).path(EMP_RESOURCE);
        WebTarget webTarget2 = adminClient.register(admin).target(uri).path(EMP_RESOURCE);

        Response response1 = webTarget1.request(APPLICATION_JSON).get();
        Response response2 = webTarget2.request(APPLICATION_JSON).get();
        assertNotEquals(response1.getStatus(), is(401));
        assertNotEquals(response2.getStatus(), is(401));
        assertNotEquals(response1.readEntity(String.class), response2.readEntity(String.class));
    }
    
    @Test
    public void test18_testPostMethod_User() throws JsonParseException, JsonMappingException, IOException {
        HttpAuthenticationFeature admin = HttpAuthenticationFeature.basic("admin", "admin");
        Client adminClient = ClientBuilder.newClient();

        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA).host(HOST)
                .port(8080).build();
        WebTarget webTarget1 = adminClient.register(admin).target(uri).path(EMP_RESOURCE);

        EmployeePojo emp1 = new EmployeePojo();
        emp1.setFirstName("test Employee Post");
        String userJson = "[\n" +
                "   {\n" +
                "       \"name\": \"test Employee Post\"\n" +
                "   }]";
        Response response1 = webTarget1.request(APPLICATION_JSON).post(Entity.json(userJson));
        assertThat(response1.getStatus(), is(405));
    }
    
            
    @Test
    public void test19_testUser_BasicAuth_getA() {
        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA).host(HOST)
                .port(8080).build();
        HttpAuthenticationFeature admin = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PW);
        Client client2 = ClientBuilder.newClient();

        
        WebTarget webTarget4 = client2.register(admin).target(uri).path("user/1");

        Response response4 = webTarget4.request(APPLICATION_JSON).delete();
        assertThat(response4.getStatus(), is(404));
    }
    
    @Test
    public void test20_testUser_BasicAuth_get() {
        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA).host(HOST)
                .port(8080).build();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget1 = client.target(uri).path(EMP_RESOURCE);
      
        Response response = webTarget1.request(APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(401));
    }

    @Test
    public void test21_testUser_BasicAuth_post() {
        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA)
            .host(HOST).port(8080).build();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget1 = client.target(uri).path(EMP_RESOURCE);
        Response response1 = webTarget1.request(APPLICATION_JSON).post(null);
        assertThat(response1.getStatus(), is(405));
    }
    
    @Test
    public void test22_testUser_BasicAuth_get_B() {
        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA)
            .host(HOST).port(8080).build();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget2 = client.target(uri).path(EMP_RESOURCE + "/1");
        Response response2 = webTarget2.request(APPLICATION_JSON).get();
        assertThat(response2.getStatus(), is(401));
    }
    
    @Test
    public void test23_testUser_BasicAuth_delete() {
        URI uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA)
            .host(HOST).port(8080).build();
        HttpAuthenticationFeature admin = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PW);
        Client client2 = ClientBuilder.newClient();
        WebTarget webTarget4 = client2.register(admin).target(uri).path(EMP_RESOURCE + "/1");
        Response response4 = webTarget4.request(APPLICATION_JSON).delete();
        assertThat(response4.getStatus(), is(405));
    }
    
    
    @Test
    public void test24_add_employeetask() {
     
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
       /*
        EmployeePojo emp = new EmployeePojo();
        emp.setFirstName("JunitTask");
        emp.setFirstName("JunitLast");
        emp.setEmail("JunitUser.JunitLast@test.com");
        emp.setTitle("Tester");
        emp.setSalary(100.0);*/
        
        EmployeeTask task = new EmployeeTask();
        task.setDescription("Perform Unittest for R");
        task.setTask_done(false);
        EmployeeTask task1 = new EmployeeTask();
        task1.setDescription("Perform Unittest for CUD");
        task1.setTask_done(true);
        List<EmployeeTask> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task);
        emp.setEmployeeTask(tasks);
        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test25_Remove_tasks() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
                emp.setEmployeeTask(null);
          
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test26_add_phonetoexisitngEmp() {
 
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
   
        List<PhonePojo> phones = new ArrayList<PhonePojo>();
        PhonePojo home = new HomePhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 2222");
        home.setPhone_type("H");
        emp.setPhones(phones);
        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test27_add_ProjecttoexisitngEmp() {
 
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
        Set<ProjectPojo> projects = new HashSet<ProjectPojo>();
        Set<EmployeePojo> employees = new HashSet<EmployeePojo>();
        ProjectPojo project = new ProjectPojo();
        project.setDescription("Testing");
        
        projects.add(project);
        employees.add(emp);
        emp.setProjects(projects);
        
        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(500));
        
    }
    
    @Test
    public void test28_remove_ProjecttoexisitngEmp() {
 
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
          
        emp.setProjects(null);
        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test29_add_AddresstoexisitngEmp() {
 
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
        
        AddressPojo address = new AddressPojo();
        address.setCity("Kanata");
        address.setStreet("NOWHERE STREET");
          
        emp.setAddress(address);
        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
    }
    
    @Test
    public void test30_remove_AddresstoexisitngEmp() {
 
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
        
        AddressPojo address = new AddressPojo();
     
          
        emp.setAddress(null);
        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test31_test_createdDateOnPhone() {
        createTestEmployee("cdateOnPhone");
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        
        EmployeePojo emp = employees.get(employees.size() - 1);
        
        List<PhonePojo> phones = new ArrayList<>();
        
        MobilePhone mp = new MobilePhone();
        mp.setAreacode("222");
        mp.setPhoneNumber("222 2222");
        mp.setPhone_type("M");
        phones.add(mp);
        
        emp.setPhones(phones);
        
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update/");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
         Response response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
         assertEquals(200, response2.getStatus());
         
         client = ClientBuilder.newClient();
         uri = UriBuilder
             .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
             .scheme(HTTP_SCHEMA)
             .host(HOST)
             .port(PORT)
             .build();
         
         WebTarget getWebTarget2 = client
             .register(feature)
             .register(MyObjectMapperProvider.class)
             .register(MyObjectMapperProvider.class)
             .target(uri)
             .path(EMP_RESOURCE+"/" + emp.getId());
         
         Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
         
         emp = getInvocationBuilder2.get(EmployeePojo.class);
         
        
        assertNotNull(emp.getPhones().get(0));
        assertNotNull(emp.getPhones().get(0).getCREATED_DATE());
    }
    
    @Test
    public void test32_test_updatedDateOnPhone() {
        
        createTestEmployee("udateOnPhone");
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        
        EmployeePojo emp = employees.get(employees.size() - 1);
        
        List<PhonePojo> phones = new ArrayList<>();
        
        MobilePhone mp = new MobilePhone();
        mp.setAreacode("222");
        mp.setPhoneNumber("222 2222");
        mp.setPhone_type("M");
        phones.add(mp);
        
        emp.setPhones(phones);
        
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update/");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
         Response response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
         assertEquals(200, response2.getStatus());
         
        assertNotNull(emp.getPhones().get(0));

        client = ClientBuilder.newClient();
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget2 = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/" + emp.getId());
        
        Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
        
        emp = getInvocationBuilder2.get(EmployeePojo.class);
        
        emp.getPhones().get(0).setAreacode("111");
        response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        assertEquals(200, response2.getStatus());
        
        emp = getInvocationBuilder2.get(EmployeePojo.class);
        
        assertNotNull(emp.getPhones().get(0));
        assertNotNull(emp.getPhones().get(0).getUPDATED_DATE());
    }
    
    @Test
    public void test33_a_test_taskDescription() {
        createTestEmployee("descOnTask");
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        
        EmployeePojo emp = employees.get(employees.size() - 1);
        
        List<EmployeeTask> tasks = new ArrayList<>();
        
        EmployeeTask task = new EmployeeTask();
        task.setDescription("Some Task");
        tasks.add(task);
        
        emp.setEmployeeTask(tasks);
        logger.debug("task count:" + tasks.size());
        logger.debug("Task emp id:" + emp.getId());
        
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update/");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
         Response response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
         logger.debug("task count:" + emp.getEmployeeTask().size());
        
         assertEquals(200, response2.getStatus());
         
         client = ClientBuilder.newClient();
         uri = UriBuilder
             .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
             .scheme(HTTP_SCHEMA)
             .host(HOST)
             .port(PORT)
             .build();
         
         WebTarget getWebTarget2 = client
             .register(feature)
             .register(MyObjectMapperProvider.class)
             .target(uri)
             .path(EMP_RESOURCE+"/" + emp.getId());
         
         logger.debug("Task emp id:" + emp.getId());
         
         Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
         
         emp = getInvocationBuilder2.get(EmployeePojo.class);
         
         logger.debug("task count:" + emp.getEmployeeTask().size());
        
        assertNotNull(emp.getEmployeeTask().get(0));
        assertEquals("Some Task", emp.getEmployeeTask().get(0).getDescription());
    }
    
    @Test
    public void test33_b_test_taskDeletion() {
        createTestEmployee("delTask");
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        
        EmployeePojo emp = employees.get(employees.size() - 1);
        
        List<EmployeeTask> tasks = new ArrayList<>();
        
        EmployeeTask task = new EmployeeTask();
        task.setDescription("Some Task");
        tasks.add(task);
        
        emp.setEmployeeTask(tasks);
        
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update/");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
         Response response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
         assertEquals(200, response2.getStatus());
         
         emp.setEmployeeTask(null);
         
         response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
         
         client = ClientBuilder.newClient();
         uri = UriBuilder
             .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
             .scheme(HTTP_SCHEMA)
             .host(HOST)
             .port(PORT)
             .build();
         
         WebTarget getWebTarget2 = client
             .register(feature)
             .register(MyObjectMapperProvider.class)
             .register(MyObjectMapperProvider.class)
             .target(uri)
             .path(EMP_RESOURCE+"/" + emp.getId());
         
         Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
         
         emp = getInvocationBuilder2.get(EmployeePojo.class);
        
        assertEquals(0, emp.getEmployeeTask().size());
    }
    
    @Test
    public void test34_test_negative_deleteNonExistingEmpId() {
        
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
       
        
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/delete/" + 100000);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        assertNotEquals(200, response.getStatus());
    }

    @Test
    public void test35_test_updateOrInsertEmp() {
        
        deleteEmp(1000000);
        
        getFirstEmpId(createTestEmployee("UpdateMe"));
        
        String idStr = getLastEmpId(getAllEmployees());
        
        logger.debug("Update Me emp id:" + idStr);
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget2 = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/" + idStr);
        
        Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder2.get(EmployeePojo.class);

        // Set non-existing employee
        emp.setId(1000000);
       
        
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/update/");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
         Response response2 = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
         assertEquals(200, response2.getStatus());
         
         
        // Cleanup
        deleteEmp(1000000);
        
    }
    
    private boolean deleteEmp(int id) {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
       
        
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/delete/" + id);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        
        if (response.getStatus() == 200)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private List<EmployeePojo> getAllEmployeesV2() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE);
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        Response response = webTarget
            .request(APPLICATION_JSON)
            .get();
        
        return response.readEntity(new GenericType<List<EmployeePojo>>(){});
    }
    
    @Test
    public void test36_test_createTimeStampOnAddress() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/add");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        EmployeePojo emp = new EmployeePojo();
        emp.setFirstName("JunitUser");
        emp.setFirstName("JunitLast");
        emp.setEmail("JunitUser.JunitLast@test.com");
        emp.setTitle("Tester");
        emp.setSalary(100.0);
        
        AddressPojo address = new AddressPojo();
        address.setCity("Nepean");
        address.setStreet("123 GOES NOWHERE STREET");
        address.setState("NEWPLACE");
        address.setCountry("Fancylan");
        address.setPostal("ABC 123");
        
        emp.setAddress(address);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        int id = employees.get(employees.size() - 1 ).getId();
        
        WebTarget getWebTarget2 = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        
        Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
        
        emp = getInvocationBuilder2.get(EmployeePojo.class);

        assertNotNull(emp);
        assertNotNull(emp.getAddress());
        assertNotNull(emp.getAddress().getCREATED_DATE());
        
    }
    
    @Test
    public void test37_test_updateTimeStampOnAddress() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/add");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        EmployeePojo emp = new EmployeePojo();
        emp.setFirstName("JunitUser");
        emp.setFirstName("JunitLast");
        emp.setEmail("JunitUser.JunitLast@test.com");
        emp.setTitle("Tester");
        emp.setSalary(100.0);
        
        AddressPojo address = new AddressPojo();
        address.setCity("Nepean");
        address.setStreet("123 GOES NOWHERE STREET");
        address.setState("NEWPLACE");
        address.setCountry("Fancylan");
        address.setPostal("ABC 123");
        
        emp.setAddress(address);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
        List<EmployeePojo> employees = getAllEmployeesV2();
        
        int id =employees.get(employees.size() - 1).getId();
        logger.debug("CreationDate employee: " + id);
        
        WebTarget getWebTarget2 = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        
        Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
        
        emp = getInvocationBuilder2.get(EmployeePojo.class);
        
        assertNotNull(emp);
        assertNotNull(emp.getAddress());
        
        emp.getAddress().setCity("Another City");
        
        response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        WebTarget getWebTarget3 = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/" + emp.getId());
        
        getInvocationBuilder2 = getWebTarget3.request(MediaType.APPLICATION_JSON);

        emp = getInvocationBuilder2.get(EmployeePojo.class);
        
        assertNotNull(emp.getAddress().getUPDATED_DATE());
    }
    
    @Test
    public void test38_test_UpdatePhoneListOnEmployees() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        createTestEmployee("UpdatePhone");
        String id = getLastEmpId(getAllEmployees());
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        List<PhonePojo> newPhones = new ArrayList<PhonePojo>();
        PhonePojo home = new HomePhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 4567");
        home.setPhone_type("H");
        
        PhonePojo work = new WorkPhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 4567");
        home.setPhone_type("H");
        
        PhonePojo mobile = new MobilePhone();
        home.setAreacode("613");
        home.setPhoneNumber("123 4567");
        home.setPhone_type("H");
        
        newPhones.add(home);
        newPhones.add(work);
        newPhones.add(mobile);
        
        emp.getPhones().addAll(newPhones);
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test39_test_creationDateOnEmployee() {
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        createTestEmployee("UpdatePhone");
        String id = getLastEmpId(getAllEmployees());
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        
        assertNotNull(emp.getCREATED_DATE());
    }
    
    @Test
    public void test40_test_updationDateOnEmployee() {
        
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        
        WebTarget getWebTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/1");
        
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        
        EmployeePojo emp = getInvocationBuilder.get(EmployeePojo.class);
        
        WebTarget addWebTarget = client.register(feature).target(uri).path(EMP_RESOURCE+"/add");
        
        logger.debug(addWebTarget
            .request(APPLICATION_JSON).toString());
        
        emp.setFirstName("JunitUserUpdated");
        emp.setFirstName("JunitLastUpdated");
        emp.setEmail("JunitUserUpdated.JunitUserUpdated@test.com");

        
        Invocation.Builder invocationBuilder = addWebTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
        
        client = ClientBuilder.newClient();
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        WebTarget webTarget = client
            .register(feature)
            .register(MyObjectMapperProvider.class)
            .target(uri)
            .path(EMP_RESOURCE+"/update");
        logger.debug(webTarget
            .request(APPLICATION_JSON).toString());
        
        createTestEmployee("UpdatePhone");
        String id = getLastEmpId(getAllEmployees());
        
        WebTarget getWebTarget2 = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/" + id);
        
        Invocation.Builder getInvocationBuilder2 = getWebTarget2.request(MediaType.APPLICATION_JSON);
        
        emp = getInvocationBuilder2.get(EmployeePojo.class);
        
        
        assertNotNull(emp.getUPDATED_DATE());

    }
    
}
