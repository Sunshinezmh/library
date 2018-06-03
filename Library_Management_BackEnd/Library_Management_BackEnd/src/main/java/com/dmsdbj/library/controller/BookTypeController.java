package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.controller.util.UploadFileUtil;
import com.dmsdbj.library.entity.TBookAndType;
import com.dmsdbj.library.service.BookService;
import com.dmsdbj.library.service.BookTypeService;
import com.dmsdbj.library.viewmodel.BookType;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.wordnik.swagger.annotations.*;
import org.jplus.hyberbin.excel.utils.ExcelUtil;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/bookType")

@Api(value = "/bookType", description = "the bookType API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-09-24T17:35:42.344+08:00")
public class BookTypeController {

    @Inject
    private Logger log;

    @Inject
    private BookTypeService bookTypeService;

    @Inject
    private BookService bookService;

    /**
     * 添加一条类型--刘雅雯--2017年10月31日11:59:22
     *
     * @param tBookAndType
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/addBookType")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "adds", notes = "Adds an item to the system", response = void.class, tags = "Admin")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "item created", response = void.class)
        ,
        
        @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class)
        ,
        
        @ApiResponse(code = 409, message = "an existing item already exists", response = void.class)})
    public ItooResult addBookType(@ApiParam(value = "Inventory item to add") TBookAndType tBookAndType,
             @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException {

        //判断是否有该类别名称
        List<TBookAndType> list = bookTypeService.findTypeIdByTypeName(tBookAndType.getName());
        if (list != null && list.size() != 0) {
            return ItooResult.build("1111", "该类别已存在");
        } else {
           boolean flag = bookTypeService.addBookType(tBookAndType);
            if (flag == false) {
                log.error("添加一条图书类型失败");
                return ItooResult.build("1111", "类别添加失败");

            } else {
                log.info("添加一条图书信息成功");
                return ItooResult.build("0000", "图书信息添加成功", tBookAndType);
            }

//            return HeaderUtil.createEntityCreationAlert(Response.created(new URI
//                            ("/resources/bookType/addBookType/" + tBookAndType.getId())),
//                    "bookAndType", tBookAndType.getId())
//                    .entity(tBookAndType).build();
        }

    }


    /**
     * 查找父类类别--刘雅雯--2017年10月10日18:52:29
     *
     * @param booktype
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/initTree")
    @Produces({"application/json"})
    @ApiOperation(value = "图书分类后台管理中树", notes = "查找一级图书类别 ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "获得所有一级类别", response = BookType.class)
        ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult bookTypeGetParent()
            throws NotFoundException {
        List<TBookAndType> list = bookTypeService.findParentBookType();
        if (list != null ) {
            log.info("可获得所有类别成功");
            return ItooResult.build("0000", "获取父类成功", list);
        } else {
            log.error("获得所有一级类别失败");
            return ItooResult.build("1111", "获取父类失败");
        }

//            return Optional.ofNullable(list)
//                    .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                    .orElse(Response.status(Response.Status.NOT_FOUND).build());findShelfBookType
    }

    /**
     * 查找二级分类--田成荣--2018年5月2日10:46:31
     *
     * @param booktype
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
        @Path("/shelfType/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "图书分类后台管理中树", notes = "查找所有二级图书类别 ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得所有二级类别", response = BookType.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult findShelfBookType(@ApiParam(value = "书籍位置", required = true) @PathParam("location") String location,
                                        @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookTypeService.findShelfBookType(location);
         if (list != null ) {
            log.info("可获得所有二类别成功");
            return ItooResult.build("0000", "获取二级分类成功", list);
        } else {
            log.error("获得所有二级类别失败");
            return ItooResult.build("1111", "获取二级分类失败");
        }

    }

    /**
     * 根据二级分类和location查询书--田成荣--2018年5月2日15:03:10
     *
     * @param booktype
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findBookbyShelf/{Location}/{typeID}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "图书分类后台管理中树", notes = "查找书，根据二级图书类别 ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查找书，根据二级图书类别", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findBookbyShelfandLocation(@ApiParam(value = "Location", required = true) @PathParam("Location") String Location,
                                                 @PathParam("typeID") String typeID,
                                              @PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize,
                                              @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookTypeService.findBookbyShelfandLocation(Location,typeID,pageNum,pageSize);
        if (list != null ) {
            log.info("查找书，根据二级图书类别成功");
            return ItooResult.build("0000", "查找书，根据二级图书类别成功", list);
        } else {
            log.error("查找书，根据二级图书类别失败");
            return ItooResult.build("1111", "查找书，根据二级图书类别失败");
        }

    }



    /**
     * 根据pId查询图书类别--刘雅雯--2017年10月10日19:42:34
     *
     * @param pId
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getSubByID/{bookTypeId}")
    @Produces({"application/json"})
    @ApiOperation(value = "图书管理页面点击上一级图书分类获取下一级图书分类", notes = "根据父类ID查找类别 ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "获得相应子类图书类别", response = BookType.class)
        ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult bookTypeGetBypId(@ApiParam(value = "父类ID", required = true) @PathParam("bookTypeId") String pId,
             @Context SecurityContext securityContext)
            throws NotFoundException {

        List<TBookAndType> list = bookTypeService.findBookTypeBypId(pId);


        if (list != null ) {
            log.info("根据id获得图书类别成功");
            return ItooResult.build("0000", "获得相应子类图书类别成功", list);
        } else {
            log.error("获得相应子类图书类别失败");
            return ItooResult.build("1111", "获得相应子类图书类别失败");
        }

//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * 根据图书类别名称查询图书的类别id--刘雅雯--2017年10月31日10:07:52
     *
     * @param bookTypeName
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookTypeName/{bookTypeName}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据图书类别名称查询图书的类别id", notes = "根据图书类别名称查询图书的类别id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "获得相应子类图书类别id", response = BookType.class)
        ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult getBookTypeNameById(@ApiParam(value = "图书类别名称", required = true) @PathParam("bookTypeName") String bookTypeName,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        List<TBookAndType> list = bookTypeService.findTypeIdByTypeName(bookTypeName);
        if (list !=null) {
            log.info("根据类别名称查询图书的类别id成功");
            return ItooResult.build("0000", "根据图书类别名称查询图书的类别id成功", list);
        } else {
            log.error("根据图书类别名称查询图书的类别id失败");
            return ItooResult.build("1111", "根据图书类别名称查询图书的类别id失败");
        }
//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(list).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * 图书管理页面点击上一级图书分类获取下一级图书分类及图书--刘雅雯--2017年11月9日20:29:00
     * @param pId
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookTypeAndBookBypId/{typeId}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "图书管理页面点击上一级图书分类获取下一级图书分类及图书", notes = "根据父类ID查找类别 ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得相应子类图书类别及图书", response = BookType.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult getBookTypeAndBookBypId(@ApiParam(value = "父类ID", required = true) @PathParam("typeId") String typeId,
                                               @PathParam("pageNum") Integer pageNum,@PathParam("pageSize") Integer pageSize,
                                               @Context SecurityContext securityContext)
            throws NotFoundException {

        try{
            PaginationEntity paginationEntity   =bookService.getBookByTypeIdGroupByIsbnByPagination(typeId,pageNum,pageSize);
            if(paginationEntity != null && paginationEntity.getListRows()!= null && paginationEntity.getListRows().size()>0){
                return ItooResult.build("0000", "查询成功",paginationEntity);
            }else{
                return ItooResult.build("0000", "没有查询到可用记录",paginationEntity);
            }
        }catch(Exception e){
            return ItooResult.build("1111", "根据图书类别显示子类及图书失败",false);
        }


    }

    /**
     * 模糊查询图书类别名称--刘雅雯--2017年10月7日20:56:27
     *
     * @param booktype
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookTypeByConditon/{conditons}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "书城", notes = "根据查询条件获取图书分类", response = BookType.class, responseContainer = "List", tags = "User")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "get bookType Succeed", response = BookType.class)
        ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult bookTypeGetByName(@io.swagger.annotations.ApiParam(value = "图书类名", required = true) @PathParam("conditons") String booktype, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
             @Context SecurityContext securityContext)
            throws NotFoundException {

        log.debug("rest request to get BookType:{}", booktype);
        List<TBookAndType> list = bookTypeService.findBookTypeByName(booktype);

        if (list != null) {
            log.info("根据查询条件获取图书分类信息成功");
            return ItooResult.build("0000", "根据查询条件获取图书分类信息成功",list);
        } else {
            log.error("根据查询条件获取图书分类信息失败");
            return ItooResult.build("1111", "根据查询条件获取图书分类信息失败");
        }

//        return Optional.ofNullable(list)
//                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum, pageSize, list)).build())
//                .orElse(Response.status(Response.Status.NO_CONTENT).build());
    }

    @GET
    @Path("/getAllType/{pageNum}/{pageSize}")
    @Produces({"applicatio"
        + "n/json"})
    @ApiOperation(value = "图书管理页面加载", notes = "查找所有的图书分类 ", response = BookType.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "获得所有分类", response = BookType.class)
        ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult getAllbookType(@io.swagger.annotations.ApiParam(value = "图书分类", required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        List<TBookAndType> list = bookTypeService.findAllbookType();
        if (list != null ) {
            log.info("查找所有的图书分类成功");
            return ItooResult.build("0000", "查找所有的图书分类成功", list);
        } else {
            log.error("查找所有的图书分类失败");
            return ItooResult.build("1111", "查找所有的图书分类失败");
        }
    }

    @POST
    @Path("/deleteBookType/{bookTypeId}")

    @Produces({"application/json"})
    @ApiOperation(value = "删除一个图书类别", notes = "", response = void.class, tags = "PC")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
        ,
        
        @ApiResponse(code = 404, message = "not found", response = void.class)})
    public ItooResult deleteBookType(@ApiParam(value = "booktype id to delete", required = true) @PathParam("bookTypeId") String bookTypeId,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        log.debug("REST request to delete BookAndType:{}", bookTypeId);
//        bookTypeId = bookTypeId.replace(" ","");
        boolean flag = bookTypeService.getChildBypId(bookTypeId);
        
        if (flag == true) {
            log.info("删除成功");
            return ItooResult.build("0000", "删除成功", flag);
        } else {
            log.error("删除失败");
            return ItooResult.build("1111", "删除失败");
        }

    }

    /**
     * 批量删除图书类别--刘雅雯--2017年10月7日20:40:41
     *
     * @param bookTypeId
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/deleteBookTypes/{bookTypeIds}")

    @Produces({"application/json"})
    @ApiOperation(value = "Deletes booktype（批量删除图书类别）", notes = "", response = void.class, tags = "PC")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "delete succeed", response = void.class)
        ,
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
        ,

            @ApiResponse(code = 404, message = "not found", response = void.class)})
    public ItooResult deleteBookTypeArr(@ApiParam(value = "booktype id to delete", required = true) @PathParam("bookTypeIds") String bookTypeIdArr,
             @Context SecurityContext securityContext)
            throws NotFoundException {
        String[] bookTypeIdArray;
        bookTypeIdArray = bookTypeIdArr.split(",");
        boolean flag = false;
        for (int i = 0; i < bookTypeIdArray.length; i++) {
            flag = bookTypeService.getChildBypId(bookTypeIdArray[i]);

        }
        if (flag == true) {
            log.info("删除成功");
            return ItooResult.build("0000", "删除成功", flag);
        } else {
            log.error("删除失败");
            return ItooResult.build("1111", "删除失败");
        }
//        return HeaderUtil.createAlertArr(Response.ok(), "BookAndType.deleted",bookTypeIdArray).build();

    }

    @POST
    @Path("/updateBookType")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑图书分类信息", notes = "", response = void.class, tags = "Admin")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
        ,
        
        @ApiResponse(code = 404, message = "not found", response = void.class)
        ,
        
        @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateBookType(@ApiParam(value = "提交更改的信息", required = true) TBookAndType tBookAndType,
             @Context SecurityContext securityContext)
            throws NotFoundException {
       boolean flag = bookTypeService.updateBookType(tBookAndType);
        if (flag == false) {
            log.error("更新图书失败");
            return ItooResult.build("1111", "更新图书失败");

        } else {
            log.info("更新图书成功");
            return ItooResult.build("0000", "图书信息更新成功", tBookAndType);
        }
//        return HeaderUtil.createEntityUpdateAlert(Response.ok(), "tBookAndType", tBookAndType.getId().toString())
//                .entity(tBookAndType).build();
    }
    @POST
    @Path("/importBookType/")
    @Consumes({"multipart/form-data"})
    @Produces({ "application/json" })
    @ApiOperation(value = "通过excel导入图书分类", notes = "Adds items to the system", response = void.class, tags= "PC")
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "item created", response = void.class),
        
        @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),
        
        @ApiResponse(code = 409, message = "an existing item already exists", response = void.class) })

    /**
     * 导入-刘雅雯重构--2017年11月3日20:30:33
     * 
     */
    public ItooResult importBookType(@Context HttpServletResponse response,@Context HttpServletRequest request)
    throws NotFoundException ,Exception{

        String filePath = UploadFileUtil.upload(response, request);
        Map<Serializable,Serializable> map = new HashMap<>();
        map.put("sheetName","sheet1");
        map.put("Class",BookType.class);

        //将查询出来的外键关联值放入map中
        Map<Serializable,Serializable> dictDataMap=new HashMap<>();
        Map<Serializable,Serializable> classData=new HashMap<>();



        //当插入子类和父类时：先查询导入中是否有父类，如果没有的话就先添加父类
        try{
            //获取excel的数据
            List<BookType> importListParentType =ExcelUtil.importExcel(filePath,response,map);
            TBookAndType bookTypeEntity =new TBookAndType();
            BookType bookTypeList =new BookType();
            for (int i=0; i<importListParentType.size();i++){
                bookTypeList=importListParentType.get(i);
                //判断是父类名称是否为null，如果为null则不用添加
                if (bookTypeList.getPID()!=null)
                {
                    //通过父类名称查询是否有该条记录
                    List<TBookAndType> bookTypeTemp=bookTypeService.findTypeIdByTypeName(bookTypeList.getPname());

                    //没有这条记录，则添加一条
                    if (bookTypeTemp!=null && bookTypeTemp.size()==0){
                        bookTypeEntity.setpId("0");
                        bookTypeEntity.setName(bookTypeList.getPname());
                        bookTypeEntity.setId(bookTypeList.getId());
                        bookTypeEntity.setShelfId("");
                        bookTypeEntity.setRemark(bookTypeList.getRemark());
                        bookTypeEntity.setoperator("");

                        //添加
                        bookTypeService.addBookType(bookTypeEntity);

                    }else{
                        continue;
                    }
                }else{
                    continue;
                }

            }
        }
        catch (Exception e)
        {
            log.error("导入失败");
            return ItooResult.build("1111", "教师信息导入失败");
        }

        //获取父类名称和键值的对应关系
        List<TBookAndType> tBookAndTypeList =bookTypeService.findAllbookType();
        if (tBookAndTypeList!=null && tBookAndTypeList.size()!=0){
            for (int i =0 ;i<tBookAndTypeList.size();i++){
                classData.put(tBookAndTypeList.get(i).getId(),tBookAndTypeList.get(i).getName());
            }
        }
        dictDataMap.put("PID",(Serializable)classData);

       boolean flag = false;

        try{
            //获取excel中的数据
            List<BookType> importList = ExcelUtil.importExcel(filePath,response,map,dictDataMap);
            TBookAndType bookTypeEntity = new TBookAndType();
            BookType bookType =new BookType();

            for(int n = 0; n<importList.size();n++){
                bookType = importList.get(n);

                //判断要添加的类别名称是否已经存在，若存在执行n+1
                List<TBookAndType> bookTypeList=bookTypeService.findTypeIdByTypeName(bookType.getName());
                if (bookTypeList!=null && bookTypeList.size()!=0){
                    continue;
                }

                bookTypeEntity.setId(bookType.getId());
                bookTypeEntity.setName(bookType.getName());
                if (bookType.getPID()==null){
                    bookTypeEntity.setpId("0");
                }else {
                    bookTypeEntity.setpId(bookType.getPID());
                }
                bookTypeEntity.setRemark(bookType.getRemark());
                bookTypeEntity.setShelfId("");
                bookTypeEntity.setoperator("");

                bookTypeService.addBookType(bookTypeEntity);
            }
            log.info("导入成功");

            return ItooResult.build("0000", "图书类型信息导入成功",true);
        }catch(Exception e){
            log.error("导入失败");
            return ItooResult.build("1111", "教师信息导入失败");
         }
        
    }

    /**
     * 下载模板--刘雅雯--2017年11月3日20:31:24
     * @param requst
     * @param response
     * @return
     */
    @GET
    @Path("/downBookType")
    @ApiOperation(value = "下载模板", response = BookType.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "成功", response = BookType.class)
        ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult downExcel(@Context HttpServletRequest  requst,@Context HttpServletResponse  response){
        Map<Serializable,Serializable> map=new HashMap<>();
        //sheet的名字
        map.put("sheetName","sheet1");
        map.put("columns",new String[]{"name","PID","Pname","remark"});
        //导出的表格标题
        map.put("title","图书类别管理");

        List<BookType> bookTypeList = new ArrayList<>();
        BookType bookTypeView=new BookType();
        bookTypeView.setName("诗歌");
        bookTypeView.setPID("文学类");
        bookTypeView.setPname("文学类");
        bookTypeView.setRemark("刘雅雯测试数据");

        bookTypeList.add(bookTypeView);
        map.put("dataList",(Serializable)bookTypeList);

        try {
            ExcelUtil.exportExcel(map,response);
            log.info("下载模板成功");
            return  ItooResult.build("0000", "下载成功");
                   
        } catch (Exception e) {
            log.error("下载模板失败");
            return ItooResult.build("1111", "下载失败");
        }
    }

    /**
     * 导出--刘雅雯--2017年11月4日20:02:23
     * @param response
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @GET
    @Path("/exportBookType/{conditons}")
    @Produces({"application/json"})
    @ApiOperation(value = "导出", notes = "下载模板  ", response = void.class, tags= "PC" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "导出成功", response = void.class)
            ,

            @ApiResponse(code = 400, message = "导出失败", response = void.class)})
    public ItooResult exportBookType(@Context HttpServletResponse response,@PathParam("conditons") String booktype) throws NotFoundException ,Exception {
        Map<Serializable,Serializable> map=new HashMap<>();
        booktype = booktype.trim();
        if (booktype.isEmpty()){
            List<TBookAndType> bookTypeList = bookTypeService.findAllbookType();
            map.put("sheetName","sheet1");
            //要导出的字段
            map.put("columns",new String[]{"name","remark"});
            map.put("title","图书类别管理");
            map.put("dataList",(Serializable)bookTypeList);
        }
        else{
            //获取要导出的全部数据
            List<TBookAndType> bookTypeList =  bookTypeService.findBookTypeByName(booktype);
            map.put("sheetName","sheet1");
            //要导出的字段
            map.put("columns",new String[]{"name","remark"});
            map.put("title","图书类别管理");
            map.put("dataList",(Serializable)bookTypeList);
        }

        try {
            log.info("导出成功");
            ExcelUtil.exportExcel(map,response);
            return  ItooResult.build("0000","导出成功");
        } catch (Exception e) {
            log.error("导出失败");
            return ItooResult.build("1111", "导出失败");
        }
}
}    
