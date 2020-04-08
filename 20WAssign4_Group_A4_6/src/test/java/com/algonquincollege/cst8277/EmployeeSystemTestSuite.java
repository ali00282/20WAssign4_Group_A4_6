/***************************************************************************f******************u************zz*******y**
 * File: EmployeeSystemTestSuite.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 * @date 2020 03
 *
 * TODO - add to this class for Assignment 4
 */
package com.algonquincollege.cst8277;

import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_CONTEXT_ROOT;
import static com.algonquincollege.cst8277.utils.MyConstants.EMPLOYEE_RESOURCE_NAME;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.algonquincollege.cst8277.models.EmployeePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
    static final String DEFAULT_USER_PW = "admin";
    
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
        userAuth = HttpAuthenticationFeature.basic("user1", "password");
        
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA)
            .host(HOST).port(PORT).build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PW);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PW);
        
        
        
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
        String output = response.readEntity(String.class);
        assertThat(response.getStatus(), is(200));
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
        WebTarget webTarget = client
            .register(feature)
            .target(uri)
            .path(EMP_RESOURCE+"/2");
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
        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
        
        String output = response.readEntity(String.class);
        logger.debug(response.toString());
        logger.debug(response.toString());
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void test06_test_deleteEmpId() {
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
            .path(EMP_RESOURCE);
        
        logger.debug("Here");
        Invocation.Builder getInvocationBuilder = getWebTarget.request(MediaType.APPLICATION_JSON);
        String empStr = getInvocationBuilder.get(String.class);
        int lastId = empStr.lastIndexOf("id")+4;
        String idStr = empStr.substring(lastId, empStr.indexOf(',', lastId));
        
        logger.debug("Emp string: " + empStr);
        logger.debug("Id:" + idStr);
        
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
    
    @Ignore
    public void test01_all_employees_adminrole() throws JsonMappingException, JsonProcessingException {
//        Response response = webTarget
//            .register(adminAuth).path(EMPLOYEE_RESOURCE_NAME).request().get();
//        assertThat(response.getStatus(), is(200));
//        List<EmployeePojo> emps = response.readEntity(new GenericType(genericType)<List<EmployeePojo>>() {});
//        assertThat(emps, is(not(empty())));
////        assertThat(emps, hasSize(2));
    }

    @Ignore
    public void test02_all_employees_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget.register(userAuth)
            // .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME).request().get();
        assertThat(response.getStatus(), is(403));
    }

    @Ignore
    public void test03_employee_by_id_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget.register(userAuth)
            // .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME + "/1").request().get();
        assertThat(response.getStatus(), is(200));
        EmployeePojo emps = response.readEntity(new GenericType<EmployeePojo>() {});
        assertEquals(emps.getId(), 1);
    }

    @Ignore
    public void test04_employee_by_id_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget.register(userAuth)
            // .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME + "/2").request().get();
        assertThat(response.getStatus(), is(403));
    }

    @Ignore
    public void test05_employee_by_id_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            // .register(userAuth)
            .register(adminAuth).path(EMPLOYEE_RESOURCE_NAME + "/1").request().get();
        assertThat(response.getStatus(), is(403));
    }
}
