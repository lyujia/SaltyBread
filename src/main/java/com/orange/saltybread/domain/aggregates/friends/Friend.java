package com.orange.saltybread.domain.aggregates.friends;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.CannotAddMySelfException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "friends")
@Getter
public class Friend extends AbstractEntity {

  private UUID userId;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "friendId")
  private User friend;

  public Friend(UUID userId, User friend) throws CannotAddMySelfException {
    super();
    if (userId.equals(friend.getId())) {
      throw new CannotAddMySelfException();
    }
    this.userId = userId;
    this.friend = friend;
  }
}
