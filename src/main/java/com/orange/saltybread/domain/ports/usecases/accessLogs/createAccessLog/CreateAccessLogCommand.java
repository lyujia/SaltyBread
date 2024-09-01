package com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAccessLogCommand(UUID userId, UUID roomId, LocalDateTime timestamp) {

}
