package com.orange.saltybread.adapters.dto;

import com.orange.saltybread.domain.aggregates.accessLogs.AccessLog;
import java.time.LocalDateTime;

public record GetLatestAccessLogResponse(LocalDateTime timestamp) {

  public static GetLatestAccessLogResponse fromView(AccessLog accessLog) {
    return new GetLatestAccessLogResponse(accessLog.getAccessTimeStamp());
  }
}
