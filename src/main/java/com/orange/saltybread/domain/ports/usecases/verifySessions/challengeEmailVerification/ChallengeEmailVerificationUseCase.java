package com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification;

import com.orange.saltybread.domain.errors.EmailVerificationFailedException;

public interface ChallengeEmailVerificationUseCase {

  public ChallengeEmailVerificationResponse challengeEmailVerification(
      ChallengeEmailVerificationCommand command) throws EmailVerificationFailedException;
}
