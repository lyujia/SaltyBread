package com.orange.saltybread.adapters.dto;

import com.orange.saltybread.domain.aggregates.friends.Friend;
import java.util.UUID;

public record FindFriendsResponse(UUID userId, String name, String email) {

  public static FindFriendsResponse fromView(Friend friend) {
    return new FindFriendsResponse(
        friend.getFriend().getId(),
        friend.getFriend().getName(),
        friend.getFriend().getEmail()
    );
  }
}
