package com.vidyarthi.NotificationService.controllers;

import com.vidyarthi.NotificationService.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;



@RestController
@Slf4j
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /*Server Sent Events */
    @CrossOrigin
    @GetMapping(path = "/event-product-created",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToProductCreatedEvent(){
        return notificationService.subscribeToProductCreatedEvent();
    }

}
