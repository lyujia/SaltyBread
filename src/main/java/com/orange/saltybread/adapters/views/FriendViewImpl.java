package com.orange.saltybread.adapters.views;

import com.orange.saltybread.domain.aggregates.friends.Friend;
import com.orange.saltybread.domain.aggregates.friends.QFriend;
import com.orange.saltybread.domain.aggregates.users.QUser;
import com.orange.saltybread.domain.ports.views.FriendView;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class FriendViewImpl implements FriendView {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Friend> findFriends(UUID userId, String name) {
    QFriend friend = QFriend.friend1;
    QUser user = QUser.user;//qclass model, api controller, view 역할이 여기
    return queryFactory
        .selectFrom(friend)
        .join(friend.friend, user)
        .where(friend.userId.eq(userId))
        .where(user.name.startsWith(name))
        .fetch();
  }
  //cqrs=command query responsibility, segregation, 쓰기 읽기 분리 command 쓰기(ddd), query 3 tier architecture /mvc 의 한 종류
}
