package com.chat.bot.chatbotapp.jpa.repository;




import com.chat.bot.chatbotapp.jpa.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
}
