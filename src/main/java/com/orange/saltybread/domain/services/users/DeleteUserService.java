package com.orange.saltybread.domain.services.users;

import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.users.deleteUser.DeregisterUserResponse;
import com.orange.saltybread.domain.ports.usecases.users.deleteUser.DeregisterUserUseCase;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserService implements DeregisterUserUseCase {

  private final UserRepository userRepository;

  @Autowired
  public DeleteUserService(UserRepository userRepository) {
    this.userRepository = userRepository;

  }

  @Transactional
  @Override
  public DeregisterUserResponse deregisterUser(UUID userId) throws UserNotFoundException {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException();
    }
    userRepository.delete(optionalUser.get());

    return new DeregisterUserResponse("회원 탈퇴 성공");
  }
}
