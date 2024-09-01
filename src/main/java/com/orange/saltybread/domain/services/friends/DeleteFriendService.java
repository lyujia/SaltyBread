package com.orange.saltybread.domain.services.friends;

import com.orange.saltybread.domain.aggregates.friends.Friend;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.FriendNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.FriendRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.friends.deleteFriend.DeleteFriendCommand;
import com.orange.saltybread.domain.ports.usecases.friends.deleteFriend.DeleteFriendResponse;
import com.orange.saltybread.domain.ports.usecases.friends.deleteFriend.DeleteFriendUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteFriendService implements DeleteFriendUseCase {

    private FriendRepository friendRepository;
    private UserRepository userRepository;

    @Override
    @Transactional
    public DeleteFriendResponse deleteFriend(DeleteFriendCommand command) throws FriendNotFoundException, UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(command.friendEmail());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<Friend> friendOptional = friendRepository.findByUserIdAndFriendId(command.userId(), optionalUser.get().getId());
        if (friendOptional.isEmpty()) {
            throw new FriendNotFoundException();
        }
        friendRepository.delete(friendOptional.get());
        return new DeleteFriendResponse();
    }
}
