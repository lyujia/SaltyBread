package com.orange.saltybread.adapters.views;


import com.orange.saltybread.domain.aggregates.messages.Message;
import com.orange.saltybread.domain.aggregates.messages.QMessage;
import com.orange.saltybread.domain.aggregates.users.QUser;
import com.orange.saltybread.domain.ports.views.MessageView;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MessageViewImpl implements MessageView {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> searchMessages(UUID roomId, Optional<String> username, Optional<String> keyword, Optional<LocalDate> sendDate, Optional<LocalDateTime> cursor, int limit) {
        QMessage message = QMessage.message1;
        QUser user = QUser.user;
        JPAQuery<Message> query = jpaQueryFactory
                .selectFrom(message)
                .join(message.sender, user)
                .where(message.roomId.eq(roomId));
        if (username.isPresent()) {
            query = query.where(user.name.eq(username.get()));
        }
        if (keyword.isPresent()) {
            query = query.where(message.message.contains(keyword.get()));
        }
        if (sendDate.isPresent()) {
            LocalDateTime startDateTime = LocalDateTime.of(sendDate.get(), LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(sendDate.get(), LocalTime.MAX);
            query = query.where(message.timeStamp.between(startDateTime, endDateTime));
        }
        query = query.orderBy(message.timeStamp.desc());
        if (cursor.isPresent()) {
            query.where(message.timeStamp.before(cursor.get()));
        }
        return query.limit(limit).fetch();
    }
}
