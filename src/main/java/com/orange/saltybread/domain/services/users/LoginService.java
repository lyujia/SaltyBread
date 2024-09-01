package com.orange.saltybread.domain.services.users;

import com.orange.saltybread.adapters.service.JWTService;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.LoginFailException;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.users.login.LoginCommand;
import com.orange.saltybread.domain.ports.usecases.users.login.LoginUseCase;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService implements LoginUseCase {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;
  private final JWTService tokenService;

  @Autowired
  public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JWTService tokenService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  @Override
  @Transactional
  public String login(LoginCommand command) throws LoginFailException {
    Optional<User> optionalUser = userRepository.findByEmail(command.email());
    if (optionalUser.isEmpty()) {
      throw new LoginFailException();
    }
    User user = optionalUser.get();
    if (!passwordEncoder.matches(command.password(), user.getPassword())) {
      throw new LoginFailException();
    }
    user.addLoginHistory(command.ipAddress(), command.userAgent());
    userRepository.save(user);
    return tokenService.createAccessToken(user);
  }


}
