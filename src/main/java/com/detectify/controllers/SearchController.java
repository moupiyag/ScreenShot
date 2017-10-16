/**
 * 
 */
package com.detectify.controllers;

import java.io.File;
import java.text.ParseException;
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
    
    @GET
    @Path("/bydate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByDate(@QueryParam("date") final String date,
    		@QueryParam("dateFormat") final String dateFormat) throws ParseException {
    	
    	if(StringUtils.isEmpty(date) || StringUtils.isEmpty(dateFormat))
    		return Response.status(Response.Status.BAD_REQUEST)
    				.entity(new String("Date or Date format is blank")).build();
    	
    	List<File> screenShots = screenShotService.searchScreenShotsByDate(date, dateFormat);
    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }
    
    @GET
    @Path("/byurlanddate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByUrlAndDate(@QueryParam("url") final String url,
    		@QueryParam("date") final String date,
    		@QueryParam("dateFormat") final String dateFormat) throws ParseException {
    	
    	if(StringUtils.isEmpty(url) || StringUtils.isEmpty(date) || StringUtils.isEmpty(dateFormat))
    		return Response.status(Response.Status.BAD_REQUEST)
    				.entity(new String("Url or Date or Date format is blank")).build();
    	
    	List<File> screenShots = screenShotService.searchScreenShotsByUrlAndDate(url, date, dateFormat);
    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }
    
    @GET
    @Path("/bydaterange")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScreenShotsByDateRange(@QueryParam("startdate") final String startDate,
    		@QueryParam("enddate") final String endDate,
    		@QueryParam("dateFormat") final String dateFormat) throws ParseException {
    	
    	if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(dateFormat))
    		return Response.status(Response.Status.BAD_REQUEST)
    				.entity(new String("Start date or End date or Date format is blank")).build();
    	
    	List<File> screenShots = screenShotService.searchScreenShotsByDateRange(startDate, endDate, dateFormat);
    	return Response.status(Response.Status.OK).entity((Object)screenShots).build();
    }

}
