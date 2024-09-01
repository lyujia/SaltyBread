package com.orange.saltybread.domain.ports.usecases.friends.getFriends;

import com.orange.saltybread.domain.aggregates.users.User;
import java.util.List;

public record GetFriendsResponse(List<User> friends) {

}
