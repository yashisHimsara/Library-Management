package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String userId;
    private String userName;
    private String email;
    private String password;
}
