package com.dmsdbj.library.facade;



import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.viewmodel.Teacher;
//import com.dmsdbj.itoo.tool.business.ItooResult;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface TeacherFacade {
     ItooResult addTeacher(Teacher form);
     ItooResult deleteTeacher(Long teacherId);
     ItooResult deleteTeachers(Long teacherIds);
     ItooResult getAllTeacher(Integer pageSize,Integer pageNum);
     ItooResult getTeacherByCondition(String conditions,Integer pageSize,Integer pageNum);
     ItooResult importTeacher(Teacher form);
     ItooResult DownExcelGet();
     ItooResult ExportTeacher();
     ItooResult updateTeacher(Teacher body);
}
