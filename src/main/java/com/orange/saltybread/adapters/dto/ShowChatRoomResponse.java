package com.orange.saltybread.adapters.dto;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import java.util.UUID;

public record ShowChatRoomResponse(UUID roomId, String title) {

  public static ShowChatRoomResponse fromView(ChatRoom chatRoom) {
    return new ShowChatRoomResponse(chatRoom.getId(), chatRoom.getTitle());
  }
}
