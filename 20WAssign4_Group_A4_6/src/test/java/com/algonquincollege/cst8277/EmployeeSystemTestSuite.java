/***************************************************************************f******************u************zz*******y**
 * File: EmployeeSystemTestSuite.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 * @date 2020 03
 *
 */
package com.algonquincollege.cst8277;

import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.EMPLOYEE_RESOURCE_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.algonquincollege.cst8277.models.EmployeePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class EmployeeSystemTestSuite {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);

    static final String APPLICATION_CONTEXT_ROOT = "make-progress";
    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 9090;
    static final String DEFAULT_ADMIN_USER = "admin";
    static final String DEFAULT_ADMIN_USER_PW = "admin";
    static final String DEFAULT_USER = "user1";
    static final String DEFAULT_USER_PW = "admin";

    // test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PW);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER, DEFAULT_USER_PW);
    }

    protected WebTarget webTarget;
    @Before
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    @Test
    public void test01_all_employees_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<EmployeePojo> emps = response.readEntity(new GenericType<List<EmployeePojo>>(){});
        assertThat(emps, is(not(empty())));
      assertThat(emps, hasSize(2));
    }
    @Test
    public void test02_all_employees_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    @Test
    public void test03_employee_by_id_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
           // .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME+"/1")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        EmployeePojo emps = response.readEntity(new GenericType<EmployeePojo>(){});
        assertEquals(emps.getId(), 1);
        
    }
    @Test
    public void test04_employee_by_id_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
           // .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME+"/2")
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    @Test
    public void test05_employee_by_id_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(EMPLOYEE_RESOURCE_NAME+"/1")
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
}