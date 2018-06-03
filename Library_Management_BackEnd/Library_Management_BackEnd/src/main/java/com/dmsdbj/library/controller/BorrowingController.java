package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.app.util.BookNetInfoUtil;
import com.dmsdbj.library.app.util.FastDfsUtils;
import com.dmsdbj.library.app.util.HttpRequestUtil;
import com.dmsdbj.library.app.util.sign;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.controller.util.UploadFileUtil;
import com.dmsdbj.library.entity.TBorrowing;
import com.dmsdbj.library.entity.TBorrowingModel1;
import com.dmsdbj.library.repository.TBorrowingRepository;
import com.dmsdbj.library.service.BookService;
import com.dmsdbj.library.service.BorrowingService;
import com.dmsdbj.library.service.StudentService;
import com.dmsdbj.library.viewmodel.BookType;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.viewmodel.Student;
import com.wordnik.swagger.annotations.*;


import net.sf.json.JSONObject;
//import org.json.JSONObject;


import org.jplus.hyberbin.excel.utils.ExcelUtil;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/borrowing")

@Api(value = "/borrowing", description = "the borrowing API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-09-26T16:04:17.815+08:00")
public class BorrowingController {

    @Inject
    private Logger log;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private StudentService studentService;
    @Inject
    private BookService bookService;   


    /**
     * 王华伟 --2017--10--23--获取所有借阅记录--2018年3月28日15:27:45修改（王华伟修改）
     *
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getAllBorrowing/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "获取所有借阅记录", notes = "获取所有借阅记录 ", response = BookViewModel.class, responseContainer = "List", tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取所有借阅记录", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getAllBorrowing(@ApiParam(value = "借阅记录", required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        List<BookViewModel> list = borrowingService.getAllBorrowing();
        if (list != null || list.size() >= 0) {
            log.info("查找全部借阅记录成功");
            return ItooResult.build("0000", "查找全部借阅记录成功", list);
        } else {
            log.error("查找全部借阅记录失败");
            return ItooResult.build("1111", "查找全部借阅记录失败");
        }
    }

    /**
     * 王雅静 --2018年4月24日10:32:11--获取所有已借借阅记录
     *
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getAllBorrowedRecoed/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "获取所有已借借阅记录", notes = "获取所有已借借阅记录 ", response = TBorrowingModel1.class, responseContainer = "List", tags = "APP")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取所有已借借阅记录", response = TBorrowingModel1.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = TBorrowingModel1.class)})
    public ItooResult getAllBorrowedRecoed(@ApiParam(value = "已借借阅记录", required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        List<BookViewModel> list = borrowingService.getAllBorrowedRecord();
        if (list != null || list.size() >= 0) {
            log.info("查找借阅记录成功");
            return ItooResult.build("0000", "查找已借借阅记录成功", list);
        } else {
            log.error("查找已借借阅记录失败");
            return ItooResult.build("1111", "查找已借借阅记录失败");
        }
    }

    @GET
    @Path("/downExcel")
    @ApiOperation(value = "下载模板", response = BookType.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = BookType.class),
            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult downExcelGet(@Context HttpServletRequest requst, @Context HttpServletResponse response) {
    Map<Serializable, Serializable> map = new HashMap<>();
        // sheet的名字
        map.put("sheetName", "Sheet1");
        //需要导出的字段
        map.put("columns", new String[]{ "studentName", "bookName","isbn","borrowTime","endDate","realDate","isOverdue","renew","location"});
        //导出的表格标题
        map.put("title", "借阅记录统计");
        //getlist()为你要导出的数据，可替换为自己的list
        List<BookViewModel> borrowingList = new ArrayList<>();
        BookViewModel borrowingModel1 =   borrowingService.getAllBorrowing().get(0);
        borrowingList.add(borrowingModel1);
        
        map.put("dataList", (Serializable) borrowingList);
        try {
            ExcelUtil.exportExcel(map, response);
            log.info("借阅记录下载成功");
            return ItooResult.build("0000", "下载成功");
        } catch (Exception e) {
            log.error("借阅记录下载失败");
            return ItooResult.build("1111", "下载失败");
        }
    }


    /**
     *添加（电脑端）--郭晶晶--2017-11-9 20:28:13
     * @param tBorrowingModel1
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/addBorrowing")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "添加（电脑端）", notes = "Adds an item to the system", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = Void.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = Void.class)})
    public ItooResult addBorrowing(@ApiParam(value = "Inventory item to add") TBorrowingModel1 tBorrowingModel1, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException {

        boolean flag = false;

        flag =  borrowingService.addBorrowing(tBorrowingModel1);
        if (flag == false) {
            log.error("借阅记录添加失败");
            return ItooResult.build("1111", "失败");

        } else {
            log.info("借阅记录添加成功");
            return ItooResult.build("0000", "成功", flag);
        }
    }


    @GET
    @Path("/returnBorrowingRecord/{code}/{searchNum}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "还书（电脑端）", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = Void.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = Void.class)})
    public ItooResult returnBorrowingRecord(@ApiParam(value = "Inventory item to add") @PathParam("code") String code, @PathParam("searchNum") String searchNum, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException {

           boolean flag = false;
                flag = borrowingService.returnBorrwing(code,searchNum);
                if (flag == true){
                    return ItooResult.build("0000","还书成功",flag);
                }
              return ItooResult.build("1111","失败");

    }

    /**
     * 王华伟---还书手机端--2018年3月22日14:26:44--王雅静修改添加还书picture-2018年5月10日14:50:52
     * @param code
     * @param searchNum
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/returnBorrowingRecordAPP")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "还书（手机端）", response = BookViewModel.class, tags = "Admin")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item created", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class)
            ,
            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})
    public ItooResult returnBorrowingRecordAPP(@ApiParam(value = "note that needs to be added", required = true) BookViewModel form, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException {

        boolean flag = false;
        flag = borrowingService.returnBorrowingRecordAPP(form);
        if (flag == true){
            return ItooResult.build("0000","还书成功",flag);
        }
        return ItooResult.build("1111","失败");

    }

    @GET
    @Path("/addBorrowingRecord/{code}/{searchNum}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "借书（电脑端）", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = Void.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = Void.class)})
    public ItooResult addBorrowingRecord(@ApiParam(value = "Inventory item to add") @PathParam("code") String code, @PathParam("searchNum") String searchNum, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException {

        boolean flag = false;

        flag =  borrowingService.addBorrowingRecord(searchNum,code );
        if (flag == false) {
            log.error("借书失败");
            return ItooResult.build("1111", "失败");

        } else {
            log.info("借书成功");
            return ItooResult.build("0000", "成功", flag);
        }
    }


    /**
     * 删除借阅记录-王华伟-2017-11-5 22:14:17
     * @param borrowingIds
     * @return
     */
    @POST
    @Path("/deleteBorrowings/{borrowingIds}")
    @Produces({"application/json"})
    @ApiOperation(value = "删除借阅记录", notes = "", response = void.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "delete succeed", response = void.class)
            ,
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)
    })
    public ItooResult deleteBorrowings (@PathParam("borrowingIds") String[] borrowingIds){
         boolean flag=false;
                 
         borrowingIds = borrowingIds[0].split(",");
         for(int i=0;i<borrowingIds.length;i++)
         {
             flag = borrowingService.deleteBorrowingIds(borrowingIds[i]);
                     
         }
             if (flag == true) {
             log.info("借阅记录删除成功");
            return  ItooResult.build("0000", "删除成功",flag);
        } else {
             log.error("借阅记录删除失败");
             return  ItooResult.build("1111", "删除失败");
        }
        
    }

    /**
     * 编辑借记录（电脑端）--郭晶晶---2017-11-10 11:01:50
     * @param tBorrowing
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/editorBorrowing")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑借阅记录（电脑端）", notes = "", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,

            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult editorBorrowing(@ApiParam(value = "", required = true) TBorrowing tBorrowing,
                                            @Context SecurityContext securityContext) throws NotFoundException {

       boolean flag = borrowingService.editorBorrowing(tBorrowing);
        if (flag == true) {
            log.info("更新借阅记录成功");
            return ItooResult.build("0000", "更新成功", flag);
        } else {
            log.error("更新借阅记录失败");
            return ItooResult.build("1111", "更新失败");
        }
    }



    @POST
    @Path("/updateBorrowing/{borrowingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "续借（手机端）", notes = "", response = void.class, tags = "andorid")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,

            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateBorrowing(@ApiParam(value = "", required = true) @PathParam("borrowingId") String  borrowingId,
                      @Context SecurityContext securityContext) throws NotFoundException {

        TBorrowing  borrowingentity = borrowingService.updateBorrowing(borrowingId);

        if (borrowingentity != null) {
            log.info("续借成功");
            return ItooResult.build("0000", "续借成功", borrowingentity);
        } else {
            log.error("续借失败");
            return ItooResult.build("1111", "续借失败");
        }

    }
    /**
     * 王华伟--2017--10--14--按状态查询借阅记录（手机端）---王华伟修改--2018年3月23日14:16:38
     *
     * @param status
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBorrowingByStatus/{status}/{userId}/{pageSize}/{pageNum}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据借阅状态查找借阅记录(手机端)", notes = "", response = TBorrowingModel1.class, responseContainer = "List", tags = "User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok", response = TBorrowingModel1.class),
            @ApiResponse(code = 400, message = "not found", response = TBorrowingModel1.class)})
    public ItooResult getBorrowingByStatus(@ApiParam(value = "借阅的状态״̬", required = true) @PathParam("status") Integer status, @PathParam("userId") String userId,
                @PathParam("pageSize") int pageSize,@PathParam("pageNum") int pageNum,@Context SecurityContext securityContext)
            throws NotFoundException {
        List<Object> liststatus = borrowingService.getBorrowingByStatusAndCode(status, userId);
        PaginationEntity paginationEntity = new PaginationEntity(pageNum,pageSize,liststatus);
        if (liststatus == null || liststatus.size()==0) {
            log.error("根据借阅状态查找借阅记录失败");
            return ItooResult.build("1111", "查询失败");
        } else {
            log.error("根据借阅状态查找借阅成功");
            return ItooResult.build("0000", "查询成功", paginationEntity);
        }
    }


    /**
     * 王华伟--2017-10-14--根据用户ID查找借阅记录（手机端）
     *
     * @param userId
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBorrowingByUserId/{userId}")

    @Produces({"application/json"})
    @ApiOperation(value = "根据用户ID查找借阅记录", notes = "", response = TBorrowingModel1.class, responseContainer = "List", tags = "User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TBorrowingModel1.class)
            ,

            @ApiResponse(code = 400, message = "not found", response = TBorrowingModel1.class)})
    public ItooResult getBorrowingByUserId(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("userId") String userId,
                                           @Context SecurityContext securityContext)
            throws NotFoundException {

        List<TBorrowingModel1> list = borrowingService.findAllBorrowingByUserId(userId);
        if (list == null || list.isEmpty()) {
            log.error("根据userId查询返回失败");
            return ItooResult.build("1111", "查询返回失败");
        } else {
            log.info("根据userId查询返回成功");
            return ItooResult.build("0000", "查询返回成功", list);
        }
    }

    /**
     * 王华伟--2017--10--23根据条件查询借阅记录--张婷修改
     *
     * @param overDueStatus
     * @param reNewStatus
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBorrowingByConditon/{conditons}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据条件获取借阅分类", notes = "根据条件获取借阅分类", response = TBorrowingModel1.class, responseContainer = "List", tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get borrowingRecord Succeed", response = TBorrowingModel1.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = TBorrowingModel1.class)})
    public ItooResult getBorrowingByConditon(@ApiParam(value = "借阅记录", required = true)
                                             @PathParam("conditons") String condition,
                                             @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                             //@PathParam("viewModelBorrowing") Borrowing viewModelBorrowing,
                                             @Context SecurityContext securityContext)
            throws NotFoundException {

        log.debug("rest request to get BorrowingRecord:{}");

        List<BookViewModel> list = borrowingService.getBorrowingByCondition(condition);
        if (list != null || list.size()>=0) {
            log.info("根据条件查询成功");
            return ItooResult.build("0000", "查询返回成功！", list);
        } else {
            log.error("根据条件查询失败！");
            return ItooResult.build("1111", "根据条件查询失败！");
        }
    }

  /**
     * 导入借阅记录
     *
     * @param response
     * @param fileName
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @POST
    @Path("/importBorrowing")

    @Consumes({ "multipart/form-data" })
    @Produces({"application/json"})

    @ApiOperation(value = "导入文件", notes = "Adds items to the "
            + "system", response = void.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class), @ApiResponse(code = 409, message = "an existing item already exists", response = void.class)})
    public  ItooResult importReservation(@Context HttpServletResponse response, @Context HttpServletRequest request) throws NotFoundException, Exception {
        //定义返回的标识
        boolean flag = false;

            String filePath = UploadFileUtil.upload(response, request);
            Map<Serializable, Serializable> map = new HashMap<>();
            //sheet的名称
            map.put("sheetName", "Sheet1");
            //所操作的实体类
            map.put("Class", TBorrowingModel1.class);
            Map<Serializable, Serializable> dictDataMap = new HashMap<>();

            List<BookViewModel> bookList = bookService.getAllBookList();
            Map<Serializable, Serializable> bookMap = new HashMap<>();

            for(int i=0;i<bookList.size();i++){
                bookMap.put(bookList.get(i).getIsbn(),bookList.get(i).getBookName());
            }
            dictDataMap.put("isbn",(Serializable)bookMap);

                List<TBorrowingModel1> tBorrowingList = ExcelUtil.importExcel(filePath, response, map, dictDataMap
                );
                if (tBorrowingList.size() ==0) {
                    log.error("导入失败");
              return   ItooResult.build("1111","请检查Excel中的学号是否存在",flag);
            }else{
                borrowingService.insertBorrowingList(tBorrowingList);
                log.info("导入成功");
               return  ItooResult.build("1111","导入成功",flag);
            }

    }

    /**
     * 导出
     *
     * @param response
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
        @GET
        @Path("/exportBorrowing")
        @Produces({"application/json"})
        @ApiOperation(value = "导出", notes = "导出记录", response = void.class, tags = "PC")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "导出成功", response = void.class),
                @ApiResponse(code = 400, message = "导出失败", response = void.class)})

        public ItooResult ExportBorrowing(@Context HttpServletResponse response) throws NotFoundException, Exception {

        Map<Serializable, Serializable> map = new HashMap<>();
        //sheet的名字
        map.put("sheetName", "借阅信息");
        //要导出的字段
        map.put("columns", new String[]{"studentName",
                 "bookName", "isbn",
                "borrowTime", "realDate", "renew", "isOverdue","location"});
        //导出的表格标题
        map.put("title", "借阅信息");
        //getAll()找到导出的数据
        map.put("dataList", (Serializable) borrowingService.getAllBorrowing());
        try {
            ExcelUtil.exportExcel(map, response);
            return ItooResult.build("0000", "导出成功", true);
        } catch (Exception e) {
            log.error("借阅管理导出失败", e);
            return ItooResult.build("1111", "导出失败", false);
        }

    }

//    getStudentBystudentCode
    @GET
    @Path("/getStudentBystudentCode/{code}")
    @Produces({"application/json"})
    @ApiOperation(value = "学生信息及借阅数", notes = "查询记录", response = void.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功", response = void.class),
            @ApiResponse(code = 400, message = "查询失败", response = void.class)})
    public ItooResult getStudentBystudentCode(@ApiParam(value = "URL中需要传递的参数", required = true) @PathParam("code") String code,@Context HttpServletResponse response){
        try {
            Student student= borrowingService.findStudentAndBorrowCount(code);
            if (student == null) {
                return ItooResult.build("1111","学生记录不存在",student);
            }else{
                return ItooResult.build("0000","学生记录查询成功",student);
            }
        }catch (Exception e){
            return ItooResult.build("1111","学生记录不存在");
        }
    }


    /**
     * 手机端（添加借阅记录）--2018年3月20日--王华伟
     * @param code
     * @param searchNum
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @GET
    @Path("/addAppBorrowingRecord/{location}/{code}/{isbn}/{ID}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "借书（手机端）", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = void.class)})
    public ItooResult addAppBorrowingRecord(@ApiParam(value = "Inventory item to add") @PathParam("code") String code, @PathParam("isbn") String isbn,@PathParam("location") String location,@PathParam("ID") String ID)
            throws NotFoundException, URISyntaxException {

        boolean flag = false;

        flag =  borrowingService.addAppBorrowingRecord(isbn,code ,location,ID);
        if (flag == false) {
            log.error("借书失败");
            return ItooResult.build("1111", "失败");

        } else {
            log.info("借书成功");
            return ItooResult.build("0000", "成功", flag);
        }
    }

    /**
     * 还书提醒（手机端）---王华伟--2018年4月4日14:52:10
     * @param isbn
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @GET
    @Path("/ReturnBookRemind/{userId}/{pageSize}/{pageNum}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "还书提醒（手机端）", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = void.class)})
    public ItooResult ReturnBookemind(@ApiParam(value = "Inventory item to add") @PathParam("userId") String userId, @PathParam("pageSize") int pageSize,@PathParam("pageNum") int pageNum,@Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> liststatus = borrowingService.ReturnBookRemind(userId);
        PaginationEntity paginationEntity = new PaginationEntity(pageNum,pageSize,liststatus);
        if (liststatus == null || liststatus.size()==0) {
            log.error("根据借阅状态查找借阅记录失败");
            return ItooResult.build("1111", "查询失败");
        } else {
            log.error("根据借阅状态查找借阅成功");
            return ItooResult.build("0000", "查询成功", paginationEntity);
        }

    }
    /**
     * 根据条件进行模糊查询+王雅静+2018年4月25日19:16:43
     *
     * @param MoHu_str
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findBorrowedRecordByConditions/{MoHu_str}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据输入的作品名称进行模糊查询", notes = "根据输入的作品名称进行模糊查询", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findBorrowedRecordByConditions(@ApiParam(value = "输入的作者，摘要，作品名称", required = true) @PathParam("MoHu_str") String MoHu_str, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                       @Context SecurityContext securityContext)
            throws NotFoundException {
        PaginationEntity paginationEntity = borrowingService.findBorrowedRecordByConditions(MoHu_str, pageNum, pageSize);
        if (paginationEntity != null) {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", paginationEntity);

        } else {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }


    /**
     * 扫一扫--王华伟--2018年5月17日14:59:17
     * @param userId
     * @param pageSize
     * @param pageNum
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/Scan/{urlCurrentPage}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "手机端扫一扫", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item created", response = void.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = void.class)})
    public ItooResult Scan(@ApiParam(value = "note that needs to be added", required = true) @PathParam("urlCurrentPage") String urlCurrentPage, @Context SecurityContext securityContext)
            throws NotFoundException {

        String  appId = "wxf7be5ce2c5dc091d";
        String appSecret = "acc1c06522aeb100a4e9ca03742f347f";
        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        //根据aapId和appSecret获得access_token(使用凭证)
        String res = HttpRequestUtil.sendGetRequest(url);
        com.alibaba.fastjson.JSONObject userInfo = com.alibaba.fastjson.JSON.parseObject(res);
//        JSONObject demoJson =  HttpRequestUtil.doGet(url);
        String access_token = userInfo.getString("access_token");
        System.out.println("access_token====" + access_token);

        //根据access_token获得jspapi_ticket
        String jsapiUrl="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
//        JSONObject demoJson1 =  HttpRequestUtil.doGet(jsapiUrl);
        String res1 =  HttpRequestUtil.sendGetRequest(jsapiUrl);
        com.alibaba.fastjson.JSONObject userInfo1 = com.alibaba.fastjson.JSON.parseObject(res1);
        System.out.println(userInfo1.toString());
        String jsapi_ticket=userInfo1.getString("ticket");

        Map<String, String> map= sign.sign(jsapi_ticket,urlCurrentPage);
        System.out.println("map====" + map);
        if (map == null|| map.size()==0) {
            log.error("根据借阅状态查找借阅记录失败");
            return ItooResult.build("1111", "查询失败");
        } else {
            log.error("根据借阅状态查找借阅成功");
            return ItooResult.build("0000", "查询成功", map);
        }

    }

}


