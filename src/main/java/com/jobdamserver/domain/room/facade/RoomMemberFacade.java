package com.jobdamserver.domain.room.facade;

import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.domain.member.service.MemberService;
import com.jobdamserver.domain.room.controller.response.GetCurrentRoomMemberResponse;
import com.jobdamserver.domain.room.controller.response.GetParticipatedRoomsResponse;
import com.jobdamserver.domain.room.service.RoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomMemberFacade {

    private final MemberService memberService;
    private final RoomMemberService roomMemberService;

    public List<GetParticipatedRoomsResponse.roomDto> findParticipatedRooms(Long memberId) {
        return roomMemberService.findParticipatedRoomsList(memberId);
    }

    public List<String> findCurrentRoomMembers(String roomName, Long memberId) {
        return roomMemberService.findCurrentRoomMembers(roomName, memberId);
    }
}
