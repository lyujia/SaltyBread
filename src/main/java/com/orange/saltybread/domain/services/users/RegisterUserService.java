package com.orange.saltybread.domain.services.users;

import com.orange.saltybread.adapters.service.SignatureService;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.EmailAlreadyExistsException;
import com.orange.saltybread.domain.errors.InvalidEmailFormatException;
import com.orange.saltybread.domain.errors.SignatureVerificationException;
import com.orange.saltybread.domain.ports.repositories.UserRepository;
import com.orange.saltybread.domain.ports.usecases.users.registerUser.RegisterUserCommand;
import com.orange.saltybread.domain.ports.usecases.users.registerUser.RegisterUserResponse;
import com.orange.saltybread.domain.ports.usecases.users.registerUser.RegisterUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserService implements RegisterUserUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SignatureService signatureService;

  @Autowired
  public RegisterUserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      SignatureService signatureService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.signatureService = signatureService;

  }


  @Override
  @Transactional
  public RegisterUserResponse registerUser(RegisterUserCommand command)
      throws EmailAlreadyExistsException, InvalidEmailFormatException, SignatureVerificationException {
//        if (!signatureService.verifySignature(command.email(), command.signature())) {
//            throw new SignatureVerificationException();
//        }
    if (userRepository.findByEmail(command.email()).isPresent()) {
      throw new EmailAlreadyExistsException();
    }
    User user = User.register(command.email(), passwordEncoder.encode(command.password()),
        command.name());
    this.userRepository.save(user);

    return new RegisterUserResponse(user.getId(), user.getName());
  }
}
