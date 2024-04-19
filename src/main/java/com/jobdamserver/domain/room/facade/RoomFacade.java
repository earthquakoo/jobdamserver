package com.jobdamserver.domain.room.facade;

import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.domain.member.service.MemberService;
import com.jobdamserver.domain.room.controller.response.*;
import com.jobdamserver.domain.room.entity.Room;
import com.jobdamserver.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomFacade {

    private final RoomService roomService;
    private final MemberService memberService;

    public CreateRoomResponse createRoom(String name, String tag, int maximumParticipants, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        roomService.createRoom(name, tag, maximumParticipants, member);


        return CreateRoomResponse.builder()
                .name(name)
                .maximumParticipants(maximumParticipants)
                .tag(tag)
                .build();
    }

    public String participateRoom(String name, Long memberId) {
        roomService.participateRoom(name, memberId);
        return name;
    }

    public void deleteRoom(String name, Long memberId) {
        roomService.deleteRoom(name, memberId);
    }

    public void leaveRoom(String name, Long memberId) {
        roomService.leaveRoom(name, memberId);
    }

    public List<GetAllRoomResponse.roomDto> findAllRooms(Long memberId) {
        return roomService.findAllRooms(memberId);
    }

    public List<GetSearchRoomsResponse.roomDto> findSearchRooms(String searchWord) {
        List<Room> rooms = roomService.findSearchRooms(searchWord);
        List<GetSearchRoomsResponse.roomDto> roomDtos = new ArrayList<>();

        for (Room room : rooms) {
            GetSearchRoomsResponse.roomDto roomDto = GetSearchRoomsResponse.roomDto.builder()
                    .name(room.getName())
                    .tag(room.getTag())
                    .numParticipants(room.getNumParticipants())
                    .maximumParticipants(room.getMaximumParticipants())
                    .build();

            roomDtos.add(roomDto);
        }
        return roomDtos;
    }

    public void changeRoomSetting(String currentRoomName, String changeRoomName, String tag, int maximumParticipants, Long memberId) {
        roomService.changeRoomSetting(currentRoomName, changeRoomName, tag, maximumParticipants, memberId);
    }
}
