package com.example.isana.models;

public class UserUpdate {
    private String email;
    private String oldPassword;
    private String newCode;
    private String newPassword;
    private String newPasswordConfirmation;

    public UserUpdate(String email, String oldPassword, String newCode, String newPassword, String newPasswordConfirmation) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newCode = newCode;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public UserUpdate(String email, String oldPassword, String newPassword, String newPasswordConfirmation) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public UserUpdate(String email, String newCode) {
        this.email = email;
        this.newCode = newCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
