package com.nopain.livetv.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
}
