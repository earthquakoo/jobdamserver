package com.jobdamserver.domain.room.service;

import com.jobdamserver.core.exception.CustomException;
import com.jobdamserver.core.exception.ErrorInfo;
import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.domain.room.controller.response.GetCurrentRoomMemberResponse;
import com.jobdamserver.domain.room.controller.response.GetParticipatedRoomsResponse;
import com.jobdamserver.domain.room.entity.Room;
import com.jobdamserver.domain.room.entity.RoomMember;
import com.jobdamserver.domain.room.repository.RoomMemberRepository;
import com.jobdamserver.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jobdamserver.core.exception.ErrorInfo.ROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomMemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final RoomRepository roomRepository;

    public RoomMember findByRoomNameAndMemberId(String roomName, Long memberId) {
        return roomMemberRepository.findByRoomNameAndMemberId(roomName, memberId)
                .orElseThrow(() -> new CustomException(ErrorInfo.ROOM_NOT_FOUND));
    }

    public List<GetParticipatedRoomsResponse.roomDto> findParticipatedRoomsList(Long memberId) {
        List<RoomMember> roomMembers = roomMemberRepository.findAllByMemberId(memberId);

        List<GetParticipatedRoomsResponse.roomDto> roomDtos = new ArrayList<>();
        for (RoomMember roomMember : roomMembers) {
            Room roomInfo = roomMember.getRoom();

            GetParticipatedRoomsResponse.roomDto roomDto = GetParticipatedRoomsResponse.roomDto.builder()
                    .id(roomInfo.getId())
                    .name(roomInfo.getName())
                    .tag(roomInfo.getTag())
                    .maximumParticipants(roomInfo.getMaximumParticipants())
                    .numParticipants(roomInfo.getNumParticipants())
                    .build();

            roomDtos.add(roomDto);
        }
        return roomDtos;
    }

    public List<String> findCurrentRoomMembers(String roomName, Long memberId) {
        List<RoomMember> roomMembers = roomMemberRepository.findAllByRoomNameAndMemberId(roomName, memberId);
        List<String> memberList = new ArrayList<>();

        for (RoomMember roomMember : roomMembers) {
            String memberName = roomMember.getMember().getName();
            memberList.add(memberName);
        }
        return memberList;
    }
}
