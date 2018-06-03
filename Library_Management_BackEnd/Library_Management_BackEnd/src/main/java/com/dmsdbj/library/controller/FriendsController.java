package com.dmsdbj.library.controller;

import com.dmsdbj.library.viewmodel.Comment;
import com.dmsdbj.library.viewmodel.Donate;
import com.dmsdbj.library.viewmodel.Friends;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/friends")

@Api(value = "/friends", description = "the friends API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class FriendsController {

    @POST
    @Path("/addfollower")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "Add a new note ", notes = "", response = void.class, tags = "notes")
    @ApiResponses(value = {
        @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
    public Response addFollower(@ApiParam(value = "note that needs to be added to the store", required = true) Friends body,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @POST
    @Path("/deleteFollower/{followerId}")

    @Produces({"application/json"})
    @ApiOperation(value = "Deletes a comment", notes = "", response = void.class, tags = "friends")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
        ,
        
        @ApiResponse(code = 404, message = "not found", response = void.class)})
    public Response deleteFolllower(@ApiParam(value = "to delete", required = true) @PathParam("followerId") Long followerId,
             @ApiParam(value = "") @HeaderParam("api_key") String apiKey,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @GET
    @Path("/getFans/{userId}/{followerId}")

    @Produces({"application/json"})
    @ApiOperation(value = "search fans", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Donate.class, tags = "friends")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Donate.class)
        ,
        
        @ApiResponse(code = 400, message = "bad input parameter", response = Donate.class)})
    public Response searchFans(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
             @ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("followerId") String followerId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @GET
    @Path("/getDynamic/{followerId}")

    @Produces({"application/json"})
    @ApiOperation(value = "search dynamic", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Comment.class, tags = "friends")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Comment.class)
        ,
        
        @ApiResponse(code = 400, message = "bad input parameter", response = Comment.class)})
    public Response searchiDynamic(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("followerId") String followerId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @GET
    @Path("/getFollower/{userId}")

    @Produces({"application/json"})
    @ApiOperation(value = "search follower", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Donate.class, tags = "friends")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Donate.class)
        ,
        
        @ApiResponse(code = 400, message = "bad input parameter", response = Donate.class)})
    public Response searchiFollower(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }
}
