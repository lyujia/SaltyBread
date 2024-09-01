package com.orange.saltybread.adapters.api;

import com.orange.saltybread.adapters.dto.AddFriendRequest;
import com.orange.saltybread.adapters.dto.ApiResponse;
import com.orange.saltybread.adapters.dto.DeleteFriendRequest;
import com.orange.saltybread.adapters.dto.FindFriendsResponse;
import com.orange.saltybread.domain.aggregates.friends.Friend;
import com.orange.saltybread.domain.errors.CannotAddMySelfException;
import com.orange.saltybread.domain.errors.FriendNotFoundException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.usecases.friends.addFriend.AddFriendCommand;
import com.orange.saltybread.domain.ports.usecases.friends.addFriend.AddFriendResponse;
import com.orange.saltybread.domain.ports.usecases.friends.addFriend.AddFriendUseCase;
import com.orange.saltybread.domain.ports.usecases.friends.deleteFriend.DeleteFriendCommand;
import com.orange.saltybread.domain.ports.usecases.friends.deleteFriend.DeleteFriendResponse;
import com.orange.saltybread.domain.ports.usecases.friends.deleteFriend.DeleteFriendUseCase;
import com.orange.saltybread.domain.ports.usecases.friends.getFriends.GetFriendsUseCase;
import com.orange.saltybread.domain.ports.views.FriendView;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/friends")
@RestController
@AllArgsConstructor
public class FriendApiController {

  private final AddFriendUseCase addFriendUseCase;
  private final DeleteFriendUseCase deleteFriendUseCase;
  private final GetFriendsUseCase getFriendsUseCase;
  private final FriendView friendView;

  @PostMapping
  public ApiResponse<AddFriendResponse> addFriend(
      @RequestBody AddFriendRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    try {
      return ApiResponse.ok(
          addFriendUseCase.addFriend(new AddFriendCommand(userId, request.friendEmail())));
    } catch (UserNotFoundException e) {
      return ApiResponse.notFound();
    } catch (CannotAddMySelfException e) {
      return ApiResponse.badRequest();
    }
  }

  @DeleteMapping
  public ApiResponse<DeleteFriendResponse> deleteFriend(
      @RequestBody DeleteFriendRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    try {
      return ApiResponse.ok(
          deleteFriendUseCase.deleteFriend(new DeleteFriendCommand(userId, request.friendEmail())));
    } catch (UserNotFoundException | FriendNotFoundException e) {
      return ApiResponse.notFound();
    }
  }

  @GetMapping
  public ApiResponse<List<FindFriendsResponse>> getFriends(
      @RequestParam(name = "friendName", required = false) String friendName) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = UUID.fromString(authentication.getPrincipal().toString());
    List<Friend> viewResult = friendView.findFriends(userId, friendName == null ? "" : friendName);
    return ApiResponse.ok(viewResult.stream().map(FindFriendsResponse::fromView).toList());
  }//cqrs


}
