package com.vidyarthi.ChatMessageService.controllers;

import com.vidyarthi.ChatMessageService.dtos.MessageRequest;
import com.vidyarthi.ChatMessageService.models.Message;
import com.vidyarthi.ChatMessageService.services.ConversationService;
import com.vidyarthi.ChatMessageService.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/*Receive and broadcast messages to intended recipient */
@Controller
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate; //used for specific user (we used uid based uniqueness) messaging using queue

    @Autowired
    private final MessageService messageService;
    @Autowired
    private final ConversationService conversationService;



    @MessageMapping("/chat")
    public void processAndRelayMessage(@Payload final MessageRequest message){

        System.out.println(message.toString());
        //sends message to /user/queue/messages-{Recipient_uid}
        //refer:https://docs.spring.io/spring-framework/reference/web/websocket/stomp/user-destination.html
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient_uid(),"/queue/messages",message);

        //Save message in db
        //Has valid convo-id (conversation id),as frontend first creates conversation if it doesn't exist then send message
        messageService.saveMessage(message);

        //Update conversation last message and last message user id
        conversationService.updateLastMessageInConvo(message.getConvo_id(),message.getMsg(),message.getSender_uid());


    }

}
