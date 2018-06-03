package com.dmsdbj.library.facade;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.viewmodel.Student;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface StudentFacade {
     ItooResult addStudent(Student form);
     ItooResult deleteStudent(Long studentId);
     ItooResult deleteStudents(Long studentIds);
     ItooResult findOverdueStudent(String dateReturnTime,Integer pageSize,Integer pageNum);
     ItooResult getAllStudent(Integer pageSize,Integer pageNum);
     ItooResult getStudent(String conditions,Integer pageSize,Integer pageNum);
     ItooResult getStudentBystudentID(String studentID,Integer pageSize,Integer pageNum);
     ItooResult importStudent(Student form);
     ItooResult DownExcel();
     ItooResult ExportStudent();
     ItooResult updateStudent(Student body);
}
