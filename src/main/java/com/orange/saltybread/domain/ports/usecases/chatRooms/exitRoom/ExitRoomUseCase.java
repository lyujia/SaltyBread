package com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom;

import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface ExitRoomUseCase {

  public ExitRoomResponse exitRoom(ExitRoomCommand command)
      throws UserNotFoundException, ChatRoomNotFoundException;

}
