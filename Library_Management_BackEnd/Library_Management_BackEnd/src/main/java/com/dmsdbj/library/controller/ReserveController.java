
package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.controller.util.UploadFileUtil;
import com.dmsdbj.library.entity.TReservation;
import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.repository.TReservationRepository;
import com.dmsdbj.library.service.ReserveService;
import com.dmsdbj.library.service.StudentService;
import com.dmsdbj.library.viewmodel.BookType;
import com.dmsdbj.library.viewmodel.Borrowing;
import com.dmsdbj.library.viewmodel.Reservation;
import com.dmsdbj.library.viewmodel.Reserve;
import com.wordnik.swagger.annotations.*;
import org.jplus.hyberbin.excel.utils.ExcelUtil;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约管理的controller 包括手机端
 * Created by WGP on 2017/11/2.
 */

@Path("/reserve")
@Api(value = "/reserve", description = "the reserve API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class ReserveController {
    private final String NOSUCCESS = "1111";
    private final String SUCCESS = "0000";
    @Inject
    private Logger log;

    @Inject
    private ReserveService reserveService;

    @Inject
    private StudentService studentService;

    //region 批量删除预约记录 -武刚鹏-2017年10月28日15:30:46
    /**
     * 批量删除预约记录
     *
     * @param reservationIds 预约记录ID 数组
     * @return
     */
    @POST
    @Path("/deleteReservationes/{reservationIds}")

    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "Deletes a reserve", notes = "", response = Boolean.class, tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = Boolean.class)
            ,

            @ApiResponse(code = 404, message = " not found", response = Boolean.class)})
    public ItooResult deleteReservationes( @PathParam("reservationIds") String reservationIds) {

        Boolean flag = reserveService.deleteReservationes(reservationIds);
        if (flag == true) {
            return ItooResult.build(SUCCESS, "删除预约成功", flag);

        } else {
            return ItooResult.build(NOSUCCESS, "删除预约失败");
        }
    }
    //endregion
    //region 取消预约（手机端） -王雅静-2017年11月23日10:19:21
    /**
     * 取消预约(手机端)
     *
     * @param reservationId 预约记录ID 数组
     * @param userId
     * @return
     */
    @POST
    @Path("/cancelReservationes/{reservationId}/{userId}")

    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "取消预约，提供预约号和用户ID", notes = "", response = Boolean.class, tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = Boolean.class)
            ,

            @ApiResponse(code = 404, message = " not found", response = Boolean.class)})
    public ItooResult cancelReservationes( @PathParam("reservationId") String reservationId,@PathParam("userId") String userId) {

        Boolean flag = reserveService.cancelReservationes(reservationId,userId);
        if (flag == true) {
            return ItooResult.build(SUCCESS, "取消预约成功", flag);

        } else {
            return ItooResult.build(NOSUCCESS, "取消预约失败");
        }
    }
    //endregion


    //region 根据isbn和location查询预约记录-杜雨-2018年5月9日15:46:26

    /**
     * 根据isbn和location查询预约记录
     * @param isbn
     * @param location
     * @return
     */
    @GET
    @Path("/getBookReservationInfo/{isbn}/{location}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "根据isbn和location查询预约记录", notes = "Multiple status values can be provided with comma separated strings", response = Borrowing.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据isbn和location查询出来所有预约记录", response = Reservation.class),
            @ApiResponse(code = 400, message = "bad input parameter", response = Reservation.class)})

    public ItooResult getBookReservationInfo(@ApiParam(value = "需要传递的isbn",
            required = true) @PathParam("isbn") String isbn, @ApiParam(value = "需要传递的location",
            required = true) @PathParam("location") String location){
        List<Reservation> list = reserveService.bookReservationInfo(isbn,location);
        if (list != null) {
            return ItooResult.build(SUCCESS, "根据条件查询预约记录成功", list);
        } else {
            return ItooResult.build(NOSUCCESS, "根据条件查询预约记录失败");
        }

    }
    //endregion



    //region 模糊查询根据条件分页查询预约记录-武刚鹏-2017年10月27日16:54:43

    /**
     * 模糊查询根据条件分页查询预约记录
     *
     * @param condition
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GET
    @Path("/getReservationByConditions/{condition}/{pageNum}/{pageSize}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "模糊条件分页查询预约记录", notes = "Multiple status values can be provided with comma separated strings", response = Borrowing.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = PaginationEntity.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = PaginationEntity.class)})

    public ItooResult getReservationByCondition(@ApiParam(value = "URL中需要传递的参数",
            required = true) @PathParam("condition") String condition, @DefaultValue
                                                        ("1") @PathParam("pageNum") int pageNum, @DefaultValue("10")
                                                @PathParam("pageSize") int pageSize) {
        PaginationEntity paginationEntity = reserveService.findReservationByPagenationCondition(condition, pageSize, pageNum);
        if (paginationEntity != null) {
            return ItooResult.build(SUCCESS, "根据条件查询预约记录成功", paginationEntity);
        } else {
            return ItooResult.build(NOSUCCESS, "根据条件查询预约记录失败");
        }

    }
    //endregion

    //region根据条件模糊查询分页查询预约记录,如果没有条件，即为 字符串 "null"值-武刚鹏-2017年10月27日16:54:43

    /**
     * 根据条件模糊查询分页查询预约记录,如果没有条件，即为 字符串 "null"值
     *
     * @param condition
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GET
    @Path("/getReservationNoConditions/{pageNum}/{pageSize}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "查询分页查询预约记录", notes = "Multiple status values can be provided with comma separated strings", response = Borrowing.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = PaginationEntity.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = PaginationEntity.class)})
    public ItooResult getReservationNoCondition(@ApiParam(value = "模糊条件，页号，页大小",
            required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10")
                                                @PathParam("pageSize") int pageSize) {
        PaginationEntity paginationEntity = reserveService.findReservationByPagenationCondition(null, pageSize, pageNum);
        if (paginationEntity != null) {
            return ItooResult.build(SUCCESS, "根据条件查询预约记录成功", paginationEntity);
        } else {
            return ItooResult.build(NOSUCCESS, "根据条件查询预约记录失败");
        }
    }
    //endregion

    //region  根据用户ID查询预约记录 -武刚鹏-2017年10月27日16:55:11

    /**
     * 根据用户ID查询预约记录
     *
     * @param userId
     * @return
     */
    @GET
    @Path("/getReservationByuserId/{userId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "根据用户ID查询预约记录", notes = "Multiple status values can be provided with comma separated strings", response = Borrowing.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询出来可借和不可借的预约记录", response = Reservation.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = Reservation.class)})

    public ItooResult getReservationByuserId(@ApiParam(value = "需要传递的用户ID",
            required = true) @PathParam("userId") String userId) {
        reserveService.findReserveByUserId(userId);
        List<Reservation> list = reserveService.findReserveByUserId(userId);
        if (list == null || list.isEmpty()) {
            log.error("根据用户ID查询预约记录失败");
            return ItooResult.build(NOSUCCESS, "根据用户ID查询预约记录失败");
        } else {
            return ItooResult.build(SUCCESS, "根据用户ID查询预约记录成功", list);
        }
    }
    //endregion

    //region 根据userId查询可以借书的预约记录个数
    @GET
    @Path("/getReservationCount/{userId}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @ApiOperation(value = "根据userId查询可以借书的预约记录个数", notes = "By passing in the appropriate options, you can search for available inventory in the system ", response = Reserve.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回预约记录的个数值", response = Integer.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = Integer.class)})

    public ItooResult getReservationCount(@ApiParam(value = "需要传递的用户ID",
            required = true) @PathParam("userId") String userId) {
        int count = reserveService.getBorrowReservationCount(userId);
        return ItooResult.build(SUCCESS, "根据userId查询可以借书的预约记录个数", count);

    }
    //endregion

    /**
     * 导入预约记录
     *
     * @param form
     * @return
     */
    @POST
    @Consumes({"multipart/form-data"})
    @Produces({ "application/json" })
    @Path("/importReservation")
    @ApiOperation(value = "导入预约记录", notes = "Multiple status values can be provided with comma separated strings", response = Borrowing.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Boolean.class)
            ,
            @ApiResponse(code = 400, message = "bad input parameter", response = Boolean.class)})
    public ItooResult importReservation(@Context HttpServletResponse response, @Context HttpServletRequest request) {
        //throws NotFoundException, Exception
        //定义返回的标识
        boolean flag = false;
        String message = "";
        try {
            String filePath = UploadFileUtil.upload(response, request);
            Map<Serializable, Serializable> map = new HashMap<>();
            //sheet的名称
            map.put("sheetName", "Sheet1");
            //所操作的实体类
            map.put("Class", Reservation.class);
            Map<Serializable, Serializable> dictDataMap = new HashMap<>();
            List<TStudent> studentList = studentService.getAllStudent();
            Map<Serializable, Serializable> studentMap = new HashMap<>();
            for (int i = 0; i < studentList.size(); i++) {
                studentMap.put(studentList.get(i).getId(), studentList.get(i).getCode());
            }
            dictDataMap.put("viewUserId", (Serializable) studentMap);

            List<Reservation> teservationList = ExcelUtil.importExcel(filePath, response, map, dictDataMap);
            if (teservationList == null) {
                message = "请检查Excel中的学号是否存在";
                return ItooResult.build(NOSUCCESS,message,flag);
            }else{
                reserveService.insertReservationList(teservationList);
                message="导入成功！";
                flag=true;
                return ItooResult.build(SUCCESS,message,flag);
            }
        } catch (Exception e) {
            flag = false;
            message ="导入失败";
            log.error(message, e);
            return ItooResult.build(NOSUCCESS,message,flag);
        }
    }

    @GET
    @Path("/downExcel")
    @ApiOperation(value = "下载模板", response = BookType.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = BookType.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookType.class)})
    public ItooResult downExcelGet(@Context HttpServletRequest requst, @Context HttpServletResponse response) {
        Map<Serializable, Serializable> map = new HashMap<>();
        // sheet的名字
        map.put("sheetName", "Sheet1");
        //需要导出的字段
        map.put("columns", new String[]{"studentNo", "studentName", "bookName", "viewIsbn","location"});
        //导出的表格标题
        map.put("title", "预约记录统计");
        //getlist()为你要导出的数据，可替换为自己的list
        List<TReservation> reservationsList = new ArrayList<>();
        Reservation reservation = new Reservation();
        String studentNo = "13050241058";
        String studentName = "武刚鹏";
        String bookName = "数据结构书";
        String viewIsbn = "aaaaa";
        String location="万达";
        reservation.setStudentNo(studentNo);
        reservation.setStudentName(studentName);
        reservation.setBookName(bookName);
        reservation.setViewIsbn(viewIsbn);
        reservation.setLocation(location);
        reservationsList.add(reservation);
        map.put("dataList", (Serializable) reservationsList);
        try {
            ExcelUtil.exportExcel(map, response);
            return ItooResult.build(SUCCESS, "下载成功");
        } catch (Exception e) {
            return ItooResult.build(NOSUCCESS, "下载失败");
        }

    }

    /**
     * 导出预约记录
     *
     * @return
     */
    @GET
    @Path("/ExportReservationGet/{condition}")
    @ApiOperation(value = "导出预约记录", notes = "Multiple status values can be provided with comma separated strings", response = Borrowing.class, responseContainer = "List", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Boolean.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = Boolean.class)})
    public ItooResult exportReservationGet(@Context HttpServletRequest requst, @Context HttpServletResponse response,@PathParam("condition") String condition) {

        Map<Serializable, Serializable> map = new HashMap<>();
        // sheet的名字
        map.put("sheetName", "Sheet1");
        //需要导出的字段
        map.put("columns", new String[]{"studentNo", "studentName", "professionName", "classesName", "bookName", "viewIsbn", "videCreateTime","location"});
        //导出的表格标题
        map.put("title", "预约记录统计");
        condition = condition.trim();

        if (condition.isEmpty()){
            //map.put("dataList", (Serializable) reserveService.findReservationByCondition(condition));
            map.put("dataList", (Serializable) reserveService.getAllReservation());
        }else{
            //getlist()为你要导出的数据，可替换为自己的list
            //map.put("dataList", (Serializable) reserveService.getAllReservation());
            map.put("dataList", (Serializable) reserveService.findReservationByCondition(condition));
        }
        try {
            ExcelUtil.exportExcel(map, response);
            return ItooResult.build(SUCCESS, "导出成功", true);
        } catch (Exception e) {
            log.error("预约管理导出失败", e);
            return ItooResult.build(NOSUCCESS, "导出失败", false);
        }

    }

    //region 更新预约记录 -武刚鹏-2017年10月30日09:59:03

    /**
     * 更新预约记录
     *
     * @param body
     * @return
     */
    @POST
    @Path("/updateReservation")
    @Produces({"application/xml", "application/json"})
    @ApiOperation(value = "更新预约记录，只能修改isbn", notes = "", response = void.class, tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,
            @ApiResponse(code = 404, message = " not found", response = void.class)})
    public ItooResult updateReservation(@ApiParam(value = "预约记录实体（只能修改isbn" +
            " to the store", required = true) Reservation resvervation) {

        boolean flage = reserveService.updateReservation(resvervation);
        if (flage) {
            return ItooResult.build(SUCCESS, "更新预约记录成功", flage);
        } else {
            return ItooResult.build(NOSUCCESS, "更新预约记录失败", flage);
        }
    }
    //endregion

    //region 手机端预约查询是否重复预约同一本书-杜雨-2018年5月8日12:08:00
    /** 手机端预约查询是否重复预约同同一本书--杜雨--2018年5月8日
     * @param userID
     * @param isbn
     * @param location
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
     @GET
     @Path( "/isReservedBook/{userID}/{isbn}/{location}")
     @Consumes({"application/json"})
     @Produces({"application/json"})
     @ApiOperation(value="手机端查询是否重复借阅同一本书，UserID/isbn/location",notes="",response=void.class,tags="reservation")
     @ApiResponses(value ={@ApiResponse(code = 405,message="Invvalid input",response=void.class)})

     public ItooResult isReservaedBook(@PathParam("userID") String userID,@PathParam("isbn") String isbn,@PathParam("location") String location) throws NotFoundException,URISyntaxException{
        Boolean flag=reserveService.isReservedBookResult(userID,isbn,location);
        if(flag) {
            return ItooResult.build(NOSUCCESS, "该书已预约", flag);
        }else{
            return ItooResult.build(SUCCESS,"该书未预约",flag);
        }
     }
   //endregion

    //region PC端添加预约记录-武刚鹏-2017年10月28日09:14:32

    /**
     * PC端添加预约记录 -武刚鹏-2017年10月27日16:34:49
     * 修改：service方法增加检查条件 郑晓东 2017年11月9日17点41分
     * @param reservation
     * @return
     */
    @POST
    @Path("/addreservetionByPC")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "PC端添加预约记录学号和isbn ", notes = "", response = void
            .class, tags = "reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
    public ItooResult addreservetionByPC(@ApiParam(value = "note that needs to be added" +
            " to the store", required = true) Reservation reservation)
            throws NotFoundException, URISyntaxException {
        boolean flag = reserveService.addReserveByPC(reservation);
        if (flag) {
            return ItooResult.build(SUCCESS, "添加预约成功", flag);
        } else {
            return ItooResult.build(NOSUCCESS, "添加预约失败", flag);
        }
    }
    //endregion


    //region 手机端添加预约记录 -武刚鹏-2017年10月30日09:58:31

    /**
     * 手机端添加预约记录 -武刚鹏-2017年10月30日09:58:30
     * 修改：修改Post传参形式和方法名-杜雨-2018年5月2日11:56:08
     * @param tReservation
     * @return
     * @throws NotFoundExceptio
     * @throws URISyntaxException
     */
    @POST
    @Path("/addreservetionByApp")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "手机端添加预约记录，userID和isbn", notes = "", response = void
            .class, tags = "reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
//    public ItooResult AddreserveionByApp(@PathParam("userID") String userID,@PathParam("isbn") String isbn ,@PathParam("location") String location) throws NotFoundException, URISyntaxException{
     public ItooResult AddreserveionByApp(@ApiParam(value="userID和isbn和location"+"to the store",required = true) TReservation tReservation)
            throws NotFoundException, URISyntaxException {

        boolean flag = reserveService.addReserveByApp(tReservation);
        if (flag) {
            return ItooResult.build(SUCCESS, "添加预约记录成功", flag);

        } else {
            return ItooResult.build(NOSUCCESS, "添加预约记录失败", flag);

        }

    }
    //endregion

    //region 手机端添加预约记录 -武刚鹏-2017年10月30日09:58:31
//
//    /**
//     * 手机端添加预约记录 -武刚鹏-2017年10月27日15:14:59
//     *
//     * @param tReservation
//     * @return
//     * @throws NotFoundExceptio
//     * @throws URISyntaxException
//     */
//    @POST
//    @Path("/addreservetionByMobile")
//    @Consumes({"application/json"})
//    @Produces({"application/json"})
//    @ApiOperation(value = "手机端添加预约记录，userID和isbn", notes = "", response = void
//            .class, tags = "reservation")
//    @ApiResponses(value = {
//            @ApiResponse(code = 405, message = "Invalid input", response = void.class)})
//
//    public ItooResult addreservetionByMobile(@ApiParam(value = "userID和isbn" +
//            " to the store", required = true) TReservation tReservation)
//            throws NotFoundException, URISyntaxException {
//        boolean flag = reserveService.addReserveByMobile(tReservation);
//        if (flag) {
//            return ItooResult.build(SUCCESS, "图书信息添加成功", flag);
//
//        } else {
//            return ItooResult.build(NOSUCCESS, "图书添加失败", flag);
//
//        }
//
//    }
   //endregion


    @POST
    @Consumes({"multipart/form-data"})
    @Produces({ "application/json" })
    @Path("/testServiceFunction")
    @ApiOperation(value = "测试方法：", notes = "Multiple status values can be provided with comma separated strings", tags = "reserve")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Boolean.class),
            @ApiResponse(code = 400, message = "bad input parameter", response = Boolean.class)})
    public ItooResult testServiceFunction(@Context HttpServletRequest requst, @Context HttpServletResponse response) throws Exception {
//        reserveService.testRepositoryFunction();
//        response.setCharacterEncoding("utf-8");
//        String NO_BOOK="以下ISBN对应图书不存在";
//        String NO_STUDENT="以下学号对应学生不存在";
//        String UNALLOW="以下学号对应预约次数已超出";
//        List<ImportErrorModel> importErrorModelList=new ArrayList<>();//错误集
//        ImportErrorModel noStudentError = new ImportErrorModel(NO_STUDENT);//不存在学生的集合
//        ImportErrorModel noBookError = new ImportErrorModel(NO_BOOK);//不存在图书的集合
//        ImportErrorModel unAllowError=new ImportErrorModel(UNALLOW);//不允许多次预约的集合
//
//        Map<Serializable, Serializable> map = new HashMap<>();
//        // sheet的名字
//        map.put("sheetName", "Sheet1");
//        //需要导出的字段
//        map.put("columns", new String[]{"userId", "viewUserId"});
//        //导出的表格标题
//        map.put("title", "预约记录统计");
//
//
//        List<Reservation> noStudentList=new ArrayList<>();
////        List<Reservation> noBookList=new ArrayList<>();
////        List<Reservation> unAllowList=new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Reservation reservation = new Reservation();
//            reservation.setUserId(""+i);
//            reservation.setViewUserId(""+i);
//            noStudentList.add(reservation);
//        }
//
//        //getlist()为你要导出的数据，可替换为自己的list
//        map.put("dataList", (Serializable)noStudentList);
//        ExcelUtil.exportExcel(map, response);
        return ItooResult.build(SUCCESS,"测试成功！",true);
    }

    public ItooResult uploadFile(){
        return null;
    }
}