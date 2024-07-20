package com.vidyarthi.ChatMessageService.services;

import com.vidyarthi.ChatMessageService.dtos.MessageRequest;
import com.vidyarthi.ChatMessageService.dtos.MessageResponse;
import com.vidyarthi.ChatMessageService.models.Message;
import com.vidyarthi.ChatMessageService.repos.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;

    public void saveMessage(final MessageRequest msgReq){
        Message msg=Message.builder()
                        .msg(msgReq.getMsg())
                        .recipientUid(msgReq.getRecipient_uid())
                        .senderUid(msgReq.getSender_uid())
                        .createdAt(msgReq.getCreatedAt())
                        .msgId(msgReq.getMsg_id())
                        .convoId(msgReq.getConvo_id())
                        .build();
        messageRepository.save(msg);
    }

    public List<MessageResponse> getMessagesByConvoId(final String convo_id){
       return messageRepository.findByConvoId(convo_id).stream().map((msg)->mapMessageToMessageResponse(msg)).toList();
    }
    public MessageResponse mapMessageToMessageResponse(final Message message){
        return MessageResponse.builder()
                .createdAt(message.getCreatedAt())
                .convo_id(message.getConvoId())
                .msg(message.getMsg())
                .msg_id(message.getMsgId())
                .recipient_uid(message.getRecipientUid())
                .sender_uid(message.getSenderUid())
                .build();
    }
}
