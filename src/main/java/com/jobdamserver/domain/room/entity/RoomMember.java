package com.jobdamserver.domain.room.entity;

import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.global.baseentity.AuditBase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomMember extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * 연관관계 메서드
     */

    public void addMember(Member member) {
        this.member = member;
    }

    public void addRoom(Room room) {
        this.room = room;
    }

    /**
     * 생성 메서드
     */

    public static RoomMember createRoomMember(Member member) {
        return RoomMember.builder()
                .member(member)
                .build();
    }

}
