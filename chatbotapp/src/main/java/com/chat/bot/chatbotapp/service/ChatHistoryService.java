package com.chat.bot.chatbotapp.service;

import com.chat.bot.chatbotapp.jpa.entity.ChatHistory;
import com.chat.bot.chatbotapp.jpa.repository.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatHistoryService {

    ChatHistoryRepository chatHistoryRepository;

    public ChatHistoryService(ChatHistoryRepository chatHistoryRepository){
        this.chatHistoryRepository = chatHistoryRepository;
    }

    public void saveChatHistory(String message, String response){

        ChatHistory chat=new ChatHistory();
        chat.setChat_req_content(message);
        chat.setChat_res_content(response);
        chat.setChatsessionId(UUID.randomUUID().toString());
        chatHistoryRepository.saveAndFlush(chat);
    }

}
