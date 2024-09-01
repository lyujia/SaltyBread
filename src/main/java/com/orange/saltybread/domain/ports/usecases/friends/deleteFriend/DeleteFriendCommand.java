package com.orange.saltybread.domain.ports.usecases.friends.deleteFriend;

import java.util.UUID;

public record DeleteFriendCommand(UUID userId, String friendEmail) {

}
