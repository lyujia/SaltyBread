package com.orange.saltybread.domain.ports.usecases.friends.addFriend;

import java.util.UUID;

public record AddFriendCommand(UUID userId, String friendEmail) {

}
