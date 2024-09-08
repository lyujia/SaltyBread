package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

  @Override
  boolean existsById(UUID id);
}
