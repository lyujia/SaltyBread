package com.orange.saltybread.domain.services.verifySessions;

import com.orange.saltybread.adapters.service.EmailService;
import com.orange.saltybread.domain.aggregates.verifySessions.EmailSession;
import com.orange.saltybread.domain.ports.repositories.EmailSessionRepository;
import com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification.RequestEmailVerificationCommand;
import com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification.RequestEmailVerificationResponse;
import com.orange.saltybread.domain.ports.usecases.verifySessions.requestEmailVerification.RequestEmailVerificationUseCase;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RequestEmailVerificationService implements RequestEmailVerificationUseCase {

  private final EmailSessionRepository emailSessionRepository;
  private final EmailService emailService;
  private final TemporalAmount emailExpiration;
  private final String emailChallengeUrl;

  @Transactional
  @Override
  public RequestEmailVerificationResponse requestEmailVerification(
      RequestEmailVerificationCommand command) {

    EmailSession emailSession = new EmailSession(command.email(),
        LocalDateTime.now(), emailExpiration);
    emailSessionRepository.save(emailSession);
    emailService.send(command.email(), "SaltyBread 회원가입 이메일 인증",
        "<a href=\"" + emailChallengeUrl + emailSession.getId().toString()
            + "\">이 링크를 눌러 이메일 인증을 완료해주세요.</a>");
    return new RequestEmailVerificationResponse();
  }
}
