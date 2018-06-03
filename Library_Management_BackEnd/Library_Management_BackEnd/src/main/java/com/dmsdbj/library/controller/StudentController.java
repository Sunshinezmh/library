package com.dmsdbj.library.controller;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.app.util.FastDfsUtils;
import com.dmsdbj.library.controller.util.UploadFileUtil;
import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.entity.TUser;
import com.dmsdbj.library.service.StudentService;
import com.dmsdbj.library.service.UserService;
import com.dmsdbj.library.viewmodel.Student;
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

@Path("/student")


@Api(value = "/student", description = "the student API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public class StudentController {

    @Inject
    private  StudentService studentService;
    @Inject
    private UserService userService;

    @Inject
    private Logger log;

    @GET
    @Path("/getStudentBystudentCode/{studentCode}")
    @Produces({"application/json"})
    @ApiOperation(value = "获取学生信息", notes = "获取全部学生信息 ", response = Student.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "获得查询结果", response = Student.class),

            @ApiResponse(code = 400, message = "Not Found", response = Student.class)})
    public ItooResult getStudentBystudentCode(@ApiParam(value = "页面显示条数", required = true) @PathParam("studentCode") String studentCode) throws NotFoundException {
        List<TStudent> list = studentService.getStudentBystudentCode(studentCode);
        if (list == null || list.isEmpty()) {
            log.error("获取全部学生信息失败");
            return ItooResult.build("1111", "获取全部学生信息失败");
        } else {
            return ItooResult.build("0000", "获取全部学生信息成功", list);
        }
    }

    /**
     * 王华伟--查询所有学生--2017--11--16
     *
     * @param studentCode
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getAllStudent")
    @Produces({"application/json"})
    @ApiOperation(value = "获取全部学生信息", notes = "获取全部学生信息 ", response = Student.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "获得查询结果", response = Student.class),

            @ApiResponse(code = 400, message = "Not Found", response = Student.class)})
    public ItooResult getAllStudent() throws NotFoundException {
        List<TStudent> list = studentService.getAllStudent();
        if (list == null || list.isEmpty()) {
            log.error("获取全部学生信息失败");
            return ItooResult.build("1111", "获取全部学生信息失败");
        } else {
            return ItooResult.build("0000", "获取全部学生信息成功", list);
        }
    }

    /**
     * 王华伟--模糊查询--2017--16--查询条件：班级/姓名/学号/专业/电话/邮箱
     *
     * @param conditions
     * @param pageSize
     * @param pageNum
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getStudentByCondition/{conditions}/{pageSize}/{pageNum}")

    @Produces({"application/json"})
    @ApiOperation(value = "模糊查学生信息", notes = "根据班级/姓名/学号/专业/电话/邮箱获得学生信息 ", response = Student.class, responseContainer = "List", tags = "PC")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "获得查询结果", response = Teacher.class),

            @ApiResponse(code = 400, message = "Not Found", response = Teacher.class)})

    public ItooResult getTeacherByCondition(@ApiParam(value = "查询条件可以为空", required = true) @PathParam("conditions") String conditions, @ApiParam(value = "页面显示条数", required = true) @PathParam("pageSize") Integer pageSize, @ApiParam(value = "第几页", required = true) @PathParam("pageNum") Integer pageNum) throws NotFoundException {
        List<TStudent> listStudent = studentService.findStudentByCondition(conditions);
        if (listStudent != null && listStudent.size() > 0) {
            log.info("获取全部学生信息成功");
            return ItooResult.build("0000", "获取全部学生信息成功", listStudent);
        } else {
            log.error("获取全部学生信息失败");
            return ItooResult.build("1111", "获取全部学生信息失败");
        }
    }

    /**
     * 王华伟--2017--11--13--添加学生
     *
     * @param tStudent
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/addStudent")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "添加（电脑端）", notes = "添加一条学生信息", response = void.class, tags = "Admin")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "item created", response = void.class), @ApiResponse(code = 400, message = "invalid input, object invalid", response = Void.class), @ApiResponse(code = 409, message = "an existing item already exists", response = Void.class)})
    public ItooResult addStudent(@ApiParam(value = "Inventory item to add") TStudent tStudent, @Context SecurityContext securityContext) throws NotFoundException, URISyntaxException {

        boolean flag = false;

        flag = studentService.addStudent(tStudent);
        if (flag == false) {
            log.error("学生记录添加失败");
            return ItooResult.build("1111", "失败");

        } else {
            log.info("学生记录添加成功");
            return ItooResult.build("0000", "成功", flag);
        }
    }


    /**
     * 王华伟--2017--11-14--编辑学生信息
     *
     * @param tStudent
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/updateStudent")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑学生信息", notes = "", response = TStudent.class, tags = "PC")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),

            @ApiResponse(code = 404, message = "not found", response = void.class),

            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})

    public ItooResult updateStudent(@ApiParam(value = "提交更改的信息", required = true) TStudent tStudent, @Context SecurityContext securityContext) throws NotFoundException {
        try {
            if (tStudent.getPictrue() != null) {
                if (tStudent.getPictrue().contains("http")) {
                    tStudent.setPictrue(tStudent.getPictrue().substring(tStudent.getPictrue().indexOf(FastDfsUtils.getFastDfsIP()) + FastDfsUtils.getFastDfsIP().length()));
                }
            }

            boolean bool = studentService.updateStudent(tStudent);
            if (bool == true) {
                log.info("编辑信息成功");
                return ItooResult.build("0000", "编辑信息成功", bool);
            } else {
                log.error("编辑信息失败");
                return ItooResult.build("1111", "编辑信息失败");
            }
        } catch(Exception e) {
            log.info("编辑信息失败");
            return ItooResult.build("1111", "编辑信息失败");
        }

    }

    /**
     * 王华伟--2017--11--13--根据ID删除学生
     *
     * @param IDs
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/deleteStudentBytudentCodes/{Ids}")
    @Produces({"application/json"})
    @ApiOperation(value = "删除学生信息", notes = "删除学生信息", response = void.class, responseContainer = "List", tags = "PC")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "删除成功", response = void.class),

            @ApiResponse(code = 400, message = "Not Found", response = void.class)})
    public ItooResult deleteStudentBystudentCodes(@ApiParam(value = "student Ids to delete", required = true) @PathParam("Ids") String Ids) throws NotFoundException {
        String[] IDArr;
        boolean flag = false;
        IDArr = Ids.split(",");
        for (int i = 0; i < IDArr.length; i++) {
            flag = studentService.deleteStudentByStudentCodes(IDArr[i]);
        }

        if (flag == true) {
            log.info("删除学生成功");
            return ItooResult.build("0000", "学生删除成功", flag);
        } else {
            log.error("删除学生失败");
            return ItooResult.build("1111", "删除学生失败");
        }

    }

    /**
     * 王华伟--下载模板--2017--11-16--修改
     *
     * @param requst
     * @param response
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/downExcel")
    @Produces({"application/json"})
    //@ApiOperation(value = "下载模板", response = BookType.class, responseContainer = "List", tags = "Android")
    @ApiOperation(value = "下载模板", notes = "下载模板  ", response = void.class, tags = "PC")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功", response = void.class), @ApiResponse(code = 400, message = "Not Found", response = void.class)})
    public ItooResult downExcelGet(@Context HttpServletRequest requst, @Context HttpServletResponse response) throws NotFoundException {
        Map<Serializable, Serializable> map = new HashMap<>();
        //sheet的名字
        map.put("sheetName", "sheet1");
        //需要导出的字段
        map.put("columns", new String[]{"code", "name", "professionName", "classesName", "telNum", "email"});
        //导出的表格标题
        map.put("title", "学生信息");

        List<TStudent> studentList = new ArrayList<>();
        TStudent student = new TStudent();
        student.setCode("130015");
        student.setName("刘雅雯15");
        student.setProfessionName("计算机");
        student.setClassesName("计算机一班");
        student.setTelNum("13116167375");
        student.setEmail("13116167275@163.com");

        studentList.add(student);
        map.put("dataList", (Serializable) studentList);

        try {
            ExcelUtil.exportExcel(map, response);
            log.info("下载成功");
            return ItooResult.build("0000", "下载成功");

        } catch (Exception e) {
            log.error("下载失败");
            return ItooResult.build("1111", "下载失败");
        }
    }

    /**
     * 王华伟--2017-11-14-导出
     *
     * @param response
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @GET
    @Path("/exportStudent/{conditions}")
    @Produces({"application/json"})
    @ApiOperation(value = "导出", notes = "下载模板  ", response = void.class, tags = "PC")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "导出成功", response = void.class),

            @ApiResponse(code = 400, message = "导出失败", response = void.class)})
    public ItooResult exportBookType(@Context HttpServletResponse response, @PathParam("conditions") String conditions) throws NotFoundException, Exception {
        Map<Serializable, Serializable> map = new HashMap<>();
        conditions = conditions.trim();
        if (conditions.isEmpty()) {
            List<TStudent> studentList = studentService.getAllStudent();
            map.put("sheetName", "sheet1");
            //要导出的字段
            map.put("columns", new String[]{"code", "name", "professionName", "classesName", "telNum", "email"});
            map.put("title", "学生信息");
            map.put("dataList", (Serializable) studentList);
        } else {
            //获取要导出的全部数据
            List<TStudent> studentList = studentService.findStudentByCondition(conditions);
            map.put("sheetName", "sheet1");
            //要导出的字段
            map.put("columns", new String[]{"code", "name", "professionName", "classesName", "telNum", "email"});
            map.put("title", "学生信息");
            map.put("dataList", (Serializable) studentList);
        }

        try {
            log.info("导出成功");
            ExcelUtil.exportExcel(map, response);
            return ItooResult.build("0000", "导出成功");
        } catch (Exception e) {
            log.error("导出失败");
            return ItooResult.build("1111", "导出失败");
        }
    }


    /**
     * 王华伟--2017--11--14--学生中的树
     *
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/initTree")
    @Produces({"application/json"})
    @ApiOperation(value = "学生信息管理中树", notes = "查找学院 ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "获得所有一级学院", response = void.class),

            @ApiResponse(code = 400, message = "Not Found", response = void.class)})
    public ItooResult studentGetParent() throws NotFoundException {
        Student student = new Student();
        String pid = student.getpId();
        ArrayList<Student> list = studentService.findParentCollege();
        //List<Student> list1 = studentService.findParentCollege1(pid);
        if (list != null) {
            log.info("可获得所有类别成功");
            return ItooResult.build("0000", "获取父类成功", list);
        } else {
            log.error("获得所有一级失败");
            return ItooResult.build("1111", "获取父类失败");
        }
    }


    /**
     * 王华伟--2017--11--14--导入
     *
     * @param response
     * @param request
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @POST
    @Path("/importStudent")
    @Consumes({"multipart/form-data"})
    //@Consumes({ "application/json" })
    @Produces({"application/json"})
    @ApiOperation(value = "通过excel导入学生信息", notes = "Adds items to the " + "system", response = void.class, tags = "PC")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "item created", response = void.class),

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = void.class),

            @ApiResponse(code = 409, message = "an existing item already exists", response = void.class)})
    public ItooResult importTeacher(@Context HttpServletResponse response, @Context HttpServletRequest request) throws NotFoundException, Exception {
        String filePath = UploadFileUtil.upload(response, request);
        // String filePath = "\u202AX:\\Users\\Harvey\\Desktop\\学生信息.xlsx";
        //String filePath="X:\\Users\\Harvey\\Desktop\\学生信息.xlsx";
        Map<Serializable, Serializable> map = new HashMap<>();
        map.put("sheetName", "Sheet1");
        map.put("Class", TStudent.class);
        boolean bool = false;
        try {
            //获取excel中的数据
            List<TStudent> importList = ExcelUtil.importExcel(filePath, response, map);
            TStudent student = new TStudent();
            TStudent studentEntity = new TStudent();
            TUser tUser = new TUser();
            for (int n = 0; n < importList.size(); n++) {

                //集合，list
                student = importList.get(n);
                //TODO
                //studentEntity.setId(student.getId());
                studentEntity.setCode(student.getCode());
                studentEntity.setName(student.getName());
                studentEntity.setGraduateSchool(student.getGraduateSchool());
                studentEntity.setProfessionName(student.getProfessionName());
                studentEntity.setClassesName(student.getClassesName());
                studentEntity.setTelNum(student.getTelNum());
                studentEntity.setEmail(student.getEmail());

                //tUser.setId(studentEntity.getId());
                //tUser.setName(studentEntity.getName());
                //判断要添加的是否已经存在,存在跳出来
                List<TStudent> tStudentsList = studentService.getStudent(studentEntity.getCode());
                //List<TUser> tUserList = userService
                if (tStudentsList != null && tStudentsList.size() != 0) {

                    continue;
                }

                studentService.addStudent(studentEntity);
                //userService.addUser(tUser.getId(),tUser.getName());
                bool = true;

            }
        } catch (Exception e) {
            bool = false;
            log.error("导入信息失败");
            return ItooResult.build("1111", "导入信息失败");
        }
        log.info("导入信息成功");
        return ItooResult.build("0000", "导入信息成功", bool);

    }


}
