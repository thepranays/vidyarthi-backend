package com.vidyarthi.ChatMessageService.controllers;

import com.vidyarthi.ChatMessageService.dtos.MessageRequest;
import com.vidyarthi.ChatMessageService.models.Message;
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



    @MessageMapping("/chat")
    public void processAndRelayMessage(@Payload final MessageRequest message){

        System.out.println(message.toString());
        
        //save message in db
        messageService.saveMessage(message);

        //sends message to /user/queue/messages-{Recipient_uid}
        //refer:https://docs.spring.io/spring-framework/reference/web/websocket/stomp/user-destination.html
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient_uid(),"/queue/messages",message);
    }

}
