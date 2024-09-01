package com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification;

public interface RequestEmailVerificationUseCase {

  public RequestEmailVerificationResponse requestEmailVerification(
      RequestEmailVerificationCommand command);
}
