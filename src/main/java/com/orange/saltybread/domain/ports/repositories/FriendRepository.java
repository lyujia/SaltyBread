package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.friends.Friend;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, UUID> {

  public Optional<Friend> findByUserIdAndFriendId(UUID userId, UUID friendId);
}
