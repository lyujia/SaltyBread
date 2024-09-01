package com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser;

import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserAlreadyInvitedException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface InviteUserUseCase {

  public InviteUserResponse inviteUser(InviteUserCommand command)
      throws UserNotFoundException, ChatRoomNotFoundException, UserAlreadyInvitedException;
}
