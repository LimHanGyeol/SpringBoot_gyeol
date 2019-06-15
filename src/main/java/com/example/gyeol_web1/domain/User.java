package com.example.gyeol_web1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length=100, unique = true)
    private String userId;


    private String password;
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
    public void setId(Long id) {
        this.id = id;
    }
    // getter 를 쓰지않고 password 객체에 접근하는법 - 메세지를 보낸다.
    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }
        return newPassword.equals(password);
    }

    public boolean matchId(Long newId) {
        if (newId == null) {
            return false;
        }
        return newId.equals(id);
    }


    public void update(User newUser) {
        this.nickname = newUser.nickname;
        this.password = newUser.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }


}
