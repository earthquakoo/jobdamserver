package com.jobdamserver.domain.chat.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdamserver.core.jwt.JwtTokenProvider;
import com.jobdamserver.core.jwt.dto.JwtUserInfo;
import com.jobdamserver.domain.chat.controller.dto.ChatMessage;
import com.jobdamserver.domain.chat.controller.response.GetChatHistoryResponse;
import com.jobdamserver.domain.chat.entity.Chat;
import com.jobdamserver.domain.chat.facade.ChatFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChatController {

    private final ChatFacade chatFacade;
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("/{roomName}")
    @SendTo("/sub/{roomName}")
    public ChatMessage chat(@DestinationVariable String roomName, @Payload byte[] message) {
        ChatMessage chatMessage = chatFacade.convertBytesToChatMessage(message);
        chatFacade.createChat(chatMessage.getMemberId(), roomName, chatMessage.getMessage());

        return chatMessage;
    }

    @GetMapping("/chat/history/{room_name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetChatHistoryResponse> getChatHistory(@PathVariable(name = "room_name") String roomName) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        List<GetChatHistoryResponse.ChatDto> chatDtos = chatFacade.findChatHistory(roomName, memberId);
        return ResponseEntity.ok().body(new GetChatHistoryResponse(chatDtos));
    }


    @PostMapping("/chat")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveChat() {

    }

    @GetMapping("/chat/history")
    @ResponseStatus(HttpStatus.OK)
    public void getChatHistory() {

    }
}
