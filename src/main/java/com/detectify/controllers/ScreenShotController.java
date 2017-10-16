/**
 * 
 */
package com.detectify.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

@Path("ScreenShot/takescreenshot")
@Consumes(MediaType.APPLICATION_JSON)
public class ScreenShotController {
	
ScreenShotService screenShotService = ContextProvider.getBean("screenShotService");
	
    @POST
    @Path("/byurls")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByUrl(@QueryParam("urls") final String urls) throws IOException {
    	
    	if(StringUtils.isEmpty(urls))
    		return Response.status(Response.Status.BAD_REQUEST).entity(new String("URL is blank")).build();
    	
    	List<File> screenShots = screenShotService.takeScreenShots(urls);
    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }
    
    @POST
    @Path("/byurls")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByFile(@QueryParam("file") final String filePath) throws IOException {
    	
    	if(StringUtils.isEmpty(filePath))
    		return Response.status(Response.Status.BAD_REQUEST).entity(new String("filePath is blank")).build();
    	
    	File file = new File(filePath);
    	List<File> screenShots = screenShotService.takeScreenShots(file);
    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }
    
}