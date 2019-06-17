package com.example.gyeol_web1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    @Column(nullable = false, length=100, unique = true)
    @JsonProperty
    private String userId;
    @JsonIgnore
    private String password;
    @JsonProperty
    private String nickname;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // getter 를 쓰지않고 id 객체에 접근하는법 - 메세지를 보낸다.
    public boolean matchId(Long newId) {
        if (newId == null) {
            return false;
        }
        return newId.equals(getId());
    }

    // getter 를 쓰지않고 password 객체에 접근하는법 - 메세지를 보낸다.
    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }
        return newPassword.equals(password);
    }




    public void update(User newUser) {
        this.nickname = newUser.nickname;
        this.password = newUser.password;
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }


}
