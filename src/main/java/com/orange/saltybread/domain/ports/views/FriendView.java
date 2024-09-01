package com.orange.saltybread.domain.ports.views;

import com.orange.saltybread.domain.aggregates.friends.Friend;
import java.util.List;
import java.util.UUID;

public interface FriendView {

  public List<Friend> findFriends(UUID userId, String name);
}
