/**
 * This file was generated by the Jeddict
 */
package com.dmsdbj.library.viewmodel;

import org.joda.time.DateTime;
import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDateConfig;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;
import org.jplus.hyberbin.excel.annotation.input.InputIntConfig;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;
import sun.util.calendar.LocalGregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;


@ExcelModelConfig
@Entity

public class BookViewModel implements Serializable {


    public Integer getWareCount() {
        return wareCount;
    }

    public void setWareCount(Integer wareCount) {
        this.wareCount = wareCount;
    }

    private Integer wareCount;

    /**
     * 使用构造器-张婷-2017-11-8 09:45:34
     **/
    public BookViewModel() {
    }

    /**
     * 使用构造器，联查book、bookbasic、bookandtype、warehuse四张表取出图书信息需要的字段
     **/

    public BookViewModel(String id, String searchNum, String isbn, String bookName, String author, String content, String summary,
                         Integer totalPage, String language, String publishTime, Date updateTime, String typeName, String owner,
                         String remark, Integer usenum, Integer wareCount, String publishplace,
                         String picture, String origin, Double bid, Double primCost, Integer rank, Double sellPrice, String operator) {
        this.author = author;
        this.wareCount = wareCount;
        this.bookName = bookName;
        this.content = content;
        this.id = id;
        this.isbn = isbn;
        this.languagea = language;
        this.owner = owner;
        this.publishtime = publishTime;
        this.remark = remark;
        this.searchNum = searchNum;
        this.summary = summary;
        this.totalpage = totalPage;
        this.typeName = typeName;
        this.updatetime = updateTime;
        this.usenum = usenum;
        this.publishplace = publishplace;
        this.picture = picture;
        this.origin = origin;
        this.bid = bid;
        this.primCost = primCost;
        this.sellPrice = sellPrice;
        this.rank = rank;
        this.operator = operator;
    }

    //使用构造器，联查book、bookbasic、bookandtype、warehuse四张表取出图书信息需要的字段--添加location--张明慧
    public BookViewModel(String id, String searchNum, String isbn, String bookName, String author, String content, String summary,
                         Integer totalPage, String language, String publishTime, Date updateTime, String typeName, String owner,
                         String remark, Integer usenum, Integer wareCount, String publishplace,
                         String picture, String origin, Double bid, Double primCost, Integer rank, Double sellPrice, String operator, String location) {
        this.author = author;
        this.wareCount = wareCount;
        this.bookName = bookName;
        this.content = content;
        this.id = id;
        this.isbn = isbn;
        this.languagea = language;
        this.owner = owner;
        this.publishtime = publishTime;
        this.remark = remark;
        this.searchNum = searchNum;
        this.summary = summary;
        this.totalpage = totalPage;
        this.typeName = typeName;
        this.updatetime = updateTime;
        this.usenum = usenum;
        this.publishplace = publishplace;
        this.picture = picture;
        this.origin = origin;
        this.bid = bid;
        this.primCost = primCost;
        this.sellPrice = sellPrice;
        this.rank = rank;
        this.operator = operator;
        this.location = location;

    }

    //    br.id,ts.name,tbb.author,tbb.isbn,tbb.location,tbb.name,tbb" +
//            ".content,br.borrowTime,br.endDate,br.realDate,br.isOverdue,br.renew,br.borrowStatus," +
//            "tbb.picture
    //PC端首页查询所有借阅记录。—田成荣—2018年5月6日20:36:56
    public BookViewModel(String userid, String id, String studentName, String author, String isbn, String location, String bookName, String content, Date borrowTime, Date endDate, Date realDate, Integer isOverdue,
                         Integer renew, Integer borrowStatus, String picture) {
        this.userId = userid;
        this.id = id;
        this.studentName = studentName;
        this.author = author;
        this.isbn = isbn;
        this.location = location;
        this.bookName = bookName;
        this.content = content;
        this.borrowTime = borrowTime;
        this.endDate = endDate;
        this.realDate = realDate;
        this.isOverdue = isOverdue;
        this.renew = renew;
        this.borrowStatus = borrowStatus;
        this.picture = picture;

    }

    public BookViewModel(String id) {
        this.id = id;
    }


    //王华伟---三表连查（student、borrowing、bookbasic表）--显示页面信息--2018年3月28日15:01:25
    public BookViewModel(String studentName, String professionName, String
            classesName, String author, String bookName, String content, Date borrowTime, Date endDate, Date realDate, Integer isOverdue,
                         Integer renew, Integer borrowStatus, String picture, String location) {
        this.studentName = studentName;
        this.professionName = professionName;
        this.classesName = classesName;
        this.author = author;
        this.bookName = bookName;
        this.content = content;
        this.borrowTime = borrowTime;
        this.endDate = endDate;
        this.realDate = realDate;
        this.isOverdue = isOverdue;
        this.renew = renew;
        this.borrowStatus = borrowStatus;
        this.picture = picture;
        this.location = location;
    }

    //盘点查询--田成荣2018年5月17日09:17:34
    public BookViewModel(String id, String isbn, String name,  String location,Integer usenum,Date update) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = name;
        this.location = location;
        this.usenum = usenum;
        this.updatetime = update;
    }

    public BookViewModel(String id, String isbn, String name,  String location,Integer usenum, int addcount,int result,String ischeck, Date createTime, Date update,Date endTime) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = name;
        this.location = location;
        this.usenum = usenum;
        this.addcount = addcount;
        this.result = result;
        this.ischeck = ischeck;
        this.createTime = createTime;
        this.updatetime = update;
        this.endTime = endTime;
    }
    //书籍详情页面——王雅静-2018年5月5日10:04:22
    public BookViewModel(String studentName, String author, String isbn, String location, String bookName, String content, Date borrowTime, Date endDate, Date realDate, Integer isOverdue,
                         Integer renew, Integer borrowStatus, String picture) {
        this.studentName = studentName;
        this.author = author;
        this.isbn = isbn;
        this.location = location;
        this.bookName = bookName;
        this.content = content;
        this.borrowTime = borrowTime;
        this.endDate = endDate;
        this.realDate = realDate;
        this.isOverdue = isOverdue;
        this.renew = renew;
        this.borrowStatus = borrowStatus;
        this.picture = picture;

    }


    //对应信息管理页面
    public BookViewModel(String id, String isbn, String bookName, String author, String content, String summary,
                         String language, Date updateTime, String remark, Integer useNum, Integer wareCount, String publishPlace,
                         Double bid, Double primCost, Integer rank, Double sellPrice) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.content = content;
        this.summary = summary;
        this.languagea = language;
        this.updatetime = updateTime;
        this.remark = remark;
        this.usenum = useNum;
        this.wareCount = wareCount;
        this.publishplace = publishPlace;
        this.bid = bid;
        this.primCost = primCost;
        this.rank = rank;
        this.sellPrice = sellPrice;
    }

    //图书管理页面的编辑，添加location--张明慧--2018年4月28日20:14:44
    public BookViewModel(String id, String isbn, String bookName, String author, String content, String summary,
                         String language, Date updateTime, String remark, Integer useNum, Integer wareCount, String publishPlace,
                         Double bid, Double primCost, Integer rank, Double sellPrice, String location) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.content = content;
        this.summary = summary;
        this.languagea = language;
        this.updatetime = updateTime;
        this.remark = remark;
        this.usenum = useNum;
        this.wareCount = wareCount;
        this.publishplace = publishPlace;
        this.bid = bid;
        this.primCost = primCost;
        this.rank = rank;
        this.sellPrice = sellPrice;
        this.location = location;
    }

    //对应PC端的图书详情页面-武刚鹏-2017年12月4日21:16:38--添加location-王雅静-2018年5月3日17:26:07
    public BookViewModel(String id, String searchNum, String isbn, String bookName, String author, String location, Integer totalPage, String publishTime, String owner, String remark, String typeName) {
        this.id = id;
        this.searchNum = searchNum;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.location = location;
        this.totalpage = totalPage;
        this.publishtime = publishTime;
        this.owner = owner;
        this.remark = remark;
        this.typeName = typeName;
    }


    //书城页面分页查询-武刚鹏-2017年12月1日17:18:40
    public BookViewModel(String bookId, String isbn, String bookName, String author, String content, String summary, String picture, String location, String remark) {
        this.id = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.content = content;
        this.summary = summary;
        this.picture = picture;
        this.location = location;
        this.remark = remark;

    }

    //猜你喜欢-分页查询书籍内容 武刚鹏-2017年11月23日12:34:41
    public BookViewModel(String bookId, String bookName, String isbn, String picture, String location) {
        this.bookName = bookName;
        this.id = bookId;
        this.isbn = isbn;
        this.picture = picture;
        this.location = location;
    }

    ////书架页面BookView--张明慧--2018年5月4日18:11:00
    public BookViewModel(String bookId, String bookName, String isbn, String picture, String location, String CollectionId) {
        this.id = bookId;
        this.bookName = bookName;
        this.isbn = isbn;
        this.picture = picture;
        this.location = location;
        this.collectionId = CollectionId;

    }

    //已还、超期、待还页面的显示---王华伟-2018年3月24日10:24:35
    public BookViewModel(String id, String picture, String name, String isbn, String location, String author, String content, Date realDate, Date borrowTime, Date endDate) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.content = content;
        this.realDate = realDate;
        this.borrowTime = borrowTime;
        this.endDate = endDate;
        this.location = location;
    }

    /**
     * 王华伟--添加location字段--2018年5月2日11:24:56
     *
     * @param id
     * @param picture
     * @param name
     * @param isbn
     * @param author
     * @param content
     * @param realDate
     * @param borrowTime
     * @param endDate
     * @param location
     */
    public BookViewModel(String id, String picture, String name, String isbn, String author, String content, Date realDate, Date borrowTime, Date endDate, String location) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.content = content;
        this.realDate = realDate;
        this.borrowTime = borrowTime;
        this.endDate = endDate;
        this.location = location;
    }
    /**
     * 王雅静--还书返回的路径--2018年5月10日15:09:46
     * @param userId
     * @param isbn
     * @param location
     * @param remark 借书上传的图片
     */
    public BookViewModel(String userId,String isbn,String location,String remark){
        this.userId=userId;
        this.name = name;
        this.isbn = isbn;
        this.location = location;
        this.remark=remark;
    }

    public BookViewModel(String id, String bookName, String isbn) {
        this.id = id;
        this.name = bookName;
        this.isbn = isbn;
    }


    public BookViewModel(String bookId, String bookName) {
        this.id = bookId;
        this.bookName = bookName;
    }

    @Id
    private String id = null;
    private String userId = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //王华伟添加--2018年3月28日15:15:52
    private String code = null;

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    @InputDicConfig(dicCode = "ischeck")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "是否盘点")
    private String ischeck = null;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    //王华伟添加--2018年3月28日15:15:52
    @InputDicConfig(dicCode = "studentName")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "姓名")
    private String studentName = null;

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    //王华伟添加--2018年3月28日15:15:52
    private String professionName = null;

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    //王华伟添加--2018年3月28日15:15:52
    private String classesName = null;

    public Integer getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Integer isOverdue) {
        this.isOverdue = isOverdue;
    }

    //王华伟添加--2018年3月28日15:15:52
    @InputDicConfig(dicCode = "isOverdue")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "是否续借")
    private Integer isOverdue = null;

    public Integer getRenew() {
        return renew;
    }

    public void setRenew(Integer renew) {
        this.renew = renew;
    }

    //王华伟添加--2018年3月28日15:15:52
    @InputDicConfig(dicCode = "renew")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "续借次数")
    private Integer renew = null;

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    //王华伟添加--2018年3月28日15:15:52
    private Integer borrowStatus = null;
    private String name = null;

    @InputDicConfig(dicCode = "searchNum")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "索书号")
    private String searchNum = null;

    @InputDicConfig(dicCode = "isbn")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "标识号")
    private String isbn = null;

    @InputDicConfig(dicCode = "bookName")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "书名")
    private String bookName = null;

    @InputDicConfig(dicCode = "author")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "作者")
    private String author = null;

    @InputDicConfig(dicCode = "content")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "目录")
    private String content = null;

    @InputDicConfig(dicCode = "summary")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "摘要")
    private String summary = null;

    @InputDicConfig(dicCode = "totalpage")//Excel导入的配置
    @InputIntConfig(nullAble = true)
    @Lang(value = "总页数")
    private Integer totalpage = null;
    @InputDicConfig(dicCode = "location")//Excel导入的配置
    @InputIntConfig(nullAble = true)
    @Lang(value = "书籍位置")
    private String location = null;
    @InputDicConfig(dicCode = "languagea")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "语种")
    private String languagea = null;

    // @InputDicConfig(dicCode = "publishtime")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "出版时间")
    private String publishtime = null;

    @InputDicConfig(dicCode = "updatetime")//Excel导入的配置
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @InputDateConfig(nullAble = true)
    @Lang(value = "更新时间")
    private Date updatetime = null;

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    @InputDicConfig(dicCode = "borrowTime")//Excel导入的配置
    @InputDateConfig(nullAble = true)
    @Lang(value = "借书日期")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date borrowTime = null;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @InputDicConfig(dicCode = "endDate")//Excel导入的配置
    @InputDateConfig(nullAble = true)
    @Lang(value = "还书日期")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate = null;


    public Date getRealDate() {
        return realDate;
    }

    public void setRealDate(Date realDate) {
        this.realDate = realDate;
    }

    @InputDicConfig(dicCode = "realDate")//Excel导入的配置
    @InputDateConfig(nullAble = true)
    @Lang(value = "实际还书日期")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date realDate = null;

    @InputTextConfig(nullAble = true)
    @Lang(value = "类别")
    @InputDicConfig(dicCode = "typeName")
/**Excel导入的配置**/
    private String typeName = null;

    @InputDicConfig(dicCode = "owner")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "来源")
    private String owner = null;

    @InputDicConfig(dicCode = "usenum")//Excel导入的配置
    @InputIntConfig(nullAble = true)
    @Lang(value = "数量")
    private Integer usenum = null;

    @InputDicConfig(dicCode = "remark")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "备注")
    private String remark = null;

    @InputDicConfig(dicCode = "publishplace")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "出版社")
    private String publishplace = null;

    @InputDicConfig(dicCode = "picture")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "图片")
    private String picture = null;

    @InputDicConfig(dicCode = "origin")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "来源")
    private String origin = null;

    @InputDicConfig(dicCode = "bid")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "进价")
    //private String bid=null;
    private Double bid = null;

    @InputDicConfig(dicCode = "sellPrice")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "售价")
    //private String sellPrice=null;
    private Double sellPrice = null;

    @InputDicConfig(dicCode = "rank")//Excel导入的配置
    @InputIntConfig(nullAble = true)
    @Lang(value = "评分")
    private Integer rank = null;

    @InputDicConfig(dicCode = "primCost")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "原价")
    private Double primCost = null;
    @InputDicConfig(dicCode = "operator")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "操作人")
    private String operator = null;

//田成荣--盘点需要
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getAddcount() {
        return addcount;
    }

    public void setAddcount(int addcount) {
        this.addcount = addcount;
    }

    @InputDicConfig(dicCode = "addcount")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "盘点量")
    private int addcount=0;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @InputDicConfig(dicCode = "result")//Excel导入的配置
    @InputTextConfig(nullAble = true)
    @Lang(value = "盘点结果")
    private int result=0;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date createTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private Date endTime;

    //图书收藏ID（查询图书收藏信息专用）--郑晓东
    private String collectionId;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getPrimCost() {
        return primCost;
    }

    public void setPrimCost(Double primCost) {
        this.primCost = primCost;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//@JsonIgnoreProperties(ignoreUnknown = true)

    /**
     * Get searchNum
     *
     * @return searchNum
     **/

    public String getremark() {
        return remark;
    }

    public void setremark(String remark) {
        this.remark = remark;
    }

    public String getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(String searchNum) {
        this.searchNum = searchNum;
    }

    public String getowner() {
        return owner;
    }

    public void setowner(String owner) {
        this.owner = owner;
    }

    public Integer getuseNum() {
        return usenum;
    }

    public void setuseNum(Integer usenum) {
        this.usenum = usenum;
    }


    /**
     * Get ISBN
     *
     * @return ISBN
     **/

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    /**
     * Get bookName
     *
     * @return bookName
     **/

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    /**
     * Get author
     *
     * @return author
     **/

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    /**
     * Get content
     *
     * @return content
     **/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getsummary() {
        return summary;
    }

    public void setsummary(String summary) {
        this.summary = summary;
    }


    /**
     * Get totalpage
     *
     * @return totalpage
     **/

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }


    /**
     * Get language
     *
     * @return language
     **/

    public String getLanguage() {
        return languagea;
    }

    public void setLanguage(String language) {
        this.languagea = language;
    }


    /**
     * Get publishtime
     *
     * @return publishtime
     **/

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }


    /**
     * Get updatetime
     *
     * @return updatetime
     **/

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }


    /**
     * Get typeName
     *
     * @return typeName
     **/

    public String gettypeName() {
        return typeName;
    }

    public void settypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getpublishPlace() {
        return publishplace;
    }

    public void setpublishPlace(String publishplace) {
        this.publishplace = publishplace;
    }

    public String getpicture() {
        return picture;
    }

    public void setpicture(String picture) {
        this.picture = picture;
    }

    public String getorigin() {
        return origin;
    }

    public void setorigin(String origin) {
        this.origin = origin;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    private int bookNum = 0;

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    private String TypeId=null;


}