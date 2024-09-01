package com.orange.saltybread.domain.services.rooms;

import com.orange.saltybread.domain.aggregates.chatRooms.ChatRoom;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.CannotCreateRoomWithEmptyFriendListException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.ChatRoomRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom.CreateRoomCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom.CreateRoomResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom.CreateRoomUseCase;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateRoomService implements CreateRoomUseCase {

  ChatRoomRepository chatRoomRepository;
  UserRepository userRepository;

  @Override
  @Transactional
  public CreateRoomResponse createRoom(CreateRoomCommand command)
      throws UserNotFoundException, CannotCreateRoomWithEmptyFriendListException {
    Optional<User> optionalUser = userRepository.findById(command.userId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException();
    }
    List<User> users = userRepository.findByEmailIn(command.friendEmails());
    if (users.size() != command.friendEmails().size()) {
      throw new UserNotFoundException();
    }
    ChatRoom chatRoom = new ChatRoom(
        command.title(),
        optionalUser.get(),
        users
    );
    chatRoomRepository.save(chatRoom);

    return new CreateRoomResponse(chatRoom.getId());
  }
}
