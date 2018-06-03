package com.dmsdbj.library.entity;

import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_borrowing")
@NamedQueries(value = {

        @NamedQuery(name = "TBorrowing.getBookQ", query = "SELECT o FROM TBorrowing o WHERE o.userId =:userId and o" +
                ".isDelete=0 order by o.updateTime desc "),
        @NamedQuery(name = "TBorrowing.getBorrowingByUserId", query = "\n" +
                "SELECT new com.dmsdbj.library.entity.TBorrowingModel1(br.bookId,br.id,ts.code,ts.name,ts" +
                ".professionName,ts" +
                ".classesName,tbb.author,tbb.location,tbb.name,tbb.content,br.borrowTime,br.endDate,br.realDate,br.isOverdue,br" +
                ".renew,br.borrowStatus,bo.searchNum,tbb.picture) FROM TStudent ts,TBook bo,TBookBasic tbb,TBorrowing" +
                " br where ts.code = br.userId and br.bookId=bo.id and tbb.id=bo.basicId and ts.isDelete =0 and tbb" +
                ".isDelete =0 and br.isDelete = 0 and br.userId =:userId"),

        @NamedQuery(name = "TBorrowing.getBorrowingByStatus", query =
                "SELECT new com.dmsdbj.library.entity.TBorrowingModel1" +
                        "(br.bookId,br.id,ts.code,ts.name,ts.professionName,ts.classesName,tbb.author,tbb.location,tbb.name,tbb.content,br.borrowTime,br.endDate,br.realDate,br.isOverdue," +
                        "br.renew,br.borrowStatus,bo.searchNum,tbb.picture) FROM TStudent ts,TBook bo,TBookBasic tbb,TBorrowing br " +
                        "where ts.code = br.userId and br.bookId=bo.id and tbb.id=bo.basicId and br.isDelete = 0 and br.borrowStatus =:status and br" +
                        ".userId =:userId "),
        //PC查询所有借阅借阅记录--2018年3月28日15:47:43--王华伟
        @NamedQuery(name = "TBorrowing.getAllBorrowing", query = "SELECT new com.dmsdbj.library.viewmodel.BookViewModel" +
                "(ts.id,br.id,ts.name,tbb.author,tbb.isbn,tbb.location,tbb.name,tbb" +
                ".content,br.borrowTime,br.endDate,br.realDate,br.isOverdue,br.renew,br.borrowStatus," +
                "tbb.picture)FROM TUser ts,TBookBasic tbb,TBorrowing br where ts.id = br.userId and br.isbn = tbb.isbn and br.isDelete = 0 "),
//查询所有未还的借阅记录--2018年4月24日10:03:01--王雅静
        @NamedQuery(name = "TBorrowing.getAllBorrowedRecord", query = "SELECT new com.dmsdbj.library.viewmodel.BookViewModel" +
                "(ts.name,tbb.author,tbb.isbn,tbb.location,tbb.name,tbb" +
                ".content,br.borrowTime,br.endDate,br.realDate,br.isOverdue,br.renew,br.borrowStatus," +
                "tbb.picture) FROM TUser ts,TBookBasic tbb,TBorrowing br where ts.id = br.userId and br.isbn = tbb.isbn and br.isDelete = 0 and br.borrowStatus <> 2 "),

        @NamedQuery(name = "getBorrowing", query = "select o from TBorrowing o where o.userId =:code and o.bookId =:bookId and o.isDelete=0 and o.borrowStatus=1"),

        //最热搜索-张婷-2017-11-13 10:22:06

//        @NamedQuery(name = "getHotBook", query = "SELECT new com.dmsdbj.library.viewmodel.hotBookViewModel(b.name,b.isbn as isbn,c
// ount(c.id) as count) FROM TBookBasic b,TBook c, TBorrowing o WHERE b.id=c.basicId and o.bookid=c.id and o.isDelete=0 group by isbn order by count desc limit 5"),
        //获取用户正在借阅数
        @NamedQuery(name = "getBorrowingCount", query = "select count(ts) from TBorrowing ts where ts.userId=:code and ts.isDelete=0 and ts.borrowStatus=1"),
        @NamedQuery(name = "fingBorrwingByBookId",query="select ts from TBorrowing ts where ts.bookId=:bookId and ts.isDelete=0 and ts.borrowStatus=1"),

        //根据isbn和userID去borrowing表中查询借阅记录（万达这边使用----王华伟---2018年3月22日14:52:47）--王华伟加location字段--2018年5月3日09:40:41
        @NamedQuery(name = "findBorrowingByISBNAndCode",query = "select o from TBorrowing o where o.userId=:userID and o.isbn =:isbn and o.location =:location and o.borrowStatus<>2 and o.isDelete=0"),

        //根据userID和借阅状态在Tborrowing表中查询出对应的记录（万达使用）--王华伟--2018年3月22日18:57:26
        @NamedQuery(name = "getISBNByStatusAndCode",query = "select o from TBorrowing o  where o.userId=:userId and o.borrowStatus=:status  order by o.endDate desc "),

        //查询所有借阅记录(根据借阅时间进行降序排序)
        @NamedQuery(name = "findAllBorrowingStatusByCodeAndStatus",query = "SELECT new com.dmsdbj.library.viewmodel.BookViewModel(" +
                "br.id,tbb.picture,tbb.name,br.isbn,tbb.location,tbb.author,tbb.content,br.realDate,br.borrowTime,br.endDate) " +
                "FROM TBookBasic tbb,TBorrowing br " +
                "where br.userId =:userId and br.isbn=tbb.isbn and br.isbn=:isbn and br.borrowStatus=:status and br.isDelete=0 ORDER BY br.borrowTime DESC"),

        //查询状态不等于2的借阅记录
        //查询状态不等于2的借阅记录---王华伟添加了一个
        @NamedQuery(name = "findBorrowingStatusByCodeAndStatus",query = "SELECT new com.dmsdbj.library.viewmodel.BookViewModel" +
                "(br.id,tbb.picture,tbb.name,br.isbn,tbb.location,tbb.author,tbb.content,br.realDate,br.borrowTime,br.endDate) " +
                "FROM TBookBasic tbb,TBorrowing br " +
                "where br.userId =:userId and br.isbn=tbb.isbn and br.isbn=:isbn and br.borrowStatus<>:status and br.location =:location and br.isDelete=0"),

        //查询状态不等于2，并且还书时间大于等于当前时间--王华伟--2018年4月4日15:50:40
        @NamedQuery(name = "ReturnBookRemind",query = "SELECT new com.dmsdbj.library.viewmodel.BookViewModel" +
                "(br.id,tbb.picture,tbb.name,br.isbn,tbb.location,tbb.author,tbb.content,br.realDate,br.borrowTime,br.endDate) " +
                "FROM TBookBasic tbb,TBorrowing br " +
                "where br.userId =:userId and br.isbn=tbb.isbn and br.endDate >= current_date and br.borrowStatus<>2 and br.isDelete=0 ORDER BY br.borrowTime DESC"),

        //根据状态查询用户借阅记录--王华伟--2018年3月31日10:22:10
        @NamedQuery(name = "getUserBorrowingNumber",query = "select o from TBorrowing o where o.borrowStatus <>:status and o.userId =:code "),

        //根据isbn和userid查询borrowing是否有这条数据--王华伟--2018年3月31日19:05:08
        @NamedQuery(name = "getBorrowingByISBNAndCode",query = "select o from TBorrowing o where o.isbn =:isbn and o.userId =:id"),
//        @NamedQuery(name = "getBorrowingByISBNAndCode",query = "select o from TBorrowing o where o.isbn =:isbn and o.userId =:code"),


        @NamedQuery(name = "GotHotBook",query ="select o from TBorrowing o where o.isDelete = 0" ),
        @NamedQuery(name = "doTime",query = "select o from TBorrowing o where o.realDate = Null"),
		@NamedQuery(name = "BorrowingTime",query = "select o from TBorrowing o where o.isbn =:isbn and o.userId =:code and o.isOverdue = 0"),
        @NamedQuery(name = "addAppBorrowingTime",query ="select o from TBorrowing o where o.isbn =:isbn and o.userId =:code and o.isDelete = 0 and o.isOverdue = 0" ) 

})
public class TBorrowing extends AbstractAuditingEntity implements Serializable {
    @Column(name = "user_id", table = "t_borrowing", length = 22)
    @Basic
    private String userId;

    @Column(name = "book_id", table = "t_borrowing", length = 22)
    @Basic
    private String bookId;


    @Column(name = "borrow_time", table = "t_borrowing")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowTime;

    @Column(name = "end_date", table = "t_borrowing")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "compensation", table = "t_borrowing", scale = 2, precision = 2)
    @Basic
    private Double compensation;

    @Column(name = "damage_id", table = "t_borrowing", length = 22)
    @Basic
    private String damageId;

    @Column(name = "is_overdue", table = "t_borrowing")
    @Basic
    private int isOverdue;

    public int getIsOverdue() {
        return isOverdue;
    }

    @Column(name = "overdue_money", table = "t_borrowing", scale = 2, precision = 2)
    @Basic
    private Double overdueMoney;

    @Column(name = "real_date", table = "t_borrowing")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date realDate;

    @Column(name = "renew", table = "t_borrowing")
    @Basic
    private int renew;

    @Column(name = "remark", table = "t_borrowing")
    @Basic
    private String remark;

    @Column(name = "borrow_status", table = "t_borrowing")
    @Basic
    private int borrowStatus;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Column(name = "isbn",table = "t_borrowing",nullable = true, length = 50)
    @Basic(optional = false)
    @InputTextConfig(nullAble=true)
    @Lang(value="ISBN")
    private String isbn;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "location",table = "t_borrowing")
    @Basic
    private String location;





    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowTime() {
        return this.borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getCompensation() {
        return this.compensation;
    }

    public void setCompensation(Double compensation) {
        this.compensation = compensation;
    }

    public String getDamageId() {
        return this.damageId;
    }

    public void setDamageId(String damageId) {
        this.damageId = damageId;
    }

    public int isIsOverdue() {
        return this.isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public Double getOverdueMoney() {
        return this.overdueMoney;
    }

    public void setOverdueMoney(Double overdueMoney) {
        this.overdueMoney = overdueMoney;
    }

    public Date getRealDate() {
        return this.realDate;
    }

    public void setRealDate(Date realDate) {
        this.realDate = realDate;
    }

    public int getRenew() {
        return this.renew;
    }

    public void setRenew(int renew) {
        this.renew = renew;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getBorrowStatus() {
        return this.borrowStatus;
    }

    public void setBorrowStatus(int borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

}
