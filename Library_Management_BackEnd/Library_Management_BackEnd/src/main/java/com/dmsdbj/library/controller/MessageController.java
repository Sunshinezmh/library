package com.dmsdbj.library.controller;

import com.dmsdbj.library.entity.TMessageRecord;
import com.dmsdbj.library.service.MessageService;
import com.dmsdbj.library.viewmodel.Message;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;

@Path("/message")

@Api(value = "/message", description = "the message API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class MessageController {

    @Inject
    private Logger log;

    @Inject
    private MessageService messageService;


    /**
     * 1,查看消息:  getMessage-何新生-2017-9-2
     * @param userId,typeId
     * @return
     * @throws Exception
     */
    @GET
    @Path("/getMessage/{userId}")

    @Produces({"application/json"})
    @ApiOperation(value = "search message", notes = "获取信息", response = Message.class, tags = "messages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Message.class)
            ,
            @ApiResponse(code = 400, message = "bad input parameter", response = Message.class)})
    public Response searchMessage(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,

                                  @Context SecurityContext securityContext)
            throws NotFoundException {

        List<TMessageRecord> messageModelList=messageService.getMessage(userId);

        return Optional.ofNullable(messageModelList)
                .map(result -> Response.status(Response.Status.OK).entity(messageModelList).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
