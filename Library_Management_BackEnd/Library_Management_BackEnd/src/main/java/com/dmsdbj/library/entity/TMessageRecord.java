package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_message_record")

//何新生--2017-9-23
@NamedQueries({

//        @NamedQuery(name="TMessageRecord.getMessage",query="select B  from TMessageRecord A \n" +
//                "   LEFT JOIN TMessageTemplate B ON A.messageTemplateId=B.id\n" +
//                "   LEFT JOIN TUser D ON D.id=A.userId \n" +
//                "WHERE  A.userId=:userId ")
        @NamedQuery(name = "TMessageRecord.getMessage",query = "select A from TMessageRecord A where  A.userId=:userId")

})

public class TMessageRecord extends AbstractAuditingEntity implements Serializable{


    @Column(name = "user_id", table = "t_message_record", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    @Column(name = "send_time", table = "t_message_record", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    @Column(name = "sender", table = "t_message_record", nullable = false, length = 22)
    @Basic(optional = false)
    private String sender;

    @Column(name = "message_template_id", table = "t_message_record", nullable = false, length = 22)
    @Basic(optional = false)
    private String messageTemplateId;
    

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageTemplateId() {
        return this.messageTemplateId;
    }

    public void setMessageTemplateId(String messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }

}