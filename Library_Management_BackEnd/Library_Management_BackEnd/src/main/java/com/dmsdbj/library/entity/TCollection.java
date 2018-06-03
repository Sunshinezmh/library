package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "t_collection")
        //根据userid查询isbn的集合
        @NamedQueries({@NamedQuery(name="TCollection.getCollectionsisbn",query="SELECT o.isbn FROM TCollection o WHERE o.userId=:UserId and o.isDelete=0 "),

        //根据bookId查询书的信息。（4表练查）
        @NamedQuery(name="TBook.TBookid",
                query="SELECT new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator)"
                + " FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t  WHERE"
                + " c.id=:bookid and b.id=c.basicId and w.isbn=b.isbn and c.typeId=t.id and c.isDelete=0 and b.isDelete=0 and w.isDelete=0 and t.isDelete=0"),
        // 根据userid查询书架中书籍的详细
         @NamedQuery(name= "TCollection.getCollectionBookInfo",
                 query = "SELECT new com.dmsdbj.library.viewmodel.BookViewModel(b.id,b.name,b.isbn,b.picture,b.location,c.id)" +
                         " from TBookBasic b,TCollection c where " +
                         "c.userId=:userId and b.location=c.location and b.isbn =c.isbn and b.isDelete=0 and c.isDelete=0"),

                //根据isbn和userID查Collection的实体。--添加location条件--张明慧--2018年5月1日18:24:07
        @NamedQuery(name="TCollection.TCollectionIsbnUserId",query="SELECT o  FROM TCollection o  WHERE o.isbn=:isbn and o.userId=:userId and o.location=:location and o.isDelete=0"),

        //根据id查Collection的实体。
        @NamedQuery(name="TCollection.getCollectionInfo",query="SELECT o FROM TCollection o  WHERE o.id=:id and o.isDelete=0"),

        //查询重复
        @NamedQuery(name="TCollection.getCollections",query="SELECT o FROM TCollection o WHERE o.userId=:UserId and o.isDelete=0 "),

      })
public class TCollection extends AbstractAuditingEntity implements Serializable{
    @Column(name = "isbn", table = "t_collection", nullable = false, length = 13)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "user_id", table = "t_collection", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    @Column(name = "remark", table = "t_collection")
    @Basic
    private String remark;



    //--TCollection实体中的location--张明慧--2018年5月1日18:20:31
    @Column(name = "location", table = "t_collection", nullable = true)
    //    @Basic(optional = false)
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}