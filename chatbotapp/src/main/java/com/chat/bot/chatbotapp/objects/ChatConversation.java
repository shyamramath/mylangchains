package com.chat.bot.chatbotapp.objects;

public class ChatConversation {

    private String message;
    private String response;

    public ChatConversation(String message, String response) {
        this.message = message;
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public String getResponse() {
        return response;
    }

}
