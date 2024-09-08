package com.orange.saltybread.domain.ports.views;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import com.orange.saltybread.domain.aggregates.users.User;
import java.util.List;
import java.util.UUID;

public interface ChatRoomView {

  public List<User> showUsers(UUID roomId);

  public List<ChatRoom> getChatRooms(UUID userId);
}
