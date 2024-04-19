package com.jobdamserver.domain.room.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateRoomResponse {

    private String name;
    private int maximumParticipants;
    private String tag;

}
