package com.jobdamserver.domain.chat.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetChatHistoryResponse {

    private final List<ChatDto> chats;

    @Getter
    @Builder
    public static class ChatDto {
        private String sender;
        private String message;
    }
}
