package com.orange.saltybread.domain.services.accesslogs;

import com.orange.saltybread.domain.aggregates.accessLogs.AccessLog;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.AccessLogRepository;
import com.orange.saltybread.domain.ports.repositories.ChatRoomRepository;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog.CreateAccessLogCommand;
import com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog.CreateAccessLogResponse;
import com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog.CreateAccessLogUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateAccessLogService implements CreateAccessLogUseCase {

  private final ChatRoomRepository chatRoomRepository;
  private final AccessLogRepository accessLogRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public CreateAccessLogResponse createAccessLog(CreateAccessLogCommand command)
      throws ChatRoomNotFoundException, UserNotFoundException {
    if (!chatRoomRepository.existsById(command.roomId())) {
      throw new ChatRoomNotFoundException();
    }
    if (!userRepository.existsById(command.userId())) {
      throw new UserNotFoundException();
    }
    AccessLog accessLog = new AccessLog(command.userId(), command.roomId(), command.timestamp());
    accessLogRepository.save(accessLog);
    return new CreateAccessLogResponse();
  }

}
