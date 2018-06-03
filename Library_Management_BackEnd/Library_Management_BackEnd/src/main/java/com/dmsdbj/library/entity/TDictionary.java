package com.dmsdbj.library.entity;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 *
 * @author lyn
 */
@Entity
@Table(name = "t_dictionary")
public class TDictionary extends AbstractAuditingEntity implements Serializable {

    @Column(name = "content", table = "t_dictionary", nullable = false)
    @Basic(optional = false)
    private String content;

    @Column(name = "type_name", table = "t_dictionary", nullable = false, length = 50)
    @Basic(optional = false)
    private String typeName;

    @Column(name = "type_id", table = "t_dictionary", length = 22)
    @Basic
    private String typeId;

    @Column(name = "remark", table = "t_dictionary")
    @Basic
    private String remark;


    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

}