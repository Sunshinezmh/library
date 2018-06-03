package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Transient;

@Entity
@Table(name = "t_book_basic")
//根据作者查询
@NamedQueries({
        @NamedQuery(name="TBookBasic.findISBN",query="SELECT t FROM TBookBasic t WHERE t.name=:bookName and t.isDelete=0"),
//根据isbn获得书籍详情
@NamedQuery(name="TBookBasic.findBookDetailsByIsbn",query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.isbn=:isbn and w.isbn =b.isbn and c.typeId=t.id and b.isDelete=0 and c.isDelete=0 and w.isDelete=0 and t.isDelete=0"),

//根据isbnList查询书籍详情
 @NamedQuery(name="TBook.TCollectionBookByIsbnList",
                query="SELECT  new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator,b.location) "
                        + "FROM"
                        + " TBookBasic b,TBook c, TWarehuse w ,TBookAndType t "
                        + "WHERE"
                        + " b.id=c.basicId and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and  w.isDelete=0 and t.isDelete=0"),

        @NamedQuery(name="TBook.getBookInfoBybookId",query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) "
                + "FROM"
                + " TBookBasic b,TBook c, TWarehuse w ,TBookAndType t "
                + "WHERE"
                + " c.id =:bookId and b.id=c.basicId and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0  and b.isDelete=0 and w.isDelete=0 and t.isDelete=0"),

        //根据ISBN的一类书的信息---（万达使用）王华伟--2018年3月22日19:20:58
        @NamedQuery(name="getBookNameByISBN",query = "SELECT t FROM TBookBasic t WHERE t.isbn=:isbn and t.isDelete=0"),

        //添加图书的物理位置使用的方法--张明慧--2018-4-26 15:10:13
        @NamedQuery(name="TBookBasic.getBookbasicId",query="SELECT c FROM TBookBasic c WHERE c.isbn=:ISBN and c.isDelete=0"),
        //添加图书的物理位置使用的方法isbn--location
        @NamedQuery(name="TBookBasic.getBookbasicLocation",query="SELECT c FROM TBookBasic c WHERE c.isbn=:ISBN and c.location=:location and c.isDelete=0"),
       //查询所有书籍没有从网上抓取信息-王雪芬-2018年5月10日11:39:48
        @NamedQuery(name = "Getbookisbn",query = "SELECT o FROM TBookBasic o WHERE o.picture IS NULL or o.content IS NULL or o.summary IS NULL "),
       //查询所有没有图片的书籍名称-王雪芬-2018年5月15日10:16:06
        @NamedQuery(name = "getBookinfoName",query="SELECT o FROM TBookBasic o WHERE o.picture IS NULL"),
        @NamedQuery(name = "getBookinfopicture",query="SELECT o FROM TBookBasic o WHERE o.isbn=:isbn")


     })

public class TBookBasic extends AbstractAuditingEntity implements Serializable{



    @Column(name = "summary", table = "t_book_basic", nullable = true)
    @Basic(optional = false)
    private String summary;

    @Column(name = "author", table = "t_book_basic", nullable = true, length = 255)
    @Basic(optional = false)
    private String author;

    @Column(name = "bid", table = "t_book_basic", nullable = true, scale = 2, precision = 10)
    @Basic(optional = false)
    private Double bid;

    @Column(name = "content", table = "t_book_basic", nullable = true)
    @Basic(optional = false)
    private String content;

    @Column(name = "isbn", table = "t_book_basic", nullable = true, length = 13)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "languagea", table = "t_book_basic", nullable = true, length = 40)
    @Basic(optional = false)
    private String languagea;

    @Column(name = "name", table = "t_book_basic", nullable = true, length = 80)
    @Basic(optional = false)
    private String name;

    @Column(name = "picture", table = "t_book_basic", nullable = true, length = 255)
    @Basic(optional = false)
    private String picture;

    @Column(name = "prim_cost", table = "t_book_basic", nullable = true, scale = 2, precision = 10)
    @Basic(optional = false)
    private Double primCost;

    @Column(name = "publish_place", table = "t_book_basic", nullable = true, length = 255)
    @Basic(optional = false)
    private String publishPlace;

    @Column(name = "publish_time", table = "t_book_basic", nullable = true)
    @Basic(optional = false)
    private String publishTime;

    @Column(name = "rank", table = "t_book_basic", nullable = true)
    @Basic(optional = false)
    private Integer rank;

    @Column(name = "remark", table = "t_book_basic")
    @Basic
    private String remark;

    @Column(name = "sell_price", table = "t_book_basic", nullable = true, scale = 2, precision = 10)
    @Basic(optional = false)
    private Double sellPrice;

    @Column(name = "total_page", table = "t_book_basic", nullable = true)
    @Basic(optional = false)
    private Integer totalPage;

//--TBookBasic实体中的location
    @Column(name = "location", table = "t_book_basic", nullable = true)
//    @Basic(optional = false)
    private String location;

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getsummary() {
        return this.summary;
    }

    public void setsummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getBid() {
        return this.bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguagea() {
        return this.languagea;
    }

    public void setLanguagea(String languagea) {
        this.languagea = languagea;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrimCost() {
        return this.primCost;
    }

    public void setPrimCost(Double primCost) {
        this.primCost = primCost;
    }

    public String getPublishPlace() {
        return this.publishPlace;
    }

    public void setPublishPlace(String publishPlace) {
        this.publishPlace = publishPlace;
    }

    public String getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
     
    
}
  