package com.orange.saltybread.domain.ports.usecases.friends.deleteFriend;

import com.orange.saltybread.domain.errors.FriendNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface DeleteFriendUseCase {

    public DeleteFriendResponse deleteFriend(DeleteFriendCommand command) throws FriendNotFoundException, UserNotFoundException;
}
