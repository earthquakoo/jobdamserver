package com.jobdamserver.domain.member.entity;

import com.jobdamserver.domain.room.entity.Room;
import com.jobdamserver.domain.room.entity.RoomMember;
import com.jobdamserver.global.baseentity.AuditBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends AuditBase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @NotBlank
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "owned_rooms", nullable = false)
    private int ownedRooms;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMember> roomMembers = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

    public void addRoomMember(RoomMember roomMember) {
        this.roomMembers.add(roomMember);
        roomMember.addMember(this);
    }

    /**
     * 비지니스 로직
     */
    public void deleteRoom() {

    }
}
