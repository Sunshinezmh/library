package com.dmsdbj.library.controller;

import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.TAnnouncement;
import com.dmsdbj.library.service.RulesService;
import com.dmsdbj.library.viewmodel.Rule;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/rules")



@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-31T16:21:12.109+08:00")
public class RulesController  {
   
   @Inject
    private Logger log;

    @Inject
    private RulesService rulesService;
    
    @GET
    @Path("/getRules")
   
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "seach rules", notes = "seach rules", response = Rule.class, responseContainer = "List", tags={ "rules", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "search results matching criteria", response = Rule.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "bad input parameter", response = Rule.class, responseContainer = "List") })
    public Response rulesGetRulesGet( @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
    throws NotFoundException {
          List<TAnnouncement> listAnnouncement = rulesService.searchingOverview("2");
        return Optional.ofNullable(listAnnouncement)
                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum,pageSize,listAnnouncement)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
