package com.dmsdbj.library.controller;


import com.dmsdbj.library.service.HomeService;
import com.wordnik.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/home")

@Api(value = "/home", description = "the home API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class HomeController {


    @Inject
    private HomeService homeservice;

//    @GET
//    @Path("/ getAnnouncement")
//
//    @Produces({"application/json"})
//    @ApiOperation(value = "get announcement", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Announcement.class, tags = "home")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "search results matching criteria", response = Announcement.class),
//            @ApiResponse(code = 400, message = "bad input parameter", response = Announcement.class)})
//    public Response getAnnouncement(@Context SecurityContext securityContext,@PathParam("id") String id)
//            throws NotFoundException {
//        List<TAnnouncement> list= homeservice.getAnnouncement();
//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
//
//
//    }

//    @GET
//    @Path("/getBookRank/{rank}")
//
//    @Produces({"application/json"})
//    @ApiOperation(value = "search bookeRank", notes = "获取信息", response = BookRank.class, tags = "home")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "search results matching criteria", response = BookRank.class),
//        @ApiResponse(code = 400, message = "bad input parameter", response = BookRank.class)})
//    /*
//    *获得阅读排行-郭晶晶-2017年9月8日21:03:22
//    */
//    public Response getReadArticles(@ApiParam(value = "URL", required = true) @PathParam("rank") String rank,
//             @Context SecurityContext securityContext)
//            throws NotFoundException {
//
//        return null;
//    }
//
//    @GET
//    @Path("/getBookeType")
//
//    @Produces({"application/json"})
//    @ApiOperation(value = "search bookeType", notes = "获取信息", response = BookType.class, tags = "home")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "search results matching criteria", response = BookType.class)
//        ,
//
//        @ApiResponse(code = 400, message = "bad input parameter", response = BookType.class)})
//    /*
//    * 获得图书一级分类-郭晶晶-2017年9月8日16:24:29
//    */
//    public Response getParentBookType(@Context SecurityContext securityContext)
//            throws NotFoundException {
//        List<TBookAndType> list =homeservice.getParentBookType();
//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
//
//    }
//
//    /*
//    *根据父ID查找相对应的子类ID-郭晶晶-2017年9月8日20:57:01
//    *param Pid  父ID
//    *result list<TBookType>
//    */
//    public Response getChildBookType(@QueryParam("Pid") String Pid,@Context SecurityContext securityContext)
//            throws NotFoundException {
//        List<TBookAndType> list =homeservice.getChildBookType(Pid);
//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
//
//    }
//
//    @GET
//    @Path("/getnewBookBystatus")
//
//    @Produces({"application/json"})
//    @ApiOperation(value = "search homeNewBook", notes = "search", response = Newbook.class, tags = "home")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "search results matching criteria", response = Newbook.class)
//        ,
//
//        @ApiResponse(code = 400, message = "bad input parameter", response = Newbook.class)})
//    /*
//    *实现首页上新书推荐接口-郭晶晶-2017年9月8日16:23:39
//    *
//    */
//    public Response getNewBook(@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "  小说  ,   期刊  ,  漫画 ") @QueryParam("status") List<String> status,
//             @Context SecurityContext securityContext)
//            throws NotFoundException {
//
//        //调用homeservice的getNewbook方法-郭晶晶-2017年9月8日16:22:56
//
//        List<TNewbook>list= homeservice.getNewBook(status);
//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
//    }
//}
}