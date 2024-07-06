package com.vidyarthi.ChatMessageService.dtos;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationRequest {
    private String buyer_uid;
    private String seller_uid;
    private String product_id;

}
