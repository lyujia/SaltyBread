package com.orange.saltybread.domain.services.users;

import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.users.getUserInfo.GetUserInfoResponse;
import com.orange.saltybread.domain.ports.usecases.users.getUserInfo.GetUserInfoUseCase;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserInfoService implements GetUserInfoUseCase {

  private final UserRepository userRepository;

  @Override
  public GetUserInfoResponse getUserInfo(UUID userId) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }
    return new GetUserInfoResponse(user.get().getName(), user.get().getEmail());
  }
}
