package com.orange.saltybread.adapters.service;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {

  private final String emailHost;
  private final String emailUsername;
  private final String emailPassword;
  private final int emailPort;

  public void send(String receiver, String title, String body) {
    //SMTP 서버의 속성 설정
    Properties props = new Properties();
    props.put("mail.smtp.host", emailHost);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", emailPort);
    props.put("mail.smtp.starttls.enable", "true"); // STARTTLS 활성화
    //세션 설정
    Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(emailUsername, emailPassword);
      }
    });
    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(emailUsername));
      Address to = new InternetAddress(receiver);
      message.setRecipient(Message.RecipientType.TO, to);
      message.setSubject(title);
      message.setText(body, "UTF-8", "html");

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
