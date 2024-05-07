package com.project.recyclapp.users.models.update;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdate {
    private String email;//M
    private String oldPassword;//M
    private String newCode;//O
    private String newPassword;//O
    private String newPasswordConfirmation;//O
}
