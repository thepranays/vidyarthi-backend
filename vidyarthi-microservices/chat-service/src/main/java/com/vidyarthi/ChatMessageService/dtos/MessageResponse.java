package com.vidyarthi.ChatMessageService.dtos;

import lombok.*;

import java.sql.Timestamp;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
    private String msg_id;
    private String convo_id;
    private String msg;
    private String sender_uid;
    private String recipient_uid;
    private Timestamp createdAt;
}
