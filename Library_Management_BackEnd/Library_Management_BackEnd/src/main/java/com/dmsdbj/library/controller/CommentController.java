package com.dmsdbj.library.controller;

import com.dmsdbj.library.viewmodel.Comment;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/comment")

@Api(value = "/comment", description = "the comment API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class CommentController {

    @POST
    @Path("/deletComment/{commentId}")

    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "Deletes a comment", notes = "", response = void.class, tags = "notes")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
        ,
        
          @ApiResponse(code = 404, message = "not found", response = void.class)})
    public Response deleteComment(@ApiParam(value = "to delete", required = true) @PathParam("commentId") Long commentId,
             @ApiParam(value = "") @HeaderParam("api_key") String apiKey,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @GET
    @Path("/getComment/{userId}")

    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "searching comment", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Comment.class, tags = "comment")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Comment.class)
        ,
        
          @ApiResponse(code = 400, message = "bad input parameter", response = Comment.class)})
    public Response getComment(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }
    
    //首页上的精彩评论的接口-郭晶晶-2017年9月8日
    @GET
    @Path("/getComment")

    @Produces({"application/json"})
    @ApiOperation(value = "search homeComment", notes = "search", response = Comment.class, tags = "home")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Comment.class)
        ,
        
        @ApiResponse(code = 400, message = "bad input parameter", response = Comment.class)})
    public Response getComment(@Context SecurityContext securityContext)
            throws NotFoundException {
        
        
        return null;
    }
}
