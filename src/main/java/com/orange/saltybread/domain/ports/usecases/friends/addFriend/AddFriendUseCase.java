package com.orange.saltybread.domain.ports.usecases.friends.addFriend;

import com.orange.saltybread.domain.errors.CannotAddMySelfException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface AddFriendUseCase {

    public AddFriendResponse addFriend(AddFriendCommand command) throws UserNotFoundException, CannotAddMySelfException;
}
