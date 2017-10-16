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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.util.StringUtils;

import com.detectify.service.ScreenShotService;
import com.detectify.util.ContextProvider;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByUrl(@QueryParam("url") final String url) {
    	
    	if(StringUtils.isEmpty(url))
    		return Response.status(Response.Status.BAD_REQUEST).entity(new String("URL is blank")).build();
    	
    	List<File> screenShots = screenShotService.searchScreenShotsByUrl(url);
    	
    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }

}
