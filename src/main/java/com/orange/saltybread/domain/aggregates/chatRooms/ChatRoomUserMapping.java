package com.orange.saltybread.domain.aggregates.chatRooms;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import com.orange.saltybread.domain.aggregates.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomUserMapping extends AbstractEntity {

  @ManyToOne(targetEntity = ChatRoom.class)
  @JoinColumn(name = "roomId")
  private ChatRoom chatRoom;
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "userId")
  private User user;

}
