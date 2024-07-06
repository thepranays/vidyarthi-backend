package com.vidyarthi.ChatMessageService.models;

/* Message model -Vidyarthi*/


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    private String msgId;
    private String convoId;
    private String msg;
    private String senderUid;
    private String recipientUid;
    private Timestamp createdAt;
    //private Timestamp updatedAt;//TODO: add updatedAt after implementing update message feature



    @Override
    public String toString(){
        return "msgId:"+msgId+
                " conv_id:"+convoId+
        " msg:"+ msg+
        " sender_uid:"+ senderUid+
        " recipient_uid:"+recipientUid+
        " timestamp:"+ createdAt;
    }


}
