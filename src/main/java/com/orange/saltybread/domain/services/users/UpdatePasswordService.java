package com.orange.saltybread.domain.services.users;

import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.PasswordNotMatchException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.users.updatePassword.UpdatePasswordCommand;
import com.orange.saltybread.domain.ports.usecases.users.updatePassword.UpdatePasswordResponse;
import com.orange.saltybread.domain.ports.usecases.users.updatePassword.UpdatePasswordUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePasswordService implements UpdatePasswordUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Autowired
  public UpdatePasswordService(UserRepository userRepository, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  @Override
  @Transactional
  public UpdatePasswordResponse updatePassword(UpdatePasswordCommand command)
      throws UserNotFoundException, PasswordNotMatchException {
    Optional<User> optionalUser = userRepository.findById(command.userId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException();
    }
    User user = optionalUser.get();
    if (!encoder.matches(command.password(), user.getPassword())) {
      throw new PasswordNotMatchException();//비밀번호 다름
    }
    user.updatePassword(encoder.encode(command.newPassword()));
    userRepository.save(user);
    return new UpdatePasswordResponse("비밀번호 변경 성공");//비밀번호동일
  }
}
