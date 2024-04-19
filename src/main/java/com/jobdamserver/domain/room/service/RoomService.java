package com.jobdamserver.domain.room.service;

import com.jobdamserver.core.exception.CustomException;
import com.jobdamserver.core.exception.ErrorInfo;
import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.domain.room.controller.response.GetAllRoomResponse;
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

import static com.jobdamserver.core.exception.ErrorInfo.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public void createRoom(String name, String tag, int maximumParticipants, Member member) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndMemberId(name, member.getId());
        if (optionalRoom.isPresent()) {
            throw new CustomException(DUPLICATE_ROOM);
        }

        RoomMember roomMember = RoomMember.createRoomMember(member);

        Room room = Room.createRoom(name, tag, maximumParticipants, roomMember);

        roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(String name, Long memberId) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndMemberId(name, memberId);
        if (optionalRoom.isEmpty()) {
            return ;
        }

        Room room = optionalRoom.get();
        roomRepository.delete(room);
    }

    public void participateRoom(String name, Long memberId) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndMemberId(name, memberId);
        if (optionalRoom.isEmpty()) {
            return ;
        }

        Room room = optionalRoom.get();
        room.joinRoom();
    }

    public void leaveRoom(String name, Long memberId) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndMemberId(name, memberId);
        if (optionalRoom.isEmpty()) {
            return ;
        }

        Room room = optionalRoom.get();
        room.leaveRoom();
    }


    public List<GetAllRoomResponse.roomDto> findAllRooms(Long memberId) {
        List<Room> rooms = roomRepository.findAllByMemberId(memberId);
        List<GetAllRoomResponse.roomDto> roomDtos = new ArrayList<>();
        for (Room room : rooms) {
            GetAllRoomResponse.roomDto roomDto = GetAllRoomResponse.roomDto.builder()
                    .id(room.getId())
                    .name(room.getName())
                    .tag(room.getTag())
                    .maximumParticipants(room.getMaximumParticipants())
                    .numParticipants(room.getNumParticipants())
                    .build();
            roomDtos.add(roomDto);
        }
        return roomDtos;
    }

    public List<Room> findSearchRooms(String searchWord) {
        String roomName = null;
        String tag = null;
        String[] args = searchWord.split(" ");

        if (args.length >= 2 && args[0].equals("tag")) {
            tag = args[1];
            List<Room> rooms = roomRepository.findAllByTag(tag);

            if (rooms.isEmpty()) {
                throw new CustomException(ROOM_NOT_FOUND);
            }
            return rooms;

        } else {
            roomName = searchWord;
            List<Room> rooms = roomRepository.findAllByName(roomName);

            if (rooms.isEmpty()) {
                throw new CustomException(ROOM_NOT_FOUND);
            }
            return rooms;
        }
    }

    @Transactional
    public void changeRoomSetting(String currentRoomName, String changeRoomName, String tag, int maximumParticipants, Long memberId) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndMemberId(currentRoomName, memberId);
        if (optionalRoom.isEmpty()) {
            return ;
        }

        Room room = optionalRoom.get();

        if (!changeRoomName.isEmpty()) {
            room.changeRoomName(changeRoomName);
        }

        if (!tag.isEmpty()) {
            room.changeTag(tag);
        }

        if (room.getMaximumParticipants() != maximumParticipants) {
            if (room.getNumParticipants() > maximumParticipants) {
                throw new CustomException(PARTICIPANTS_OVER_COUNT);
            }
            room.changeMaximumParticipants(maximumParticipants);
        }

    }

    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ROOM_NOT_FOUND));
    }
}
