package com.orange.saltybread.domain.services.friends;

import com.orange.saltybread.domain.aggregates.friends.Friend;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.CannotAddMySelfException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.FriendRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.friends.addFriend.AddFriendCommand;
import com.orange.saltybread.domain.ports.usecases.friends.addFriend.AddFriendResponse;
import com.orange.saltybread.domain.ports.usecases.friends.addFriend.AddFriendUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddFriendService implements AddFriendUseCase {

  private final FriendRepository friendRepository;
  private final UserRepository userRepository;

  @Autowired
  public AddFriendService(FriendRepository friendRepository, UserRepository userRepository) {
    this.friendRepository = friendRepository;
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public AddFriendResponse addFriend(AddFriendCommand command)
      throws UserNotFoundException, CannotAddMySelfException {
    Optional<User> friendOptional = userRepository.findByEmail(command.friendEmail());
    if (friendOptional.isEmpty()) {
      throw new UserNotFoundException();
    }
    Friend friend = new Friend(command.userId(), friendOptional.get());
    friendRepository.save(friend);
    return new AddFriendResponse(friendOptional.get().getId(), friendOptional.get().getName(),
        friendOptional.get().getEmail());
  }
}
