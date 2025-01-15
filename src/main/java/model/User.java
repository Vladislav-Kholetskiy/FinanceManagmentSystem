package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class User {
    private String username;
    private String password;
    private Wallet wallet;

    public User(String username, String passwordHash) {
        this.username = Objects.requireNonNull(username, "Username cannot be null");
        this.password = Objects.requireNonNull(passwordHash, "Password cannot be null");
        this.wallet = new Wallet();
    }

}
