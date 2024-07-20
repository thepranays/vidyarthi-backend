package com.vidyarthi.ChatMessageService.controllers;

import com.vidyarthi.ChatMessageService.dtos.MessageResponse;
import com.vidyarthi.ChatMessageService.models.Message;
import com.vidyarthi.ChatMessageService.services.MessageService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/message")
public class MessageRestController {
    @Autowired
    private final MessageService messageService;

    @GetMapping("/get")
    public List<MessageResponse> getMessagesByConvoId(@RequestParam(value="convo_id") final String convoId){
        return messageService.getMessagesByConvoId(convoId);
    }

    
}
