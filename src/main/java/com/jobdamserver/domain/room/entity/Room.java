package com.jobdamserver.domain.room.entity;

import com.jobdamserver.core.exception.CustomException;
import com.jobdamserver.domain.chat.entity.Chat;
import com.jobdamserver.global.baseentity.AuditBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room extends AuditBase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "num_participants", nullable = false)
    private int numParticipants;

    @Column(name = "maximum_participants", nullable = false)
    private int maximumParticipants;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RoomMember> roomMembers = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

    public void addRoomMembers(RoomMember roomMember) {
        this.roomMembers.add(roomMember);
        roomMember.addRoom(this);
    }

    /**
     * 생성 메서드
     */

    public static Room createRoom(
            String name,
            String tag,
            int maximumParticipants,
            RoomMember roomMember
    ) {
        Room room = Room.builder()
                .name(name)
                .tag(tag)
                .numParticipants(1)
                .maximumParticipants(maximumParticipants)
                .build();

        // RoomMember
        room.addRoomMembers(roomMember);

        return room;

    }


    /**
     * 비지니스 로직
     */

    public void joinRoom() {
        this.numParticipants += 1;
    }

    public void leaveRoom() {
        this.numParticipants -= 1;
    }

    public void changeRoomName(String name) {
        this.name = name;
    }

    public void changeTag(String tag) {
        this.tag = tag;
    }

    public void changeMaximumParticipants(int maximumParticipants) {
        this.maximumParticipants = maximumParticipants;
    }

}
