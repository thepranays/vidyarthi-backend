package com.vidyarthi.ChatMessageService.controllers;

import com.vidyarthi.ChatMessageService.dtos.MessageResponse;
import com.vidyarthi.ChatMessageService.models.Message;
import com.vidyarthi.ChatMessageService.services.MessageService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MessageRestController {
    @Autowired
    private final MessageService messageService;

    @GetMapping("/get/convo/{convo_id}")
    public List<MessageResponse> getMessagesByConvoId(@PathVariable(value="convo_id") final String convoId){
        return messageService.getMessagesByConvoId(convoId);
    }

    
}
