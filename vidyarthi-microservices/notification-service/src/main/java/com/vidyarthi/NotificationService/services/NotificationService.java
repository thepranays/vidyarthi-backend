package com.vidyarthi.NotificationService.services;

import com.vidyarthi.NotificationService.constants.Constants;
import com.vidyarthi.NotificationService.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.io.IOException;


import java.util.*;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

//TODO: Find whether using sink and sink flux is better practice or not (whether it supports multithreading)
@Service
@Slf4j //for logging
public class NotificationService {
    private final List<SseEmitter> emitters=new CopyOnWriteArrayList<>();


    //REST API services
    public SseEmitter subscribeToProductCreatedEvent(){
        //Create sse emitter for current subscribed client and keep the sse connection open until client disconnects
        SseEmitter currClientSseEmitter=new SseEmitter();
        emitters.add(currClientSseEmitter);

        // Remove emitter from emitters list on client disconnect
        currClientSseEmitter.onCompletion(()-> {
            synchronized (emitters) {
                emitters.remove(currClientSseEmitter);

            }
        });
        currClientSseEmitter.onError((e)->{
            synchronized (emitters){
                emitters.remove(currClientSseEmitter);
                        }
        });
        currClientSseEmitter.onTimeout(()->{
            synchronized (emitters){
                emitters.remove(currClientSseEmitter);
            }
        });


        return currClientSseEmitter;

    }


    /*KAFKA Services*/
    //Kafka listeners
    @KafkaListener(topics = Constants.KAFKA_TOPIC_PRODUCT_CREATED)
    public void handleProductCreatedNotification(ProductCreatedEvent productCreatedEvent){

        //o(N)
        //send message to client through each SSE emitter
        synchronized (emitters){
            for(SseEmitter sseEmitter:this.emitters){
                try{

                    sseEmitter.send(
                            SseEmitter.event()
                                    .id(UUID.randomUUID().toString())
                                    .data(productCreatedEvent.getType().equals("sell") ? "Want to buy "+productCreatedEvent.getTitle()+" ?":"Anyone have "+productCreatedEvent.getTitle()+" ?")
                                    .name(Constants.EVENT_PRODUCT_CREATED)
                                    .reconnectTime(2000) //time after which client should retry after disconnection from server or interruption in n/w ,helps incase of transient issues
                    );
                }catch(IOException e){
                    //System.out.println("Removed disconnected sse emitter");
                    emitters.remove(sseEmitter);

                }
            }
        }

        log.info("Product listed:"+productCreatedEvent.getType()+"|"+productCreatedEvent.getTitle()+" by "+productCreatedEvent.getUid());
    }
}







//
//
//package com.vidyarthi.NotificationService.services;
//
//import com.vidyarthi.NotificationService.constants.Constants;
//import com.vidyarthi.NotificationService.events.ProductCreatedEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.codec.ServerSentEvent;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Sinks;
//
//import java.time.Duration;
//import java.util.UUID;
//
//@Service
//@Slf4j
//public class NotificationService {
//    private final Sinks.Many<String> sink;
//
//    public NotificationService() {
//        this.sink = Sinks.many().multicast().onBackpressureBuffer();
//    }
//
//    // REST API services
//    public Flux<ServerSentEvent<String>> subscribeToProductCreatedEvent() {
//        return sink.asFlux().map(str ->
//                ServerSentEvent.<String>builder()
//                        .id(UUID.randomUUID().toString())
//                        .event(Constants.EVENT_PRODUCT_CREATED)
//                        .data(str)
//                        .retry(Duration.ofMillis(2000))
//                        .build()
//        ).doOnCancel(() -> log.info("Client disconnected"));
//    }
//
//    /* KAFKA Services */
//    // Kafka listeners
//    @KafkaListener(topics = Constants.KAFKA_TOPIC_PRODUCT_CREATED)
//    public void handleProductCreatedNotification(ProductCreatedEvent productCreatedEvent) {
//        sink.tryEmitNext(productCreatedEvent.getTitle());
//        log.info("Product listed: " + productCreatedEvent.getType() + "|" + productCreatedEvent.getTitle() + " by " + productCreatedEvent.getUid());
//    }
//}
//





