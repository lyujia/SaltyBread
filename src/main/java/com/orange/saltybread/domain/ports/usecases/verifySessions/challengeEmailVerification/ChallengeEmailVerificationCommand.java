package com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification;

import java.util.UUID;

public record ChallengeEmailVerificationCommand(UUID emailSessionId) {

}
