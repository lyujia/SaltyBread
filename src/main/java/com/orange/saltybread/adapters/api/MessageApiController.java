package com.orange.saltybread.adapters.api;

import com.orange.saltybread.adapters.dto.ApiResponse;
import com.orange.saltybread.adapters.dto.SearchMessagesResponse;
import com.orange.saltybread.adapters.dto.SendMessageRequest;
import com.orange.saltybread.adapters.service.JWTService;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.usecases.messages.sendMessage.SendMessageCommand;
import com.orange.saltybread.domain.ports.usecases.messages.sendMessage.SendMessageResponse;
import com.orange.saltybread.domain.ports.usecases.messages.sendMessage.SendMessageUseCase;
import com.orange.saltybread.domain.ports.views.MessageView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageApiController {

  private final SendMessageUseCase sendMessageUseCase;
  private final JWTService service;
  private final MessageView messageView;

  @PostMapping
  public ApiResponse<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    try {
      return ApiResponse.ok(sendMessageUseCase.sendMessage(
          new SendMessageCommand(userId, request.roomId(), request.message())));
    } catch (UserNotFoundException | ChatRoomNotFoundException e) {
      return ApiResponse.notFound();
    }
  }

  @GetMapping("/{roomId}")
  public ApiResponse<List<SearchMessagesResponse>> searchMessages(
      @PathVariable("roomId") UUID roomId,
      @RequestParam Optional<String> senderName,
      @RequestParam Optional<String> keyword,
      @RequestParam Optional<LocalDate> sendDate,
      @RequestParam Optional<LocalDateTime> cursor,
      @RequestParam int limit
  ) {
    return ApiResponse.ok(messageView
        .searchMessages(roomId, senderName, keyword, sendDate, cursor, limit)
        .stream()
        .map(SearchMessagesResponse::fromView)
        .toList().reversed());
  }
}
