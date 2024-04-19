package com.jobdamserver.domain.room.repository;

import com.jobdamserver.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r JOIN r.roomMembers rm WHERE r.name = :name AND rm.member.id = :memberId ")
    Optional<Room> findByRoomNameAndMemberId(
            @Param("name") String name,
            @Param("memberId") Long memberId
    );

    @Query("SELECT r FROM Room r JOIN r.roomMembers rm WHERE rm.member.id = :memberId")
    List<Room> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT r FROM Room r WHERE r.tag = :tag")
    List<Room> findAllByTag(@Param("tag") String tag);

    @Query("SELECT r FROM Room r WHERE r.name = :name")
    List<Room> findAllByName(@Param("name") String name);
}
