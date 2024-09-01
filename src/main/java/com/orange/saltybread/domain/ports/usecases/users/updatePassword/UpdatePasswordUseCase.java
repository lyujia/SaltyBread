package com.orange.saltybread.domain.ports.usecases.users.updatePassword;

import com.orange.saltybread.domain.errors.PasswordNotMatchException;
import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface UpdatePasswordUseCase {

  public UpdatePasswordResponse updatePassword(UpdatePasswordCommand command)
      throws UserNotFoundException, PasswordNotMatchException;
}
