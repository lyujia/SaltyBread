package com.orange.saltybread.adapters.dto;

import com.orange.saltybread.domain.aggregates.users.User;
import java.util.UUID;

public record ShowChatRoomUsersResponse(UUID userId, String name, String email) {

  public static ShowChatRoomUsersResponse fromView(User user) {
    return new ShowChatRoomUsersResponse(
        user.getId(),
        user.getName(),
        user.getEmail()
    );
  }
}
