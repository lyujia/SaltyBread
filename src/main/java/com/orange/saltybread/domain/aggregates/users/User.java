package com.orange.saltybread.domain.aggregates.users;

import com.orange.saltybread.domain.aggregates.base.AbstractEntity;
import com.orange.saltybread.domain.aggregates.friends.Friend;
import com.orange.saltybread.domain.aggregates.messages.Message;
import com.orange.saltybread.domain.errors.InvalidEmailFormatException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User extends AbstractEntity {

    private String email;
    private String name;
    private String password;
    private String resetPasswordToken;
    private LocalDateTime lastLogin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "friend")
    private List<Friend> friends = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<LoginHistory> loginHistories = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();

    protected User(String email, String password, String name) {
        super();
        this.loginHistories = new ArrayList<>();
        this.email = email;
        this.password = password;
        this.name = name;
        this.resetPasswordToken = null;
        this.lastLogin = null;
    }

    public void updateInfo(String name) {
        this.name = name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        if (!EmailValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException();
        }
        this.email = email;
    }

    public void addLoginHistory(String ipAddress, String userAgent) {
        this.lastLogin = LocalDateTime.now();
        this.loginHistories.add(new LoginHistory(this, ipAddress, userAgent, this.lastLogin));
    }

    public static User register(String email, String password, String name)
            throws InvalidEmailFormatException {
        if (!EmailValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException();
        }
        return new User(email, password, name);
    }

}
