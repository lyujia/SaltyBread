package com.orange.saltybread.adapters.api;

import com.orange.saltybread.adapters.dto.ApiResponse;
import com.orange.saltybread.adapters.dto.CreateAccessLogRequest;
import com.orange.saltybread.adapters.dto.GetLatestAccessLogResponse;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog.CreateAccessLogCommand;
import com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog.CreateAccessLogResponse;
import com.orange.saltybread.domain.ports.usecases.accessLogs.createAccessLog.CreateAccessLogUseCase;
import com.orange.saltybread.domain.ports.views.AccessLogView;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accessLogs")
@AllArgsConstructor
public class AccessLogApiController {

  private final CreateAccessLogUseCase createAccessLogUseCase;
  private final AccessLogView accessLogView;

  @PostMapping
  public ApiResponse<CreateAccessLogResponse> createAccessLog(
      @RequestBody CreateAccessLogRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    try {
      return ApiResponse.ok(
          createAccessLogUseCase.createAccessLog(
              new CreateAccessLogCommand(userId, request.roomId(), request.timestamp())
          )
      );
    } catch (UserNotFoundException | ChatRoomNotFoundException e) {
      return ApiResponse.notFound();
    }
  }

  @GetMapping("/{roomId}")
  public ApiResponse<Optional<GetLatestAccessLogResponse>> getLatestAccessLog(
      @PathVariable(name = "roomId") UUID roomId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    return ApiResponse.ok(
        accessLogView.getLatestAccessLog(userId, roomId).map(GetLatestAccessLogResponse::fromView));
  }
}
