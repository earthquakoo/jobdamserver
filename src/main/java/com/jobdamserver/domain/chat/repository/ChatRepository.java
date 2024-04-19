package com.jobdamserver.domain.chat.repository;

import com.jobdamserver.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN c.roomMember rm WHERE rm.room.name = :roomName AND rm.member.id = :memberId")
    List<Chat> findChatHistoryByRoomNameAndMemberId(
            @Param("roomName") String roomName,
            @Param("memberId") Long memberId
    );
}
