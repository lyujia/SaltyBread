package com.orange.saltybread.domain.ports.usecases.users.updateUser;

import com.orange.saltybread.domain.errors.UserNotFoundException;

public interface UpdateUserInfoUseCase {

  public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoCommand command)
      throws UserNotFoundException;
}
