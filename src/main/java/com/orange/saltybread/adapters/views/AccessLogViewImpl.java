package com.orange.saltybread.adapters.views;

import com.orange.saltybread.domain.aggregates.accessLogs.AccessLog;
import com.orange.saltybread.domain.aggregates.accessLogs.QAccessLog;
import com.orange.saltybread.domain.ports.views.AccessLogView;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccessLogViewImpl implements AccessLogView {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<AccessLog> getLatestAccessLog(UUID userId, UUID roomId) {
    QAccessLog accessLog = QAccessLog.accessLog;
    return Optional.ofNullable(
        jpaQueryFactory
            .selectFrom(accessLog)
            .where(
                accessLog.userId.eq(userId)
                    .and(accessLog.roomId.eq(roomId))
            )
            .orderBy(accessLog.accessTimeStamp.desc())
            .limit(1)
            .fetchOne()
    );
  }
}
