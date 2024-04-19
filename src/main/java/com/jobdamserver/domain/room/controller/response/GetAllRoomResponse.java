package com.jobdamserver.domain.room.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllRoomResponse {

    private List<roomDto> rooms;

    @Getter
    @Builder
    public static class roomDto {
        private Long id;
        private String name;
        private String tag;
        private int maximumParticipants;
        private int numParticipants;
    }
}
