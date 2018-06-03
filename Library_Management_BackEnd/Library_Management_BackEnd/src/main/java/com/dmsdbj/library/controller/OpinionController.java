package com.dmsdbj.library.controller;

import com.dmsdbj.library.controller.util.HeaderUtil;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.TOpinion;
import com.dmsdbj.library.service.OpinionService;
import com.dmsdbj.library.viewmodel.Opinion;
import com.dmsdbj.library.viewmodel.Reserve;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Path("/opinion")

@Api(value = "/opinion", description = "the opinion API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class OpinionController {

    @Inject
    private Logger log;

    @Inject
    private OpinionService opinionService;

    @POST
    @Path("/addOpinion")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Add a new opinion ", notes = "", response = void.class, tags = "opinion")
    @ApiResponses(value = {
        @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
    public Response addOpinion(@ApiParam(value = "note that needs to be added to the store", required = true) Opinion opinion)
            throws NotFoundException, URISyntaxException {
        opinionService.addOpinion(opinion);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/opinion/addOpinion/" + opinion.getId())),
                "opinion", opinion.getId())
                .entity(opinion).build();
    }

    @GET
    @Path("/findByStatus/{pageNum}/{pageSize}")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Finds opinion by status", notes = "Multiple status values can be provided with comma separated strings", response = TOpinion.class, tags = "opinion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = TOpinion.class)
        ,
        
          @ApiResponse(code = 400, message = "bad input parameter", response = TOpinion.class)})
    public Response findOpinionByStatus(@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = " 全部 ,  待解决 ") @QueryParam("status") List<String> status, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        if(status.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).tag("参数status为空").build();
        }
        List<TOpinion> list = opinionService.findOpinionByStatus(status);
//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum, pageSize, list)).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
        return Optional.ofNullable(list)
                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum, pageSize, list)).build())
                .orElse(Response.status(Response.Status.NO_CONTENT).build());
        
    }

    @GET
    @Path("/getOpinion/{userId}/{pageNum}/{pageSize}")
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "searching opinion", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Reserve.class, tags = "opinion")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Reserve.class)
        ,
          @ApiResponse(code = 400, message = "bad input parameter", response = Reserve.class)})
    public Response searchingOpinion(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        List<TOpinion> list = opinionService.searchingOpinion(userId);
        return Optional.ofNullable(list)
                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum, pageSize, list)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

}
