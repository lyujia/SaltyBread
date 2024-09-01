package com.orange.saltybread.domain.services.rooms;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.ChatRoomRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom.ExitRoomCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom.ExitRoomResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom.ExitRoomUseCase;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ExitRoomService implements ExitRoomUseCase {

  private ChatRoomRepository chatRoomRepository;
  private UserRepository userRepository;

  @Transactional
  @Override
  public ExitRoomResponse exitRoom(ExitRoomCommand command)
      throws UserNotFoundException, ChatRoomNotFoundException {
    Optional<User> optionalUser = userRepository.findById(command.userId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException();
    }
    Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(command.roomId());
    if (optionalChatRoom.isEmpty()) {
      throw new ChatRoomNotFoundException();
    }
    ChatRoom chatRoom = optionalChatRoom.get();
    chatRoom.removeUser(optionalUser.get());
    if (chatRoom.getMappings().size() < 2) {
      chatRoomRepository.delete(chatRoom);
    } else {
      chatRoomRepository.save(chatRoom);
    }
    return new ExitRoomResponse();
  }
}
