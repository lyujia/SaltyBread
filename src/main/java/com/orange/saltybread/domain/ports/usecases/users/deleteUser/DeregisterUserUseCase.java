package com.orange.saltybread.domain.ports.usecases.users.deleteUser;

import com.orange.saltybread.domain.errors.UserNotFoundException;
import java.util.UUID;

public interface DeregisterUserUseCase {

  public DeregisterUserResponse deregisterUser(UUID userId) throws UserNotFoundException;
}
