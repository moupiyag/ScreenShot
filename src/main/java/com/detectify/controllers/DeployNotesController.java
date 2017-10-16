package com.detectify.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("deploy_notes/gen")
@Consumes(MediaType.APPLICATION_JSON)
public class DeployNotesController {
    @GET
    @Path("/{project: (.+)}/issues")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus(@PathParam("project") final String project,
                              @DefaultValue("deployed") @QueryParam("deployedSha") final String deployedSha,
                              @DefaultValue("master") @QueryParam("masterSha") final String masterSha) {
        return Response.status(Response.Status.OK).entity(new String("Hello World")).build();
    }

}
