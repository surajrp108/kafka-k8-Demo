package com.srp.users.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDate;

@Entity
public class UserAuthEntity {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Column
    private String password;

    @Column
    private LocalDate lastLoggedIn;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(LocalDate lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
