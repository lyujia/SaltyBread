package com.orange.saltybread.adapters.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAccessLogRequest(UUID roomId, LocalDateTime timestamp) {

}
