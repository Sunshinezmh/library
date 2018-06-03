
package com.dmsdbj.library.viewmodel;


import com.dmsdbj.library.entity.TReservation;
import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Reservation
 */
@ExcelModelConfig
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Reservation extends TReservation {

    @InputTextConfig(nullAble=false)
    private String viewUserId;
    @InputTextConfig(nullAble=false)
    @Lang(value="学号")
    @InputDicConfig(dicCode = "viewUserId")
    private String studentNo;

    @InputTextConfig(nullAble=false)
    @Lang(value="姓名")
    private String studentName;

    @InputTextConfig(nullAble=false)
    @Lang(value="专业")
    private String professionName;

    @InputTextConfig(nullAble=false)
    @Lang(value="班级")
    private String classesName;


    @InputTextConfig(nullAble=false)
    @Lang(value="书籍名称")
    private String bookName;

    @InputTextConfig(nullAble=false)
    @Lang(value="书籍位置")
    private String location;

    @InputTextConfig(nullAble=false)
    @Lang(value="预约创建时间")
    private String viewCreateTime;
    private String picture;
    private Integer count;
    private  String borrowDate;
    private Integer isBorrowed;
    private String authorName;

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @InputTextConfig(nullAble=false)
    @Lang(value="国家标准书号")
    private String viewIsbn;

    @InputTextConfig(nullAble=false)
    @Lang(value="导入失败原因")
    private String reason;

    public Reservation() {
    }

    /**
     *  getBookReservationInfobyisbnAndlocation根据isnb和location条件查询预约记录所用的构造函数--杜雨--2018年5月10日11:01:34
     * @param id
     * @param userId
     * @param isbn
     * @param studentNo
     * @param studentName
     * @param professionName
     * @param classesName
     * @param bookName
     * @param dateTime
     * @param location
     * @param picture
     */
    public Reservation(String userId, String isbn, String studentName, String professionName, String classesName,
                       String bookName, Date createtime,String location,String picture) {
        this.setUserId(userId);
        this.setViewUserId(userId);
        this.studentName = studentName;
        this.professionName = professionName;
        this.classesName = classesName;
        this.bookName = bookName;
        this.setViewIsbn(isbn);
        this.setIsbn(isbn);
        this.location=location;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        String date = format.format(createtime);
//        this.setViewCreateTime(date);
        this.setCreateTime(format.parse(date,pos));
        this.picture = picture;
    }


    /**
     * findReservationByPagenationCondition根据模糊条件分页查询 预约记录（预约管理)所用的构造函数
     * @param id
     * @param user_id
     * @param bookId
     * @param studentNo
     * @param studentName
     * @param professionName
     * @param classesName
     * @param bookName
     * @param searchNum
     * @param dateTime
     */
    public Reservation(String id, String userId, String isbn, String studentNo, String studentName, String professionName, String classesName,
                       String bookName, Date dateTime,String location) {
        this.studentNo = studentNo;
        this.setUserId(userId);
        this.setViewUserId(userId);
        this.studentName = studentName;
        this.professionName = professionName;
        this.classesName = classesName;
        this.bookName = bookName;
        this.setViewIsbn(isbn);
        this.setIsbn(isbn);
        this.setId(id);
        this.location=location;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        String date = format.format(dateTime);
        this.setViewCreateTime(date);
        this.setCreateTime(format.parse(date,pos));
    }

    /**
     * 对应findReserveByUserId根据用户id查询用户预约的书籍的方法的构造函数
     * @param id
     * @param userId
     * @param bookId
     * @param bookName
     * @param authorName
     * @param isBorrowed
     * @param borrowDate
     */
    public Reservation(String id, String userId, String isbn,String bookName,
                       String authorName,Integer isBorrowed,String picture,String location){
        this.setId(id);
        this.setUserId(userId);
        this.setViewUserId(userId);
        this.setIsbn(isbn);
        this.setBookName(bookName);
        this.setAuthorName(authorName);
        //0表示可借，1表示不可借
        this.isBorrowed = isBorrowed >0 ? 0:1;
        this.picture = picture;
        this.location=location;
    }


    public String getViewUserId() {
        return viewUserId;
    }

    public void setViewUserId(String viewUserId) {
        this.viewUserId = viewUserId;
    }
    public String getViewCreateTime() {
        return viewCreateTime;
    }

    public void setViewCreateTime(String viewCreateTime) {
        this.viewCreateTime = viewCreateTime;
    }
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIsBorrowed() {
        return isBorrowed;
    }

    public void setIsBorrowed(Integer isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
    public String getViewIsbn() {
        return viewIsbn;
    }

    public void setViewIsbn(String viewIsbn) {
        this.viewIsbn = viewIsbn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }




}
