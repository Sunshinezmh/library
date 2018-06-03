package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "t_message_template")

//何新生---2017-9-23
@NamedQueries({

        @NamedQuery(name="TMessageTemplate.queryContentBytemplateId",query="SELECT o FROM TMessageTemplate o WHERE o.id=:templateId ")

})

public class TMessageTemplate extends AbstractAuditingEntity implements Serializable{

  

    @Column(name = "content", table = "t_message_template", nullable = false)
    @Basic(optional = false)
    private String content;

    @Column(name = "message_type_id", table = "t_message_template", nullable = false, length = 22)
    @Basic(optional = false)
    private String messageTypeId;


    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageTypeId() {
        return this.messageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

}