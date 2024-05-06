package com.project.recyclapp.users.models.login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLogin {
    private String email;
    private String password;
}
