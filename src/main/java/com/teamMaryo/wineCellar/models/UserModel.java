package com.teamMaryo.wineCellar.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public class UserModel {
    @Id
    @Column("USER_ID")
    private Long userId;

    @Column("USERNAME")
    @Size(max=64)
    @NotNull
    private String username;

    @Column("EMAIL")
    @Size(max=254)
    @NotNull
    private String email;

    @Column("PASSWORD")
    @Size(max=254)
    @NotNull
    private String password;

    public UserModel(@Size(max = 64) @NotNull String username, @Size(max = 254) @NotNull String email,
            @Size(max = 254) @NotNull String password) {
        this.setUsername(username);
        this.setEmail(email);
        this.password = password;
    }

    public UserModel() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username.matches("[a-zA-Z0-9 ]+")){
            this.username = username;
        } else {
            this.username = "noname";
        }     
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            this.email= email;
        } else {
            this.email = "nomail@no.com";
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserModel other = (UserModel) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}
