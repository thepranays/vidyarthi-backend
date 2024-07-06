package com.vidyarthi.ChatMessageService.repos;

import com.vidyarthi.ChatMessageService.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    public List<Message> findByConvoId(String convo_id);
}
