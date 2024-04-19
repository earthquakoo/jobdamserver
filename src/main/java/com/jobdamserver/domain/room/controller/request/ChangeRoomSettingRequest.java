package com.jobdamserver.domain.room.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeRoomSettingRequest {

    private String currentRoomName;
    private String changeRoomName;
    private String tag;
    private int maximumParticipants;
}
