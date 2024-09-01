package com.orange.saltybread.domain.ports.usecases.users.login;

import com.orange.saltybread.domain.errors.LoginFailException;

public interface LoginUseCase {

  public String login(LoginCommand command) throws LoginFailException;
}
