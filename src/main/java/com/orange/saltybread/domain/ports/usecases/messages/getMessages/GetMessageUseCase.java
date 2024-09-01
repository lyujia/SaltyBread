package com.orange.saltybread.domain.ports.usecases.messages.getMessages;

import java.util.List;

public interface GetMessageUseCase {

  public List<GetMessageResponse> getMessages(GetMessageCommand command);
}
