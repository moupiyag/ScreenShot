/**
 * 
 */
package com.detectify.controllers;

import java.io.File;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.util.StringUtils;

import com.detectify.service.ScreenShotService;
import com.detectify.util.ContextProvider;
import com.sun.jersey.multipart.MultiPart;

/**
 * @author Moupiya
 *
 */

@Path("ScreenShot/search")
@Consumes(MediaType.APPLICATION_JSON)
public class SearchController {
	
	ScreenShotService screenShotService = ContextProvider.getBean("screenShotService");
	
    @GET
    @Path("/byurl")
//    @Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.MULTIPART_FORM_DATA)
//    @Produces("image/jpg")
    public Response getScreenShotsByUrl(@QueryParam("url") final String url) {
    	
    	if(StringUtils.isEmpty(url))
    		return Response.status(Response.Status.BAD_REQUEST).entity(new String("URL is blank")).build();
    	
    	List<File> screenShots = screenShotService.searchScreenShotsByUrl(url);
    	MultiPart multipart = new MultiPart().bodyPart(entity, mediaType);
    	GenericEntity<List<File>> entity = new GenericEntity<List<File>>(screenShots) {};
    	ResponseBuilder response = Response.ok(entity);
		response.header("Content-Disposition",
			"attachment;");
		return response.build();
    	
//    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }

}
