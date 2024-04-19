package com.jobdamserver.domain.chat.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    private Long memberId;
    private String sender;
    private String roomName;
    private String message;
}
