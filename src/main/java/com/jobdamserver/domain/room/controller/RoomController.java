package com.jobdamserver.domain.room.controller;

import com.jobdamserver.core.jwt.JwtTokenProvider;
import com.jobdamserver.core.jwt.dto.JwtUserInfo;
import com.jobdamserver.domain.room.controller.request.*;
import com.jobdamserver.domain.room.controller.response.*;
import com.jobdamserver.domain.room.entity.Room;
import com.jobdamserver.domain.room.facade.RoomFacade;
import com.jobdamserver.domain.room.facade.RoomMemberFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RoomMemberFacade roomMemberFacade;
    private final RoomFacade roomFacade;


    @PostMapping("/room/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateRoomResponse> createRoom(@Valid @RequestBody CreateRoomRequest request) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        CreateRoomResponse response = roomFacade.createRoom(request.getName(), request.getTag(), request.getMaximumParticipants(), memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/room/participate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ParticipateRoomResponse> participateRoom(@Valid @RequestBody ParticipateRoomRequest request) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        String roomName = roomFacade.participateRoom(request.getRoomName(), memberId);
        ParticipateRoomResponse response = ParticipateRoomResponse.builder()
                .name(roomName)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/room/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@Valid @RequestBody DeleteRoomRequest request) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        roomFacade.deleteRoom(request.getName(), memberId);
    }

    @PostMapping("/room/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveRoom(@Valid @RequestBody LeaveRoomRequest request) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        roomFacade.leaveRoom(request.getName(), memberId);
    }

    @GetMapping("/room/{room_name}/members")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetCurrentRoomMemberResponse> getCurrentRoomMembers(@PathVariable(name = "room_name") String roomName) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        List<String> roomMembers = roomMemberFacade.findCurrentRoomMembers(roomName, memberId);
        return ResponseEntity.ok().body(new GetCurrentRoomMemberResponse(roomMembers));
    }

    @GetMapping("/room/participated-rooms")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetParticipatedRoomsResponse> getParticipatedRooms() {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        List<GetParticipatedRoomsResponse.roomDto> roomDto = roomMemberFacade.findParticipatedRooms(memberId);
        return ResponseEntity.ok().body(new GetParticipatedRoomsResponse(roomDto));
    }

    @GetMapping("/rooms")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetAllRoomResponse> getAllRooms() {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        List<GetAllRoomResponse.roomDto> roomDto = roomFacade.findAllRooms(memberId);
        return ResponseEntity.ok().body(new GetAllRoomResponse(roomDto));
    }

    @GetMapping("/room/search/{word}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetSearchRoomsResponse> getSearchRooms(@PathVariable(name = "word") String word) {
        List<GetSearchRoomsResponse.roomDto> rooms = roomFacade.findSearchRooms(word);
        return ResponseEntity.ok().body(new GetSearchRoomsResponse(rooms));
    }

    @PatchMapping("/room/change-setting")
    @ResponseStatus(HttpStatus.OK)
    public void changeRoomSetting(@Valid @RequestBody ChangeRoomSettingRequest request) {
        JwtUserInfo jwtUserInfo = jwtTokenProvider.getCurrentUserInfo();
        Long memberId = jwtUserInfo.getMemberId();

        roomFacade.changeRoomSetting(
                request.getCurrentRoomName(),
                request.getChangeRoomName(),
                request.getTag(),
                request.getMaximumParticipants(),
                memberId);
    }
}
