package com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom;

import com.orange.saltybread.domain.errors.CannotCreateRoomWithEmptyFriendListException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface CreateRoomUseCase {

  public CreateRoomResponse createRoom(CreateRoomCommand command)
      throws UserNotFoundException, CannotCreateRoomWithEmptyFriendListException;
}
