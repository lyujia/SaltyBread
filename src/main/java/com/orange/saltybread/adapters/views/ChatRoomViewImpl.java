package com.orange.saltybread.adapters.views;

import com.orange.saltybread.domain.aggregates.chatRooms.QChatRoom;
import com.orange.saltybread.domain.aggregates.chatRooms.QChatRoomUserMapping;
import com.orange.saltybread.domain.aggregates.users.QUser;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.ports.views.ChatRoomView;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ChatRoomViewImpl implements ChatRoomView {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<User> showUsers(UUID roomId) {
    QChatRoom chatRoom = QChatRoom.chatRoom;
    QUser user = QUser.user;
    QChatRoomUserMapping mapping = QChatRoomUserMapping.chatRoomUserMapping;
    return queryFactory
        .selectFrom(chatRoom)
        .select(user)
        .join(chatRoom.mappings, mapping)
        .join(mapping.user, user)
        .where(chatRoom.id.eq(roomId))
        .fetch();
  }
}
