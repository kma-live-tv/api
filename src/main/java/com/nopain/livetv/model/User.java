package com.nopain.livetv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends ApplicationModel {
    @NotBlank
    @Size(min = 6, max = 20)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Size(min = 6, max = 30)
    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private Long balance = 0L;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Livestream> livestreams;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "followTo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Follower> followers;

    @OneToMany(mappedBy = "followedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follower> followings;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
