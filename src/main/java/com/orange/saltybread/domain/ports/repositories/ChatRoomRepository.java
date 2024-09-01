package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    @Override
    boolean existsById(UUID id);
}
