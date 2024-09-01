package com.orange.saltybread.domain.ports.views;

import com.orange.saltybread.domain.aggregates.accessLogs.AccessLog;
import java.util.Optional;
import java.util.UUID;

public interface AccessLogView {

  public Optional<AccessLog> getLatestAccessLog(UUID userId, UUID roomId);
}
