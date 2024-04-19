package com.jobdamserver.domain.room.controller.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetCurrentRoomMemberResponse {
    private List<String> members;
}
