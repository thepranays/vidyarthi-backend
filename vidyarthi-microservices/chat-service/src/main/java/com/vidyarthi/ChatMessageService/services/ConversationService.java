package com.vidyarthi.ChatMessageService.services;


import com.vidyarthi.ChatMessageService.dtos.ConversationRequest;
import com.vidyarthi.ChatMessageService.dtos.ConversationResponse;
import com.vidyarthi.ChatMessageService.models.Conversation;
import com.vidyarthi.ChatMessageService.repos.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {
    @Autowired
    private final ConversationRepository conversationRepository;


    public void createConversation(ConversationRequest conversationRequest){
        Conversation newConv = Conversation.builder()
                .convoId(UUID.randomUUID().toString())
                .createdAt(Timestamp.from(Instant.now()))
                .sellerUid(conversationRequest.getSeller_uid())
                .buyerUid(conversationRequest.getBuyer_uid())
                .productId(conversationRequest.getProduct_id())
                .build();
        System.out.println("created convo:"+newConv.toString());
        conversationRepository.save(newConv);
    }

    //Return conversation response fetched by matching convo_id
    public ConversationResponse getConversationByConvoId(String convoId){
        Optional<Conversation> optionalConversation=conversationRepository.findById(convoId);
        if(optionalConversation.isPresent()){
            Conversation conversation=optionalConversation.get();
            return mapConversationToConversationResponse(conversation);
        }
        return null;
    }
    //Return conversation response fetched by matching buyer_uid, seller_uid, product_id
    public ConversationResponse getConversation(String buyer_uid,String seller_uid,String product_id){
        System.out.println("product_id:"+product_id+"buyerid:"+buyer_uid+"sellerid"+seller_uid);
        Optional<Conversation> optionalConversation = conversationRepository.findByBuyerUidAndSellerUidAndProductId(buyer_uid,seller_uid,product_id);
       // System.out.println(optionalConversation.isPresent());

        if(optionalConversation.isPresent()){
           // System.out.println(optionalConversation.get());
            Conversation conv=optionalConversation.get();
            System.out.println("fetched convo:"+conv.toString());
            return mapConversationToConversationResponse(conv);
        }
        return null;
    }

    //Update conversation by convo-id
    public void updateConversationByConvoId(String convoId,ConversationRequest conversationRequest){
        Optional<Conversation> optionalConversation=conversationRepository.findById(convoId);
        if(optionalConversation.isPresent()){
            Conversation conv=optionalConversation.get();
                conv.setConvoId(UUID.randomUUID().toString());
                conv.setCreatedAt((Timestamp.from(Instant.now())));
                conv.setSellerUid(conversationRequest.getSeller_uid());
                conv.setBuyerUid(conversationRequest.getBuyer_uid());
                conv.setProductId(conversationRequest.getProduct_id());
                conv.setLastMsg(conv.getLastMsg());
                conv.setLastMsgUid(conv.getLastMsgUid());
                //save back to db
                conversationRepository.save(conv);
        }
    }

    //Update last message and last message user id columns in conversation
    public void updateLastMessageInConvo(String convoId,String message,String message_uid){
        Optional<Conversation> optionalConversation=conversationRepository.findById(convoId);
        if(optionalConversation.isPresent()){
            Conversation conv=optionalConversation.get();
            conv.setLastMsg(message);
            conv.setLastMsgUid(message_uid);
            //save back to db
            conversationRepository.save(conv);
        }
    }

    public List<ConversationResponse> getBuyingConversationsOfUser(String uid){
        List<Conversation> convList = conversationRepository.findByBuyerUid(uid);

        return convList.stream().map((conv)->mapConversationToConversationResponse(conv)).toList();
    }
    public List<ConversationResponse> getSellingConversationsOfUser(String uid){
        List<Conversation> convList = conversationRepository.findBySellerUid(uid);

        return convList.stream().map((conv)->mapConversationToConversationResponse(conv)).toList();
    }


    public ConversationResponse mapConversationToConversationResponse(Conversation conv){
        return ConversationResponse.builder()
                .convo_id(conv.getConvoId())
                .buyer_uid(conv.getBuyerUid())
                .seller_uid(conv.getSellerUid())
                .lastMsg(conv.getLastMsg())
                .createdAt(conv.getCreatedAt())
                .lastMsg_uid(conv.getLastMsgUid())
                .product_id(conv.getProductId())
                .build();
    }

}
