/**
 * 
 */
package com.detectify.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.detectify.service.RequestProducer;
import org.springframework.util.StringUtils;

import com.detectify.util.ScreenShotUtility;

/**
 * @author Moupiya
 *
 */

@Path("screenShot/takeScreenShot")
@Consumes(MediaType.APPLICATION_JSON)
public class ScreenShotController {

    @POST
    @Path("/byUrls")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByUrl(final List<String> urls) throws IOException {
        RequestProducer.produceRequest(urls);
    	return Response.status(Response.Status.OK).entity(new String("Taking Screenshots...")).build();
    }
    
    @POST
    @Path("/byFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByFile(@QueryParam("file") final String filePath) throws IOException {
    	
    	if(StringUtils.isEmpty(filePath))
    		return Response.status(Response.Status.BAD_REQUEST).entity(new String("filePath is blank")).build();
    	
    	File file = new File(filePath);
    	RequestProducer.produceRequest(ScreenShotUtility.getUrlsFromFile(file));
    	return Response.status(Response.Status.OK).entity(new String("Taking Screenshots...")).build();
    }
}
