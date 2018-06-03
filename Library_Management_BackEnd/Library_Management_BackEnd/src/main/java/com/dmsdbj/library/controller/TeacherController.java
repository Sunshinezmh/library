package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.controller.util.UploadFileUtil;
import com.dmsdbj.library.entity.TTeacher;
import com.dmsdbj.library.service.TeacherService;
import com.dmsdbj.library.viewmodel.Teacher;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/teacher")


@Api(value = "/teacher",description = "the teacher API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public class TeacherController {

    @Inject
    private Logger log;

    @Inject
    private TeacherService teacherService;

    /**
     * 添加一条教师记录--刘雅雯--2017年11月1日06:14:16
     * @param form
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/addTeacher")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "添加教师信息", notes = "Adds an item to the system", response = void.class, tags="PC")
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "item created", response = void.class),
        
        @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),
        
        @ApiResponse(code = 409, message = "an existing item already exists", response = void.class) })

    public ItooResult addTeacher(@ApiParam(value = "Inventory item to add" )  TTeacher tTeacher
            ,@Context SecurityContext securityContext)
            throws NotFoundException, java.net.URISyntaxException {


        //判断该工号是否存在
        List<TTeacher> listTeacher =teacherService.findTeacherByCodeList(tTeacher.getCode());
        if (listTeacher!=null && listTeacher.size()!=0){
            log.error("添加失败");
            return ItooResult.build("1111","该工号已存在，添加失败");

        }else{
            //没有该员工号时可以添加
            boolean statueBool= teacherService.addTeacher(tTeacher);
            if (statueBool==true){
                log.info("添加成功");
                return ItooResult.build("0000","添加成功",statueBool);
            }else{
                log.error("添加失败");
                return ItooResult.build("1111","添加失败");
            }
        }


    }



    /**
     * 批量删除teacher--刘雅雯-2017年11月1日02:34:28
     * @param teacherIds
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/deleteTeachers/{teacherIds}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "批量删除", notes = "", response = void.class, tags="PC")
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
        
        @ApiResponse(code = 404, message = "not found", response = void.class) })

    public ItooResult deleteTeachers(@ApiParam(value = "teacher id to delete",required=true) @PathParam("teacherIds") String teacherIds)
    throws NotFoundException {
        String[] teacherIdArr;
        boolean bool;
        teacherIdArr=teacherIds.split(",");
        for (int i=0;i<teacherIdArr.length;i++){
            bool = teacherService.deleteTeacher(teacherIdArr[i]);
        }
        if (bool = true){
            log.info("删除成功");
            return ItooResult.build("0000","删除成功",bool);
        }else{
            log.error("删除失败");
            return ItooResult.build("1111","删除失败");
        }


//        return HeaderUtil.createAlertArr(Response.ok(), "Teachers.deleted",teacherIdArr).build();
    }

    /**
     * 获取全部教师信息--刘雅雯--2017年10月31日17:46:52
     * @param pageSize
     * @param pageNum
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getAllTeacher/{pageSize}/{pageNum}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "获取全部教师信息", notes = "获取全部教师信息 ", response = Teacher.class, responseContainer = "List", tags= "PC")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "获得查询结果", response = Teacher.class),
        
        @ApiResponse(code = 400, message = "Not Found", response = Teacher.class) })

    public ItooResult getAllTeacher(@ApiParam(value = "页面显示条数",required=true) @PathParam("pageSize") Integer pageSize
,@ApiParam(value = "第几页",required=true) @PathParam("pageNum") Integer pageNum)
    throws NotFoundException {
         List<TTeacher> listTeacher= teacherService.findAllTeacher();
         if (listTeacher != null && listTeacher.size()>0){
             log.info("获取全部教师信息");
             return ItooResult.build("0000","获取全部教师信息成功",listTeacher);
         }else {
             log.error("获取全部教师信息失败");
             return ItooResult.build("1111", "获取全部教师信息失败");
         }
//        return Optional.ofNullable(listTeacher)
//                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum, pageSize, listTeacher)).build())
//                .orElse(Response.status(Response.Status.NO_CONTENT).build());
    }

    /**
     *模糊查询获取教师记录（模糊条件：教工号/姓名/职务/职称/权限）--刘雅雯--2017年10月31日19:27:05
     * @param conditions
     * @param pageSize
     * @param pageNum
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getTeacherByCondition/{conditions}/{pageSize}/{pageNum}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "模糊查询获取教师信息", notes = "根据教工号和姓名获得用户的借阅记录 ", response = Teacher.class, responseContainer = "List", tags= "PC")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "获得查询结果", response = Teacher.class),
        
        @ApiResponse(code = 400, message = "Not Found", response = Teacher.class) })

    public ItooResult getTeacherByCondition(@ApiParam(value = "查询条件可以为空",required=true) @PathParam("conditions") String conditions
,@ApiParam(value = "页面显示条数",required=true) @PathParam("pageSize") Integer pageSize
,@ApiParam(value = "第几页",required=true) @PathParam("pageNum") Integer pageNum)
    throws NotFoundException {
        List<TTeacher> listTeacher = teacherService.findTeacherByConditon(conditions);
        if (listTeacher != null && listTeacher.size()>0){
            log.info("获取全部教师信息成功");
            return ItooResult.build("0000","获取全部教师信息成功",listTeacher);
        }else {
            log.error("获取全部教师信息失败");
            return ItooResult.build("1111", "获取全部教师信息失败");
        }
//        return Optional.ofNullable(listTeacher)
//                .map(result -> Response.status(Response.Status.OK).entity(new PaginationEntity(pageNum, pageSize, listTeacher)).build())
//                .orElse(Response.status(Response.Status.NO_CONTENT).build());
    }

    @GET
    @Path("/downExcel")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "下载模板", notes = "下载模板  ", response = void.class, tags= "PC")
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "下载成功", response = void.class),
        
        @ApiResponse(code = 400, message = "下载失败", response = void.class) })

    public ItooResult downExcelGet(@Context HttpServletRequest  requst,@Context HttpServletResponse  response) throws NotFoundException {
        Map<Serializable,Serializable> map =new HashMap<>();
        //sheet的名字
        map.put("sheetName","sheet1");
        //需要导出的字段
        map.put("columns",new String[]{"teacherCode","teachername","sex","duties","job"});
        //导出的表格标题
        map.put("title","教师信息");


        List<Teacher> teacherList = new ArrayList<>();
        Teacher tTeacher = new Teacher();
        tTeacher.setTeacherCode("130015");
        tTeacher.setTeachername("刘雅雯15");
        tTeacher.setSex("女");
        tTeacher.setJob("主任");
        tTeacher.setDuties("教授");

        teacherList.add(tTeacher);
        map.put("dataList",(Serializable)teacherList);


        try {
            ExcelUtil.exportExcel(map,response);
            log.info("下载成功");
            return  ItooResult.build("0000", "下载成功");
                   
        } catch (Exception e) {
            log.error("下载失败");
            return ItooResult.build("1111", "下载失败");
        }
    }



    @GET
    @Path("/exportTeacher/{conditions}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "导出", notes = "下载模板  ", response = void.class, tags= "PC" )
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "导出成功", response = void.class),
        
        @ApiResponse(code = 400, message = "导出失败", response = void.class) })

    public ItooResult exportTeacher(@Context HttpServletResponse response,@PathParam("conditions") String conditions) throws NotFoundException ,Exception {
        Map<Serializable,Serializable> map=new HashMap<>();
        conditions = conditions.trim();
        if (conditions.isEmpty()){
            System.out.println(conditions);
            //List<TTeacher> tTeacherList = teacherService.findTeacherByConditon(conditions);
            List<TTeacher> teacherList =  teacherService.findAllTeacher();
            map.put("sheetName","sheet1");
            //要导出的字段
            map.put("columns",new String[]{"code","name",
                    "sex","jobTitle_name","duty_name"});
            map.put("title","教师管理");
            map.put("dataList",(Serializable)teacherList);
        }else{
            //获取要导出的全部数据
            //List<TTeacher> teacherList =  teacherService.findAllTeacher();
            List<TTeacher> tTeacherList = teacherService.findTeacherByConditon(conditions);
            map.put("sheetName","sheet1");
            //要导出的字段
            map.put("columns",new String[]{"code","name",
                    "sex","jobTitle_name","duty_name"});
            map.put("title","教师管理");
            map.put("dataList",(Serializable)tTeacherList);
        }
        try {
            ExcelUtil.exportExcel(map,response);
            log.info("导出成功");
          return  ItooResult.build("0000","导出成功");
        } catch (Exception e) {
            log.error("导出失败");
            return ItooResult.build("1111", "导出失败");
        }
        
    }


    /**
     * 编辑教师信息--刘雅雯--2017年11月1日06:06:48
     * @param body
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/updateTeacher")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "编辑教师信息", notes = "", response = void.class, tags= "PC" )
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
        
        @ApiResponse(code = 404, message = "not found", response = void.class),
        
        @ApiResponse(code = 405, message = "Validation exception", response = void.class) })

    public ItooResult updateTeacher(@ApiParam(value = "提交更改的信息" ,required=true) TTeacher tTeacher
            ,@Context SecurityContext securityContext)
            throws NotFoundException  {

           boolean bool= teacherService.updateTeacher(tTeacher);
            if (bool==true){
                log.info("编辑信息成功");
                return ItooResult.build("0000","编辑信息成功",bool);
            }else{
                log.error("编辑信息失败");
                return ItooResult.build("1111","编辑信息失败");
            }
    }

    //导入--刘雅雯--2017年11月1日20:09:26
    @POST
    @Path("/importTeacher")

    @Consumes({"multipart/form-data"})
    @Produces({ "application/json" })

    @ApiOperation(value = "通过excel导入借阅信息", notes = "Adds items to the " +
            "system", response = void.class, tags= "PC" )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = void.class),

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),

            @ApiResponse(code = 409, message = "an existing item already exists", response = void.class) })
             public ItooResult importTeacher(@Context  HttpServletResponse response, @Context HttpServletRequest request
                                    )

            throws NotFoundException, Exception {

            String filePath = UploadFileUtil.upload(response, request);
            Map<Serializable,Serializable> map=new HashMap<>();
            map.put("sheetName","sheet1");
            map.put("Class",Teacher.class);
            boolean bool= false;

            try{
                    //获取excel中的数据
                    List<Teacher> importList = ExcelUtil.importExcel(filePath,response,map);
                    Teacher teacher =new Teacher();
                    TTeacher teacherEntity=new TTeacher();
                    for (int n=0 ;n<importList.size();n++){

                        //集合，list
                        teacher=importList.get(n);
                        //TODO
                        teacherEntity.setId(teacher.getId());
                        teacherEntity.setCode(teacher.getTeacherCode());
                        teacherEntity.setName(teacher.getTeachername());
                        teacherEntity.setSex(teacher.getSex());
                        teacherEntity.setJobTitlename(teacher.getJob());
                        teacherEntity.setdutyname(teacher.getDuties());

                        //判断要添加的是否已经存在,存在跳出来
                        List<TTeacher> tTeachersList= teacherService.findTeacherByCodeList(teacherEntity.getCode());
                        if (tTeachersList!=null && tTeachersList.size()!=0){

                            continue;
                        }

                        teacherService.addTeacher(teacherEntity);
                        bool=true;

                    }
                }catch(Exception e){
                    bool=false;
                    log.error("导入信息失败");
                    return ItooResult.build("1111","导入信息失败");
                }
                log.info("导入信息成功");
                return ItooResult.build("0000","导入信息成功", bool);

    }


}
