package com.jobdamserver.domain.chat.facade;

import com.jobdamserver.domain.chat.controller.dto.ChatMessage;
import com.jobdamserver.domain.chat.controller.response.GetChatHistoryResponse;
import com.jobdamserver.domain.chat.entity.Chat;
import com.jobdamserver.domain.chat.service.ChatService;
import com.jobdamserver.domain.room.entity.Room;
import com.jobdamserver.domain.room.entity.RoomMember;
import com.jobdamserver.domain.room.service.RoomMemberService;
import com.jobdamserver.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatFacade {

    private final ChatService chatService;
    private final RoomService roomService;
    private final RoomMemberService roomMemberService;

    @Transactional
    public void createChat(Long memberId, String roomName, String message) {
        RoomMember roomMember = roomMemberService.findByRoomNameAndMemberId(roomName, memberId);
        chatService.saveChat(roomMember, message);
    }

    public List<GetChatHistoryResponse.ChatDto> findChatHistory(String roomName, Long memberId) {
        return chatService.findChatHistory(roomName, memberId);
    }

    public ChatMessage convertBytesToChatMessage(byte[] messageBytes) {
        return chatService.convertBytesToChatMessage(messageBytes);
    }
}
