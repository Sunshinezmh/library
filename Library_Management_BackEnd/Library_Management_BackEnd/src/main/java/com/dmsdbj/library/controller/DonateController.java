
package com.dmsdbj.library.controller;

import com.dmsdbj.library.controller.util.HeaderUtil;
import com.dmsdbj.library.entity.TDonate;
import com.dmsdbj.library.service.DonateService;
import com.dmsdbj.library.viewmodel.Donate;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Path("/donate")

@Api(value = "/donate", description = "the donate API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class DonateController {


    @Inject
    private Logger log;

    @Inject
    private DonateService donateService;


    /**
     * 1,模糊查询所有捐赠书目(分页):  getDonate-何新生-2017-9-2
     * @param userId
     * @return
     * @throws Exception
     */
    @GET
    @Path("/getDonate/{userId}")
    @Consumes("application/json")
    @Produces({"application/json"})
    @ApiOperation(value = "searching donate record", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Donate.class, tags = "donate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Donate.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = Donate.class)})
    public Response searchingDonateRecord(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
                                          @Context SecurityContext securityContext)
            throws NotFoundException {

        List<TDonate> listDoante=donateService.getDonate(userId);

        return Optional.ofNullable(listDoante)
                .map(result -> Response.status(Response.Status.OK).entity(listDoante).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * 2,捐赠书:  addDonate-何新生-2017-9-2
     * @param donate
     * @return
     * @throws Exception
     */
    @POST
    @Path("/addDonate")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    //用在方法上，说明方法的作用
    @ApiOperation(value = "adds", notes = "Adds an item to the system", response = Donate.class, tags="donate")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = void.class),

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),

            @ApiResponse(code = 409, message = "an existing item already exists", response = void.class) })
    public Response addDonate(@ApiParam(value = "note that needs to be added" +
            " to the store", required = true) TDonate tDonate)
            throws NotFoundException, URISyntaxException {
        donateService.addDonate(tDonate);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI
                        ("/resources/Donate/addDonate/" + tDonate.getId())),
                "donate", tDonate.getId())
                .entity(tDonate).build();
    }

    /**
     * 3,根据状态查询捐赠的书:  findByStatus-何新生-2017-9-2
     *
     * @param userId,status
     * @return
     * @throws Exception
     */
    @GET
    @Path("/findByStatus/{status}")
    @Consumes("application/json")
    @Produces({"application/json"})
    @ApiOperation(value = "searching donate record", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Donate.class, tags = "donate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Donate.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = Donate.class)})
    public Response findDonateByStatus(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("status") String status,
                                          @Context SecurityContext securityContext)
            throws NotFoundException {

        List<TDonate> donates=donateService.findByStatus(status);

        return Optional.ofNullable(donates)
                .map(result -> Response.status(Response.Status.OK).entity(donates).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


}
