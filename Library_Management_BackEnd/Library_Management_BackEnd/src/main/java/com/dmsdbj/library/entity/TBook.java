package com.dmsdbj.library.entity;

import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputIntConfig;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "t_book")

//根据bookid获得tbook单表信息-张婷-2017年11月4日10:33:47
@NamedQueries({@NamedQuery(name="TBook.getBook",query="SELECT c FROM TBook c WHERE c.id=:bookId and c.isDelete=0"),
    @NamedQuery(name="TBook.getAllBook",query="SELECT c FROM TBook c WHERE  c.isDelete=0"),
    @NamedQuery(name="TBook.getAllBooktype",query="SELECT c FROM TBookAndType c WHERE   c.isDelete=0"),

        @NamedQuery(name="TBook.getAllBookByTypeId",query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id) FROM TBook c ,TBookBasic b " +
                "WHERE c.basicId=b.id and c.typeId =:typeId and b.location =:location and c.isDelete=0"),
    @NamedQuery(name="TBook.getBookbasicId",query="SELECT c FROM TBookBasic c WHERE c.isbn=:ISBN and c.isDelete=0"),
	//query="SELECT o FROM TBook o WHERE o.id=:bookId and o.isDelete=0"),
               @NamedQuery(name="TBook.getBookTypeId",query="SELECT a FROM TBook a WHERE a.id=:BookId and a.isDelete=0"),
	       //查询所有图书信息-田成荣-2017-11-4 10:34:35
        @NamedQuery(name="TBook.findAllBook", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator,b.location) FROM TBook c left join TBookAndType t on t.isDelete=0 and c.typeId=t.id left join TBookBasic b on b.isDelete=0 and b.id=c.basicId left join TWarehuse w on w.isDelete=0 and w.isbn=b.isbn and c.isDelete=0" ),

        //分页查询书籍信息 对应信息管理页面
        @NamedQuery(name="TBook.findAllBookByPagination", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(b.id,b.isbn,b.name,b.author,b.content,b.summary,b.languagea,b" +
                ".updateTime,b.remark,w.useNum,w.wareCount,b.publishPlace,b.bid,b.primCost,b" +
                ".rank,b.sellPrice,b.location) FROM TBookBasic b, TWarehuse w WHERE w.isbn=b.isbn and w.location=b.location and b.isDelete=0 and w.isDelete=0 " ),
        //分页查询书籍信息返回basic表的ID
        @NamedQuery(name="TBook.findAllBookByPaginationSum", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(b" +
                ".id,c" +
                ".searchNum,b.isbn,b.name,b.author,b.content,b.summary,b.totalPage,b.languagea,b.publishTime,b" +
                ".updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b" +
                ".rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w,TBookAndType t WHERE b.id=c" +
                ".basicId and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and " +
                "t.isDelete=0 group by w.isbn"),


        @NamedQuery(name="TBook.getBookInfo", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.id=c.basicId and t.name=:bookType and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0"),
	       //根据图书类型id查找图书-田成荣-2017-11-4 10:35:23
               @NamedQuery(name="TBook.getBookByTypeId", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.id=c.basicId and t.id=:bookType and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0"),
        @NamedQuery(name="TBook.getBookByTypeIdGroupByIsbn", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(" +
                "c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name," +
                "c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) " +
                "FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.id=c.basicId and t.id=:bookType and w.isbn=b.isbn" +
                " and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0 group by b.isbn"),

        @NamedQuery(name="TBook.getBooktype", query="SELECT c FROM TBookAndType c WHERE c.isDelete=0"),
	       @NamedQuery(name="TBook.deleteBook",query="UPDATE TBook b SET b.isDelete=1 WHERE  b.id=:bookId"),
               @NamedQuery(name="TBook.getBookBySearchId", query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.id=c.basicId and c.searchNum=:searchNum and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0"),

        @NamedQuery(name = "TBook.findBook",query="SELECT o FROM TBook o WHERE o.id in :bookIdList and o.isDelete=0"),
	//根据isbn获得索书号 getSearchSumByISBN-张婷-2017-11-4 10:37:51
	 @NamedQuery(name = "TBook.getSearchSumByISBN",query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.id=c.basicId and w.isbn=b.isbn and b.isbn =:isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0 order by c.searchNum desc"),
        //获得最热搜索-张婷-2017-11-12 21:26:03
        @NamedQuery(name = "TBook.getHotBook",query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t ,TBorrowing tb WHERE b.id=c.basicId and w.isbn=b.isbn and b.isbn =:isbn and c.typeId=t.id and tb.id=c.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0 order by c.searchNum desc"),

})
@ExcelModelConfig
public class TBook extends AbstractAuditingEntity implements Serializable{

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "ID", table = "t_book", nullable = true, length = 22)
    @Basic(optional = false)

    private String id;

    @Column(name = "basic_id", table = "t_book", nullable = true, length = 22)
    @Basic(optional = false)
    @InputTextConfig(nullAble=true)
    @Lang(value="图书基本ID")
    private String basicId;
    
    @Column(name = "owner", table = "t_book", nullable = true, length = 50)
    @Basic(optional = false)
     @InputTextConfig(nullAble=true)
    @Lang(value="所属者")
    private String owner;
	
	@Column(name = "book_shelf_id", table = "t_book", nullable = true, length = 22)
    @Basic(optional = false)
    @InputTextConfig(nullAble=true)
    @Lang(value="书架主键")
    private String bookshelfId;

    @Column(name = "origin", table = "t_book", nullable = true, length = 50)
    @Basic(optional = false)
     @InputTextConfig(nullAble=true)
    @Lang(value="来源")
    private String origin;

    @Column(name = "search_num", table = "t_book", nullable = true, length = 255)
    @Basic(optional = false)
     @InputTextConfig(nullAble=true)
    @Lang(value="索书号")
    private String searchNum;

    @Column(name = "status", table = "t_book", nullable = true)
    @Basic(optional = false)
     @InputIntConfig(nullAble=true)
    @Lang(value="状态")
    private Integer status;

    @Column(name = "type_id", table = "t_book", nullable = true, length = 22)
    @Basic(optional = false)
     @InputTextConfig(nullAble=true)
    @Lang(value="图书分类ID")
    private String typeId;

    @Column(name = "remark", table = "t_book")
    @Basic(optional = false)
     @InputTextConfig(nullAble=true)
    @Lang(value="备注")
    private String remark;


    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Column(name = "publish_time", table = "t_book", nullable = true, length = 22)
    @Basic(optional = false)
    @InputTextConfig(nullAble=true)
    @Lang(value="出版时间")
    private String publishTime;


    @Column(name = "total_pages", table = "t_book", nullable = true, length = 22)
    @Basic(optional = false)
    @InputTextConfig(nullAble=true)
    @Lang(value="总页数")
    private Integer totalPage;


    public String getBasicId() {
        return this.basicId;
    }

    public void setBasicId(String basicId) {
        this.basicId = basicId;
    }
    
	public String getBookShelfId() {
        return this.bookshelfId;
    }

    public void setBookShelfId(String bookshelfId) {
        this.bookshelfId = bookshelfId;
    }
	
		
    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSearchNum() {
        return this.searchNum;
    }

    public void setSearchNum(String searchNum) {
        this.searchNum = searchNum;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    
      public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    
    
   
}
