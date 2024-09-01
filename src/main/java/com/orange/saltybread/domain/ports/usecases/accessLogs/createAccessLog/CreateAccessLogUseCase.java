package com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog;

import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface CreateAccessLogUseCase {

  public CreateAccessLogResponse createAccessLog(CreateAccessLogCommand command)
      throws ChatRoomNotFoundException, UserNotFoundException;
}
