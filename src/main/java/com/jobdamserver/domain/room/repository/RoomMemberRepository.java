package com.jobdamserver.domain.room.repository;

import com.jobdamserver.domain.room.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    @Query("SELECT rm FROM RoomMember rm WHERE rm.member.id = :memberId")
    List<RoomMember> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT rm FROM RoomMember rm WHERE rm.room.name = :roomName AND rm.member.id = :memberId")
    Optional<RoomMember> findByRoomNameAndMemberId(
            @Param("roomName") String roomName,
            @Param("memberId") Long memberId
    );

    @Query("SELECT rm FROM RoomMember rm WHERE rm.room.name = :roomName AND rm.member.id = :memberId")
    List<RoomMember> findAllByRoomNameAndMemberId(
            @Param("roomName") String roomName,
            @Param("memberId") Long memberId
    );

}
