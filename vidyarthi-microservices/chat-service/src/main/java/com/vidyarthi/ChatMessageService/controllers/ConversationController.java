package com.vidyarthi.ChatMessageService.controllers;

import com.vidyarthi.ChatMessageService.dtos.ConversationRequest;
import com.vidyarthi.ChatMessageService.dtos.ConversationResponse;
import com.vidyarthi.ChatMessageService.models.Conversation;
import com.vidyarthi.ChatMessageService.services.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {

    @Autowired
    private final ConversationService conversationService;


    //Create Conversation using seller,buyer uid and product id
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createConversation(@RequestBody final ConversationRequest conversationRequest){

        conversationService.createConversation(conversationRequest);
    }


    //Endpoint to fetch buying conversations of the user
    @GetMapping("/get/buying/{uid}") //TODO:secure with owner level authorization layer
    @ResponseStatus(HttpStatus.OK)
    public List<ConversationResponse> getBuyingConversationsOfUser(@PathVariable(value="uid") final String uid){
        return conversationService.getBuyingConversationsOfUser(uid);
    }

    //Endpoint to fetch selling conversations of the user who is seller
    @GetMapping("/get/selling/{uid}") //TODO:secure with owner level authorization layer
    @ResponseStatus(HttpStatus.OK)
    public List<ConversationResponse> getSellingConversationsOfUser(@PathVariable(value="uid") final String uid){
        return conversationService.getSellingConversationsOfUser(uid);
    }
    @GetMapping("/get/{convo_id}")
    @ResponseStatus(HttpStatus.OK)
    public ConversationResponse getConversationByConvoId(@PathVariable(value="convo_id")final String convoId){

        return conversationService.getConversationByConvoId(convoId);
    }
    //Get conversation using unique combination of seller,buyer uid and product id
    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ConversationResponse getConversation(@RequestParam(name="product_id") final String product_id,@RequestParam(name="buyer_uid") final String buyer_uid,@RequestParam(name="seller_uid") final String seller_uid){ //@RequestParam:query parameter , by default required is true can be mapped using @RequestParam(name='id) final String userId)
      //  System.out.println("product_id:"+product_id+"buyerid:"+buyer_uid+"sellerid"+seller_uid);
        return conversationService.getConversation(buyer_uid,seller_uid,product_id);
    }
}
