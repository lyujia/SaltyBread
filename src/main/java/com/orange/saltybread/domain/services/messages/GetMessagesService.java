package com.orange.saltybread.domain.services.messages;

import com.orange.saltybread.domain.ports.repositories.MessageRepository;
import com.orange.saltybread.domain.ports.usecases.messages.getMessages.GetMessageCommand;
import com.orange.saltybread.domain.ports.usecases.messages.getMessages.GetMessageResponse;
import com.orange.saltybread.domain.ports.usecases.messages.getMessages.GetMessageUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetMessagesService implements GetMessageUseCase {

  MessageRepository messageRepository;

  @Override
  public List<GetMessageResponse> getMessages(GetMessageCommand command) {
    //roomId로 모든 메세지들 가져온다.
    //return 리스트를 만든ㄷ다.
    return null;
  }
}
