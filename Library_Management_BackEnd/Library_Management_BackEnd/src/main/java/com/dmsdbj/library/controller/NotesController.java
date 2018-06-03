package com.dmsdbj.library.controller;

import com.dmsdbj.library.viewmodel.Notes;
import com.dmsdbj.library.viewmodel.Reserve;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/notes")

@Api(value = "/notes", description = "the notes API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class NotesController {

    @POST
    @Path("/addNote")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "Add a new note ", notes = "", response = void.class, tags = "notes")
    @ApiResponses(value = {
        @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
    public Response addNote(@ApiParam(value = "note that needs to be added to the store", required = true) Notes body,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @POST
    @Path("/deleteNote/{noteId}")

    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "Deletes a note", notes = "", response = void.class, tags = "notes")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
        ,
        
          @ApiResponse(code = 404, message = "note not found", response = void.class)})
    public Response deleteNote(@ApiParam(value = "Note id to delete", required = true) @PathParam("noteId") Long noteId,
             @ApiParam(value = "") @HeaderParam("api_key") String apiKey,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @GET
    @Path("/getNotes/{userId}")

    @Produces({"application/json"})
    @ApiOperation(value = "searching notes", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Reserve.class, responseContainer = "List", tags = "notes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = Reserve.class)
        ,
        
          @ApiResponse(code = 400, message = "bad input parameter", response = Reserve.class)})
    public Response searchingNotes(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }

    @POST
    @Path("/updateNote/{noteId}")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "Updates a note  form data", notes = "", response = void.class, tags = "notes")
    @ApiResponses(value = {
        @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
    public Response updateNoteWithForm(@ApiParam(value = "ID of note that needs to be updated", required = true) @PathParam("noteId") Long noteId,
             @ApiParam(value = "Updated content of the note") @FormParam("content") String content,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        return null;
    }
}
