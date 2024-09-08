package com.orange.saltybread.adapters.api;

import com.orange.saltybread.adapters.dto.ApiResponse;
import com.orange.saltybread.adapters.dto.CreateRoomRequest;
import com.orange.saltybread.adapters.dto.InviteChatRoomRequest;
import com.orange.saltybread.adapters.dto.ShowChatRoomResponse;
import com.orange.saltybread.adapters.dto.ShowChatRoomUsersResponse;
import com.orange.saltybread.adapters.dto.UpdateRoomTitleRequest;
import com.orange.saltybread.domain.errors.CannotCreateRoomWithEmptyFriendListException;
import com.orange.saltybread.domain.errors.ChatRoomNotFoundException;
import com.orange.saltybread.domain.errors.UserAlreadyInvitedException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom.CreateRoomCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom.CreateRoomResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.createRoom.CreateRoomUseCase;
import com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom.ExitRoomCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom.ExitRoomResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.exitRoom.ExitRoomUseCase;
import com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser.InviteUserCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser.InviteUserResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.inviteUser.InviteUserUseCase;
import com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName.UpdateRoomTitleCommand;
import com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName.UpdateRoomTitleResponse;
import com.orange.saltybread.domain.ports.usecases.chatRooms.updateRoomName.UpdateRoomTitleUseCase;
import com.orange.saltybread.domain.ports.views.ChatRoomView;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/chatRooms")
public class ChatRoomApiController {

  private final CreateRoomUseCase createRoomUseCase;
  private final ExitRoomUseCase exitRoomUseCase;
  private final InviteUserUseCase inviteUserUseCase;
  private final UpdateRoomTitleUseCase updateRoomTitleUseCase;
  private final ChatRoomView chatRoomView;

  @PostMapping
  public ApiResponse<CreateRoomResponse> createRoom(
      @RequestBody CreateRoomRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    try {
      return ApiResponse.ok(createRoomUseCase.createRoom(
          new CreateRoomCommand(userId, request.title(), request.friendEmails())));
    } catch (UserNotFoundException e) {
      return ApiResponse.notFound();
    } catch (CannotCreateRoomWithEmptyFriendListException e) {
      return ApiResponse.badRequest();
    }
  }

  @DeleteMapping("/{roomId}/users")
  public ApiResponse<ExitRoomResponse> exitRoom(
      @PathVariable("roomId") UUID roomId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    try {
      return ApiResponse.ok(
          exitRoomUseCase.exitRoom(new ExitRoomCommand(userId, roomId)));
    } catch (UserNotFoundException | ChatRoomNotFoundException e) {
      return ApiResponse.notFound();
    }
  }

  @PostMapping("/{roomId}/users")
  public ApiResponse<InviteUserResponse> inviteUser(@PathVariable("roomId") UUID roomId,
      @RequestBody
      InviteChatRoomRequest request) {
    try {
      return ApiResponse.ok(
          inviteUserUseCase.inviteUser(new InviteUserCommand(roomId, request.friendEmails())));
    } catch (UserNotFoundException | ChatRoomNotFoundException e) {
      return ApiResponse.notFound();
    } catch (UserAlreadyInvitedException e) {
      return ApiResponse.badRequest();
    }
  }

  @PutMapping("/{roomId}/title")
  public ApiResponse<UpdateRoomTitleResponse> updateRoom(@PathVariable("roomId") UUID roomId,
      @RequestBody UpdateRoomTitleRequest request) {
    try {
      return ApiResponse.ok(updateRoomTitleUseCase.updateRoomTitle(
          new UpdateRoomTitleCommand(roomId, request.title())));
    } catch (ChatRoomNotFoundException e) {
      return ApiResponse.notFound();
    }
  }

  @GetMapping("/{roomId}/users")
  public ApiResponse<List<ShowChatRoomUsersResponse>> showUsers(
      @PathVariable("roomId") UUID roomId) {
    return ApiResponse.ok(
        chatRoomView.showUsers(roomId).stream().map(ShowChatRoomUsersResponse::fromView).toList());
  }

  @GetMapping
  public ApiResponse<List<ShowChatRoomResponse>> getRooms() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    return ApiResponse.ok(
        chatRoomView.getChatRooms(userId).stream().map(ShowChatRoomResponse::fromView).toList());
  }
}
