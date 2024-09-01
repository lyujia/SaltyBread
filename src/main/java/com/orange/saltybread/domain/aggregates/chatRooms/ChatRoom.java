package com.orange.saltybread.domain.aggregates.chatRooms;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import com.orange.saltybread.domain.aggregates.users.User;
import com.orange.saltybread.domain.errors.CannotCreateRoomWithEmptyFriendListException;
import com.orange.saltybread.domain.errors.UserAlreadyInvitedException;
import com.orange.saltybread.domain.errors.UserNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Entity
@Table(name = "chatRooms")
@Getter
public class ChatRoom extends AbstractEntity {

    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatRoomUserMapping> mappings;

    private LocalDateTime lastChatTime;

    public ChatRoom(String title, User user, List<User> friends)
            throws CannotCreateRoomWithEmptyFriendListException {
        super();
        this.title = title;
        this.lastChatTime = null;
        if (friends.isEmpty()) {
            throw new CannotCreateRoomWithEmptyFriendListException();
        }
        this.mappings = new ArrayList<>(
                friends.stream().map(friend -> new ChatRoomUserMapping(this, friend)).toList());
        this.mappings.add(new ChatRoomUserMapping(this, user));
    }

    public void addUser(User user) throws UserAlreadyInvitedException {
        if (mappings.stream().anyMatch(mapping -> mapping.getUser().equals(user))) {
            throw new UserAlreadyInvitedException();
        }
        mappings.add(new ChatRoomUserMapping(this, user));
    }

    public void removeUser(User user) {
        Optional<ChatRoomUserMapping> mapping = mappings.stream().filter(m -> m.getUser().equals(user))
                .findFirst();
        if (mapping.isEmpty()) {
            throw new UserNotFoundException();
        }
        mappings.remove(mapping.get());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLastChatTime() {
        this.lastChatTime = LocalDateTime.now();
    }
}
