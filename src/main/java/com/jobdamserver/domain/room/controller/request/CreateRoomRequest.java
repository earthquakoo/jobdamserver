package com.jobdamserver.domain.room.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRoomRequest {
    private String name;
    private String tag;
    private int maximumParticipants;
}
