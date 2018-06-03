package com.dmsdbj.library.viewmodel;

/**
 * Student 郑晓东 2017年11月12日21点24分
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public class Student   {
  //学生ID
  private String id = null;
  //学生学号
  private String studentCode = null;
  //学生姓名
  private String studentName = null;
  //学生性别
  private String sex=null;
  //学生专业
  private String major = null;
  //学生班级
  private String className = null;
  //手机号
  private String iphoneNum = null;
  //邮箱
  private String emaill = null;
  //照片
  private String picture=null;
  //以借阅数量
  private Integer borrowCount = null;
  //学院（王华伟--2017--11--14--添加）
  private String graduatedSchool = null;

  public String getpId() {
    return pId;
  }

  public void setpId(String pId) {
    this.pId = pId;
  }

  //学院（一级“建工学院”；王华伟--2017--11--14--添加）
  private String pId = null;
  //专业名称（二级“专业名称”；王华伟--2017--11--14--添加）
  private String professionalName = null;
//  //班级名称（三级“班级名称”；王华伟--2017--11--14--添加）

  public String getAlluser_id() {
    return alluser_id;
  }

  public void setAlluser_id(String alluser_id) {
    this.alluser_id = alluser_id;
  }

  //与itoo的tc_alluser表相关联
  private String alluser_id = null;



//  private String className = null;
  /**树中的name**/
  private String name =null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProfessionalName() {
    return professionalName;
  }

  public void setProfessionalName(String professionalName) {
    this.professionalName = professionalName;
  }
//  public String getPID() {
//    return pId;
//  }
//
//  public void setPID(String PID) {
//    this.pId= PID;
//  }
  public String getGraduatedSchool() {
    return graduatedSchool;
  }

  public void setGraduatedSchool(String graduatedSchool) {
    this.graduatedSchool = graduatedSchool;
  }
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getStudentCode() {
    return studentCode;
  }

  public void setStudentCode(String studentCode) {
    this.studentCode = studentCode;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getIphoneNum() {
    return iphoneNum;
  }

  public void setIphoneNum(String iphoneNum) {
    this.iphoneNum = iphoneNum;
  }

  public String getEmaill() {
    return emaill;
  }

  public void setEmaill(String emaill) {
    this.emaill = emaill;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Integer getBorrowCount() {
    return borrowCount;
  }

  public void setBorrowCount(Integer borrowCount) {
    this.borrowCount = borrowCount;
  }

  public Student(){ }

  public Student(String id,String name,String sex,String className,String picture,Integer borrowCount ){
    this.id=id;
    this.studentName=name;
    this.sex=sex;
    this.className=className;
    this.picture=picture;
    this.borrowCount=borrowCount;
  }
}

