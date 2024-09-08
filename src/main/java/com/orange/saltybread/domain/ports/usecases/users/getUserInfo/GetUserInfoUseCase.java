package com.orange.saltybread.domain.ports.usecases.users.getUserInfo;

import com.orange.saltybread.domain.errors.UserNotFoundException;
import java.util.UUID;

public interface GetUserInfoUseCase {

  public GetUserInfoResponse getUserInfo(UUID userId) throws UserNotFoundException;
}
