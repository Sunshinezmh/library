
package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;
import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;

import java.io.Serializable;
import java.util.Date;


/**
 * Borrowing
 */
@ExcelModelConfig
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Borrowing  implements Serializable {

  private String id = null;

  private String name = null;

  private String autor = null;
  @Lang(value="借书日期")
  private Date borrowDate = null;
  @Lang(value="实际还书日期")
  private Date realDate = null;

  private Date endDate = null;

  private String  picture = null;

  @InputDicConfig(dicCode = "SJMC")//Excel导入的配置
  @Lang(value="书籍ID")
  private String bookId = null;

  @Lang(value="续借情况")
  private Integer renew = 0;
  @Lang(value="超期情况")
  private Integer isOverdue = null;

  //private String operator =null;
  @Lang(value="书籍名称")
  private String bookName = null;
  @Lang(value="索书号")
  private String searchNumber = null;

  private String studentId = null;
  @Lang(value="学号")
  private String studentCode = null;
  @Lang(value="姓名")
  private String studentName = null;
  @Lang(value="专业")
  private String professionName = null;
  @Lang(value="班级")
  private String className = null;


  /**
   * borrowing Status
   */
  public enum StatusEnum {
    ALREADYRETURN("已还"),
    
    NOTRETURN("未还"),
    
    OUTOFDATE("超期"),
    
    ALL("全部");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private StatusEnum status = null;

  private String remain = null;

  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing name(String name) {
    this.name = name;
    return this;
  }

  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  public String getStudentCode() {
    return studentCode;
  }

  public void setStudentCode(String studentCode) {
    this.studentCode = studentCode;
  }

  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing studentCode(String studentCode) {
    this.studentCode = studentCode;
    return this;
  }


  @ApiModelProperty(example = "", required = true, value = "")
  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing bookName(String bookName) {
    this.name = name;
    return this;
  }

  @ApiModelProperty(example = "", required = true, value = "")
  public String getSearchNumber (){
    return searchNumber;
  }

  public void setSearchNumber(String searchNumber) {
    this.searchNumber = searchNumber;
  }

  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing searchNumber(String searchNumber) {
    this.searchNumber = searchNumber;
    return this;
  }


  @ApiModelProperty(example = "", required = true, value = "")
  public String  getStudentId(){
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing studnetId(String studentId) {
    this.studentId = studentId;
    return this;
  }



  @ApiModelProperty(example = "", required = true, value = "")
  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing professionName(String professionName) {
    this.professionName = professionName;
    return this;
  }
  @ApiModelProperty(example = "", required = true, value = "")
  public String getProfessionName() {
    return professionName;
  }

  public void setProfessionName(String professionName) {
    this.professionName = professionName;
  }



  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing className(String className) {
    this.className = className;
    return this;
  }
  @ApiModelProperty(example = "", required = true, value = "")
  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }



  /**
   * isOverdue
   * @param isOverdue
   * @return
   */
  @ApiModelProperty(example="",required = true,value = "")
  public Borrowing isOverdue(Integer isOverdue){
    this.isOverdue = isOverdue;
    return this;
  }

  public Integer getIsOverdue(){return isOverdue;}

  public void setIsOverdue(Integer isOverdue){
    this.isOverdue = isOverdue;

  }
  /**
   * reNew
   * @param reNew
   * @return
   */
  @ApiModelProperty(example = "", required = true, value = "")
  public Borrowing reNew(Integer renew){
    this.renew = renew;
    return this;
  }

  public void setreNew(Integer renew){this.renew = renew;}

  public Integer getreNew(){return renew;}

  /**
   * bookId
   * @param bookId
   * @return
   */
  @ApiModelProperty(example = "", required = true, value = "")
  public Borrowing bookId(String bookId){
    this.bookId = bookId;
    return this;
  }

  public void setBookId(String bookId){this.bookId = bookId;}

  public String getBookId(){return bookId;}


   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "《疯狂Java讲义》", required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Borrowing autor(String autor) {
    this.autor = autor;
    return this;
  }

   /**
   * Get autor
   * @return autor
  **/
  @ApiModelProperty(example = "李刚", required = true, value = "")
  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public Borrowing borrowDate(Date borrowDate) {
    this.borrowDate = borrowDate;
    return this;
  }

   /**
   * Get borrowDate
   * @return borrowDate
  **/
  @ApiModelProperty(example = "2017年8月16日11:17:11", required = true, value = "")
  public Date getBorrowDate() {
    return borrowDate;
  }

  public void setBorrowDate(Date borrowDate) {
    this.borrowDate = borrowDate;
  }

  public Borrowing realDate(Date realDate) {
    this.realDate = realDate;
    return this;
  }

   /**
   * Get realDate
   * @return realDate
  **/
  @ApiModelProperty(example = "2017年8月20日11:17:31", required = true, value = "")
  public Date getRealDate() {
    return realDate;
  }

  public void setRealDate(Date realDate) {
    this.realDate = realDate;
  }

  public Borrowing endDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(example = "2017年9月16日11:17:31", required = true, value = "")
  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Borrowing  picture(String picture) {
    this.picture = picture;
    return this;
  }

   /**
   * Get picture
   * @return picture
  **/
  @ApiModelProperty(required = true, value = "")
  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Borrowing status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * borrowing Status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "borrowing Status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Borrowing remain(String remain) {
    this.remain = remain;
    return this;
  }

   /**
   * Get remain
   * @return remain
  **/
  @ApiModelProperty(example = "剩余2次", required = true, value = "")
  public String getRemain() {
    return remain;
  }

  public void setRemain(String remain) {
    this.remain = remain;
  }

}


