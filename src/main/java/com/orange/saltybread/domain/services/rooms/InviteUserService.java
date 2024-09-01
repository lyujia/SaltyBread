package com.orange.saltybread.domain.services.rooms;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserAlreadyInvitedException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.ChatRoomRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser.InviteUserCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser.InviteUserResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser.InviteUserUseCase;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InviteUserService implements InviteUserUseCase {

  private ChatRoomRepository chatRoomRepository;
  private UserRepository userRepository;


  @Override
  @Transactional
  public InviteUserResponse inviteUser(InviteUserCommand command)
      throws UserNotFoundException, ChatRoomNotFoundException, UserAlreadyInvitedException {
    List<User> users = userRepository.findByEmailIn(command.friendEmails());
    if (users.size() != command.friendEmails().size()) {
      throw new UserNotFoundException();
    }
    Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(command.roomId());
    if (optionalChatRoom.isEmpty()) {
      throw new ChatRoomNotFoundException();
    }
    ChatRoom chatRoom = optionalChatRoom.get();
    users.forEach(chatRoom::addUser);
    chatRoomRepository.save(chatRoom);

    return new InviteUserResponse();
  }
}
