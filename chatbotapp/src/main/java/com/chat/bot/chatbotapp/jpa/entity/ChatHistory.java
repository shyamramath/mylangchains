package com.chat.bot.chatbotapp.jpa.entity;

import jakarta.persistence.*;

@Entity
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatsessionId")
    private String chatsessionId;
    @Column(name = "chat_req_content")
    private String chat_req_content;
    @Column(name = "chat_res_content")
    private String chat_res_content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    public ChatHistory(String chatsessionId, String chat_req_content, String chat_res_content) {
//        this.chatsessionId = chatsessionId;
//        this.chat_req_content = chat_req_content;
//        this.chat_res_content = chat_res_content;
//    }


    public String getChatsessionId() {
        return chatsessionId;
    }

    public void setChatsessionId(String chatsessionId) {
        this.chatsessionId = chatsessionId;
    }

    public String getChat_req_content() {
        return chat_req_content;
    }

    public void setChat_req_content(String chat_req_content) {
        this.chat_req_content = chat_req_content;
    }

    public String getChat_res_content() {
        return chat_res_content;
    }

    public void setChat_res_content(String chat_res_content) {
        this.chat_res_content = chat_res_content;
    }
}
