package com.orange.saltybread.domain.services.verifySessions;

import com.orange.saltybread.adapters.service.SignatureService;
import com.orange.saltybread.domain.aggregates.verifySessions.EmailSession;
import com.orange.saltybread.domain.errors.EmailVerificationFailedException;
import com.orange.saltybread.domain.errors.SignatureGenerationException;
import com.orange.saltybread.domain.ports.repositories.EmailSessionRepository;
import com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification.ChallengeEmailVerificationCommand;
import com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification.ChallengeEmailVerificationResponse;
import com.orange.saltybread.domain.ports.usecases.verifySessions.challengeEmailVerification.ChallengeEmailVerificationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChallengeEmailVerificationService implements ChallengeEmailVerificationUseCase {

    private final EmailSessionRepository emailSessionRepository;
    private final SignatureService signatureService;

    @Transactional
    @Override
    public ChallengeEmailVerificationResponse challengeEmailVerification(
            ChallengeEmailVerificationCommand command) throws EmailVerificationFailedException {
        Optional<EmailSession> optionalEmailSession = emailSessionRepository.findById(
                command.emailSessionId());
        if (optionalEmailSession.isEmpty()) {
            throw new EmailVerificationFailedException();
        }
        EmailSession emailSession = optionalEmailSession.get();
        if (emailSession.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new EmailVerificationFailedException();
        }
        try {
            return new ChallengeEmailVerificationResponse(signatureService.generateSignature(emailSession.getEmail()));
        } catch (SignatureGenerationException e) {
            throw new EmailVerificationFailedException();
        }
    }

}
