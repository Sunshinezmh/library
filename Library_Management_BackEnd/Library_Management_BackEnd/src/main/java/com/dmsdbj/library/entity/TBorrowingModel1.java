package com.dmsdbj.library.entity;

import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDateConfig;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;
import org.jplus.hyberbin.excel.annotation.input.InputIntConfig;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

@ExcelModelConfig
@Entity

    public class TBorrowingModel1 extends AbstractAuditingEntity implements Serializable{

    //使用构造器
    public TBorrowingModel1(){this.borrowTime = null;
 }

    public TBorrowingModel1(String bookId,String id,String code, String studentName, String professionName, String
            classesName, String author,String location,String bookName, String content,Date borrowTime, Date endDate, Date realDate, Integer isOverdue,
                            Integer renew, Integer borrowStatus, String searchNum,String picture){
        this.borrowTime = null;

        this.bookId = bookId;
        this.setId(id);
        this.code = code;
        this.studentName = studentName;
        this.professionName =professionName;
        this.classesName = classesName;
        this.author = author;
        this.location=location;
        this.bookName = bookName;
        this.content=content;
        this.borrowTime=borrowTime;
        this.endDate=endDate;
        this.realDate=realDate;
        this.isOverdue=isOverdue;
        this.renew=renew;
        this.borrowStatus=borrowStatus;
        this.searchNum = searchNum;
        this.picture = picture;

    }

//    //已还、超期、待还页面的显示---王华伟-2018年3月24日10:24:35
//    public TBorrowingModel1(String picture, String name, String isbn, String author,String content,Date realDate,Date borrowTime,Date endDate ){
//        this.picture = picture;
//        this.name = name;
//        this.isbn = isbn;
//        this.author = author;
//        this.content = content;
//        this.realDate = realDate;
//        this.borrowTime = borrowTime;
//        this.endDate = endDate;
//    }

   /**
    *
    *
    * @param id
    * @param borrowTime
    * @param endDate
    * @param code
    * @param professionName
    * @param classesName
    * @param
    * @param isOverdue
    * @param renew
    * @param searchNum
    */
    public TBorrowingModel1(Date realDate,String id,Date borrowTime,int isOverdue,Date endDate,int renew,String code,String classesName,String
            professionName,String bookName,String searchNum,String location){
         this.setId(id);
         this.realDate=realDate;
         this.borrowTime=borrowTime;
         this.isOverdue=isOverdue;
         this.endDate=endDate;
         this.renew = renew;
         this.code = code;
         this.bookName = bookName;
         this.classesName= classesName;
         this.professionName=professionName;
         this.studentName = studentName;
         this.searchNum= searchNum;
         this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRealDate() {
        return realDate;
    }

    public void setRealDate(Date realDate) {
        this.realDate = realDate;
    }

    public Integer getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Integer isOverdue) {
        this.isOverdue = isOverdue;
    }

    public Integer getRenew() {
        return renew;
    }

    public void setRenew(Integer renew) {
        this.renew = renew;
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(String searchNum) {
        this.searchNum = searchNum;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    @InputTextConfig(nullAble=false)
    @Lang(value="学号")
    private String code =null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name = null;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @InputTextConfig(nullAble=false)
    @Lang(value = "标识号")
    private String isbn = null;

    @InputTextConfig(nullAble=false)
    @Lang(value = "姓名")
    private String studentName =null;
    
    @InputTextConfig(nullAble=false)
    @Lang(value="专业")
    private String professionName =null;
    
    @InputTextConfig(nullAble=false)
    @Lang(value="班级")
    private String classesName =null;
    @InputTextConfig(nullAble=false)
//    @InputDicConfig(dicCode = "isbn")
    @Lang(value="书名")
    private String bookName=null;

    private String author = null;


    private String content =null;
    
    @InputDateConfig(nullAble=false)
    @Lang(value="借书日期")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date borrowTime = new Date();
    
    @InputDateConfig(nullAble=false)
    @Lang(value="还书日期")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate =null;
    
    @InputDateConfig(nullAble=false)
    @Lang(value="实际还书日期")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date realDate=null;

    @InputIntConfig(nullAble=false)
    @Lang(value="是否续借")
    private Integer isOverdue=null;
    
    @InputIntConfig(nullAble=false)
    @Lang(value="续借次数")
    private Integer renew =null;

     
    @Lang(value = "借阅状态")
	@InputIntConfig(nullAble=false)
    private Integer borrowStatus =null;
    
    @InputTextConfig(nullAble=false)
    @Lang(value="索书号")
    private String searchNum =null;

    private String userNum=null;

    private  String  borrwoingCount = null;

    public String getuserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getBorrwoingCount() {
        return borrwoingCount;
    }

    public void setBorrwoingCount(String borrwoingCount) {
        this.borrwoingCount = borrwoingCount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private  String picture=null;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    private String bookId = null;

    @InputTextConfig(nullAble=false)
    @Lang(value="书籍位置")
    private String location=null;
}