package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_notes")
public class TNotes extends AbstractAuditingEntity implements Serializable {


    @Column(name = "content", table = "t_notes", nullable = false)
    @Basic(optional = false)
    private String content;

    @Column(name = "isbn", table = "t_notes", nullable = false, length = 13)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "user_id", table = "t_notes", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    @Column(name = "is_public", table = "t_notes", nullable = false)
    @Basic(optional = false)
    private short isPublic;

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

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public short getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(short isPublic) {
        this.isPublic = isPublic;
    }

}