package com.newsilkroad.views.SignUpSignIn;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
