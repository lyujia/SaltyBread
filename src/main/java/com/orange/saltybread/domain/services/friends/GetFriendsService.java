package com.orange.saltybread.domain.services.friends;

import com.orange.saltybread.domain.ports.repositories.FriendRepository;
import com.orange.saltybread.domain.ports.usecases.friends.getFriends.GetFriendsResponse;
import com.orange.saltybread.domain.ports.usecases.friends.getFriends.GetFriendsUseCase;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetFriendsService implements GetFriendsUseCase {

  private FriendRepository friendRepository;

  @Autowired
  public GetFriendsService(FriendRepository friendRepository) {
    this.friendRepository = friendRepository;
  }

  @Override
  @Transactional
  public GetFriendsResponse getFriends(UUID userId) {
    //쿼리 DSL사용->해당 userId인 목록들 가져오기-> <user>List or <friend>List?
    return null;
  }
}
