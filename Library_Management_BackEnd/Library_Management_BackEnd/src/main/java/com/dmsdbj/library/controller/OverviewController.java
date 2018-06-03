package com.dmsdbj.library.controller;

import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.TAnnouncement;
import com.dmsdbj.library.service.OverviewService;
import com.dmsdbj.library.viewmodel.Overview;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/overview")

@Api(value = "/overview", description = "the overview API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class OverviewController {

    @Inject
    private Logger log;

    @Inject
    private OverviewService overviewService;

    @GET
    @Path("/getOview/{pageNum}/{pageSize}")

    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "searching overview", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Overview.class, responseContainer = "List", tags = "overview")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Overview.class)
        ,
        
        @ApiResponse(code = 400, message = "bad input parameter", response = Overview.class)})
    public Response searchingOverview( @DefaultValue("1") @PathParam("pageNum") int pageNum,@DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        List<TAnnouncement> listAnnouncement = overviewService.searchingOverview("1");
        return Optional.ofNullable(listAnnouncement)
                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum,pageSize,listAnnouncement)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
