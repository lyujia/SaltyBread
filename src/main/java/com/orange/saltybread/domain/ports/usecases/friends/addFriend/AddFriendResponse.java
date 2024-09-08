package com.orange.saltybread.domain.ports.usecases.friends.addFriend;

import java.util.UUID;

public record AddFriendResponse(UUID userId, String name, String email) {

}
