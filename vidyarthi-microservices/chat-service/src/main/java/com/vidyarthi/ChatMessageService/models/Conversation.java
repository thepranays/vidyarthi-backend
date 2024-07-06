package com.vidyarthi.ChatMessageService.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversations",uniqueConstraints = { @UniqueConstraint(columnNames =
        { "buyerUid", "sellerUid","productId" })})
public class Conversation {
    @Id
    private String convoId;
    private String buyerUid;
    private String sellerUid;
    private String productId;
    private String lastMsg;
    private String lastMsgUid;
    private Timestamp createdAt;

}
