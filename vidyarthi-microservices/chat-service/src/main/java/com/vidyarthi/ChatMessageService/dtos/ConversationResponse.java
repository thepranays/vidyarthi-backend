package com.vidyarthi.ChatMessageService.dtos;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    private String convo_id;
    private String buyer_uid;
    private String seller_uid;
    private String lastMsg;
    private String lastMsg_uid;
    private String product_id;
    private Timestamp createdAt;
}
