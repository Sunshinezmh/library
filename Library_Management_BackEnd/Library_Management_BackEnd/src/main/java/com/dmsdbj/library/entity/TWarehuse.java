package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_warehuse")
@NamedQueries({
//根据图书Isbn获得图书的可借用量和入库数量
 @NamedQuery(name="TWarehuse.getUseCount",query="SELECT o FROM TWarehuse o WHERE o.isbn =:isbn and o.isDelete=0"),
//根据图书Isbn和location获得图书的可借用量和入库数量
 @NamedQuery(name="TWarehuse.getUseCountBylocation",query="SELECT o FROM TWarehuse o WHERE o.isbn =:isbn and o.location=:location and o.isDelete=0")
})
public class TWarehuse extends AbstractAuditingEntity implements Serializable{

    @Column(name = "author", table = "t_warehuse", nullable = false, length = 255)
    @Basic(optional = false)
    private String author;

    @Column(name = "isbn", table = "t_warehuse", nullable = false, length = 13)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "shelf_id", table = "t_warehuse", nullable = false, length = 22)
    @Basic(optional = false)
    private String shelfId;

    @Column(name = "origin", table = "t_warehuse", nullable = false, length = 50)
    @Basic(optional = false)
    private String origin;

    @Column(name = "use_count", table = "t_warehuse", nullable = false)
    @Basic(optional = false)
    private Integer useNum;

    @Column(name = "ware_count", table = "t_warehuse", nullable = false)
    @Basic(optional = false)
    private Integer wareCount;

    @Column(name = "remark", table = "t_warehuse")
    @Basic
    private String remark;

//    添加location字段-张明慧--2018年4月26日21:02:31
    @Column(name = "location", table = "t_warehuse", nullable = true)
    @Basic
    private String location;

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
 
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getShelfId() {
        return this.shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getuseNum() {
        return this.useNum;
    }

    public void setuseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public Integer getWareCount() {
        return this.wareCount;
    }

    public void setWareCount(Integer wareCount) {
        this.wareCount = wareCount;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

   

}