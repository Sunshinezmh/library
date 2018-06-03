package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.TCollection;
import com.dmsdbj.library.service.CollectionService;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.viewmodel.Collection;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.net.URISyntaxException;
import java.util.List;

@Path("/collection")

@Api(value = "/collection", description = "the collection API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class CollectionController {
    @Inject
    private Logger log;
    @Inject
    private CollectionService CollectionService;

    /**
     * 批量删除图书+田成荣+2017年10月15日11:30:14
     * 修改：更改删除方式由单一循环为批量删除 郑晓东 2017年11月9日10点58分
     * @param deleteCollectionIdsArr 收藏ID 字符串数组
     * @param securityContext
     * @return ItooResult
     * @throws NotFoundException
     */
    @POST
    @Path("/deleteCollections/{deleteCollectionIds}")

    @Produces({ "application/json" })
    @ApiOperation(value = "Deletes book（批量删除图书）", notes = "", response = void.class, tags="PC")
    @ApiResponses(value = {

            @ApiResponse(code = 200, message = "delete succeed", response = void.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),

            @ApiResponse(code = 404, message = "not found", response = void.class) })
    public ItooResult deleteBookTypeArr(@ApiParam(value = "booktype id to delete",required=true) @PathParam("deleteCollectionIds") String[] deleteCollectionIdsArr
            , @Context SecurityContext securityContext)throws NotFoundException {
        boolean flag;
        deleteCollectionIdsArr=deleteCollectionIdsArr[0].split(",");

        flag=CollectionService.deleteCollections(deleteCollectionIdsArr);
        if (flag = true) {
            log.error("删除成功！");
            return ItooResult.build("0000", "删除收藏成功",flag);
        } else {
            log.error("删除失败！");
            return ItooResult.build("1111", "删除收藏失败");
        }
    }

    /**
     * 根据UserID查询收藏+田成荣+2017年10月15日11:30:14
     * 修改：修改后台实现逻辑 郑晓东 2017年11月9日11点00分
     * 修改：返回结果添加location 张明慧  2018年5月1日18:44:42
     * @param userId 用户ID
     * @param securityContext
     * @return ItooResult
     * @throws NotFoundException
     */
     @GET
    @Path("/getCollectionByuserId/{userId}")

    @Produces({"application/json"})
    @ApiOperation(value = "searching collection", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = BookViewModel.class, tags = "collection")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = BookViewModel.class),
        @ApiResponse(code = 400, message = "bad input parameter", response = BookViewModel.class)})
     public ItooResult getCollectionByuserId(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
	    List<BookViewModel> list = CollectionService.getCollectionByuserId(userId);
	    if (list!=null &&list.size()>0) {
             log.error("查询收藏成功！");
              return  ItooResult.build("0000", "查询收藏成功",list);
        } else {
            log.error("查询收藏失败！");
		    return ItooResult.build("1111", "查询收藏失败");
        }
        
    }

    /**
     * 收藏添加+田成荣+2017年10月15日11:29:52
     * @param tCollection
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
     @POST
    @Path("/addBook")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    //用在方法上，说明方法的作用
    @ApiOperation(value = "adds", notes = "Adds an item to the system", response = Collection.class, tags="book")
    //表示一组响应
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "item created", response = void.class),
        
        @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),
        
        @ApiResponse(code = 409, message = "an existing item already exists", response = void.class) })
    public ItooResult addBook(@ApiParam(value = "note that needs to be added" +
            " to the store", required = true) TCollection tCollection)
    throws NotFoundException, URISyntaxException {
       boolean flag = false;
       flag = CollectionService.addCollection(tCollection);
       if (flag == false) {
           log.error("收藏失败！");
           return ItooResult.build("1111", "收藏失败");
       } else {
           log.error("收藏成功！");
           return  ItooResult.build("0000", "收藏成功",tCollection);
       }
    }


    //根据bookId查isbn，再拿着isbn和UserId查询书架上信息+田成荣。
    @GET
    @Path("/getCollectionIdBybookId/{bookid}/{userid}")

    @Produces({"application/json"})
    @ApiOperation(value = "searching collection", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = BookViewModel.class, tags = "collection")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "search results matching criteria", response = TCollection.class),
        @ApiResponse(code = 400, message = "bad input parameter", response = TCollection.class)})
    public ItooResult getCollectionIdBybookId(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("bookid") String bookid,@PathParam("userid") String userid,
             @Context SecurityContext securityContext)throws NotFoundException {
        List<TCollection> list = CollectionService.getCollectionIdBybookId(bookid,userid);
        if (list!=null &&list.size()>0) {
            log.error("查询成功！");
            return  ItooResult.build("0000", "查询收藏成功",list);
        } else {
            log.error("查询成功！");
            return ItooResult.build("1111", "查询收藏失败");
        }
        
    }

    /**
     * 输入书名在书架中搜索图书--张明慧--2018年5月21日21:03:32
     *
     * @param MoHu_str
     * @param userid
     * @return
     * @throws NotFoundException
     */

    @GET
    @Path("/findColByCondition/{MoHu_str}/{userId}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据用户名和书名进行模糊查询",notes = "根据用户名和书名进行模糊查询",response = BookViewModel.class,responseContainer = "List",tags = "Admin")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class),
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})

    public ItooResult findColByCondition(@ApiParam(value = "输入的作者，书名还有userid",required = true)@PathParam("MoHu_str") String MoHu_str,@PathParam("userId") String userId,@Context SecurityContext securityContext) throws NotFoundException{
        List<BookViewModel> list=CollectionService.findColByCondition(MoHu_str,userId);
        if (list != null || list.size() >=0)
        {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", list);
        }else
        {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }
}
