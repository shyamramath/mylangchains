package com.chat.bot.chatbotapp.controller;

import com.bot.ai.ChatService;
import com.bot.ai.ChatServiceImpl;
import com.chat.bot.chatbotapp.objects.ChatConversation;
import com.chat.bot.chatbotapp.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    ChatService chatService = new ChatServiceImpl();

    @Autowired
    ChatHistoryService chatHistoryService;

    @GetMapping("/chat")
    public String chatMessage(){
        return chatService.chatMessage("message");
    }

    @PostMapping("/conversation")
    public String conversationalChat(@RequestBody ChatConversation chatConversation){
        String response =chatService.chatMessage(chatConversation.getMessage());
        chatHistoryService.saveChatHistory(chatConversation.getMessage(), response);
        return response;
    }

}
