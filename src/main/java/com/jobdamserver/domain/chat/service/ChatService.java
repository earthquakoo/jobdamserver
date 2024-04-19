package com.jobdamserver.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdamserver.domain.chat.controller.dto.ChatMessage;
import com.jobdamserver.domain.chat.controller.response.GetChatHistoryResponse;
import com.jobdamserver.domain.chat.entity.Chat;
import com.jobdamserver.domain.chat.repository.ChatRepository;
import com.jobdamserver.domain.room.entity.Room;
import com.jobdamserver.domain.room.entity.RoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void saveChat(RoomMember roomMember, String message) {
        Chat chat = Chat.createChat(roomMember,message);
        chatRepository.save(chat);

    }

    public List<GetChatHistoryResponse.ChatDto> findChatHistory(String roomName, Long memberId) {
        List<Chat> chats = chatRepository.findChatHistoryByRoomNameAndMemberId(roomName, memberId);
        List<GetChatHistoryResponse.ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat : chats) {
            GetChatHistoryResponse.ChatDto chatDto = GetChatHistoryResponse.ChatDto.builder()
                    .sender(chat.getRoomMember().getMember().getName())
                    .message(chat.getMessage())
                    .build();

            chatDtos.add(chatDto);
        }
        return chatDtos;
    }

    public ChatMessage convertBytesToChatMessage(byte[] messageBytes) {
        try {
            String jsonMessage = new String(messageBytes, "UTF-8");

            // 작은 따옴표를 큰 따옴표로 변경
            jsonMessage = jsonMessage.replace("'", "\"");

            // ObjectMapper를 사용하여 JSON 문자열을 ChatMessage 객체로 역직렬화
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonMessage, ChatMessage.class);
        } catch (Exception e) {
            // 변환 과정에서 예외 발생 시 처리
            e.printStackTrace();
            return null;
        }
    }
}
