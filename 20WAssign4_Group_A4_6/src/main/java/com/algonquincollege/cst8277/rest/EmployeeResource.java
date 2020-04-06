package com.algonquincollege.cst8277.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.algonquincollege.cst8277.utils.MyConstants.*;

@Path(EMPLOYEE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @GET()
    public Response getAllEmployees()
    {
        return Response.ok().build();
    }
}