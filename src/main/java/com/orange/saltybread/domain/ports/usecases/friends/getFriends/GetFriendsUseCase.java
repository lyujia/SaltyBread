package com.orange.saltybread.domain.ports.usecases.friends.getFriends;

import java.util.UUID;

public interface GetFriendsUseCase {

  public GetFriendsResponse getFriends(UUID userId);
}
