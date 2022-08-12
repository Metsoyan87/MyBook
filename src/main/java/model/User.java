package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class User {
    private String name;
    private String surName;
    private String email;
    private String password;
    private Role role;

}