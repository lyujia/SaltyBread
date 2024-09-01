package com.orange.saltybread.domain.services.users;

import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.users.updateUser.UpdateUserInfoCommand;
import com.orange.saltybread.domain.ports.usecases.users.updateUser.UpdateUserInfoResponse;
import com.orange.saltybread.domain.ports.usecases.users.updateUser.UpdateUserInfoUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService implements UpdateUserInfoUseCase {

  private final UserRepository userRepository;

  @Autowired
  public UpdateUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  @Override
  public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoCommand command)
      throws UserNotFoundException {
    Optional<User> optionalUser = userRepository.findById(command.userId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException();
    }
    User user = optionalUser.get();
    user.updateInfo(command.name());
    userRepository.save(user);

    return new UpdateUserInfoResponse(user.getId(), user.getName());
  }
}
