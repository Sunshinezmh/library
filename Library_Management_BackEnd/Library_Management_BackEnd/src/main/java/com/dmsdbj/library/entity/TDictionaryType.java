package com.dmsdbj.library.entity;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "t_dictionary_type")
public class TDictionaryType extends AbstractAuditingEntity implements Serializable {


    @Column(name = "content", table = "t_dictionary_type")
    @Basic
    private String content;

    @Column(name = "type_name", table = "t_dictionary_type")
    @Basic
    private String typeName;

   

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

   
}