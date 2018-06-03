package com.dmsdbj.library.entity;

import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_book_and_type")
@NamedQueries({
        @NamedQuery(name="TBookAndType.findAllbookType",query = "select o from TBookAndType o where o.isDelete=0"),
        @NamedQuery(name="TBookAndType.findBookTypeByName",query="select o from TBookAndType o where o.name like :BookName and o.isDelete=0" ),
        @NamedQuery(name="TBookAndType.findParentBookType",query="select o from TBookAndType o where o.pId =:pid and o.isDelete=0"  ),
        @NamedQuery(name="TBookAndType.findBookTypeBypId",query="select o from TBookAndType o where o.pId =:pid and o.isDelete=0"  ),
        @NamedQuery(name="TBookAndType.findBookTypeIdByName",query="select o from TBookAndType o where o.name =:typeName or o.id =:typeName and o.isDelete=0"),
        @NamedQuery(name="TBookAndType.findShelfBookType",query="select o from TBookAndType o where o.pId <>:pid and o.isDelete=0 order by o.name"  )
})
@ExcelModelConfig
public class TBookAndType extends AbstractAuditingEntity implements Serializable{


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "ID", table = "t_book_and_type", nullable = false, length = 22)
    private String id;
    @Column(name = "name", table = "t_book_and_type", nullable = false, length = 50)
    @Basic(optional = false)
    @Lang(value = "类别名称")
    private String name;

    @Column(name = "pId", table = "t_book_and_type", nullable = false, length = 22)
    @Basic(optional = false)
    private String pId;

    @Column(name = "remark", table = "t_book_and_type")
    @Basic
    @Lang(value = "备注")
    private String remark;

    @Column(name = "shelf_id", table = "t_book_and_type", nullable = false, length = 22)
    @Basic(optional = false)
    private String shelfId;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return this.pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShelfId() {
        return this.shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

}