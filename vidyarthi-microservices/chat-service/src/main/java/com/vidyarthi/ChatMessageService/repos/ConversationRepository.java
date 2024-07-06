package com.vidyarthi.ChatMessageService.repos;

import com.vidyarthi.ChatMessageService.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ConversationRepository extends JpaRepository<Conversation,String> {

    //findBy{column name}{and/or/etc}{other column} .... {remember to use camalcase like show below}\
    //Returns conversation whose buyer_uid and seller_uid matches
    //use double underscore to escape 'underscore' as it is reserved character in spring
    Optional<Conversation> findByBuyerUidAndSellerUidAndProductId(String buyer_uid, String seller_uid, String product_id);

    //Return conversation whose buyer_uid matches
    List<Conversation> findByBuyerUid(String buyer_uid);
    //Return conversation whose seller_uid matches
    List<Conversation> findBySellerUid(String seller_uid);
}
