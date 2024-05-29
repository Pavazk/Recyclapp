package com.project.recyclapp.modules.users.services;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.commons.messages.Defines;
import com.project.recyclapp.commons.messages.ErrorMessage;
import com.project.recyclapp.modules.users.models.login.UserLogin;
import com.project.recyclapp.modules.users.models.update.UserUpdate;
import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.models.UserType;
import com.project.recyclapp.modules.users.repository.UserRepository;
import com.project.recyclapp.modules.users.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

import static com.project.recyclapp.commons.Utils.*;

@Service
public class UserServiceImplements implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public User registerUser(User user) {
        if (user == null) {
            throw noDataValid();
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS_EMAIL.getMessage());
        }
        if (userRepository.findByCode(user.getCode()).isPresent()) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS_CODE.getMessage());
        }
        user.setUserType(findUserTypeByCode(user.getCode(), userTypeRepository));
        user.setPassword(Utils.generatePassword());
        User userCreated = userRepository.save(user);
        new Thread(() ->
                Utils.sendEmailCreateUser(userCreated)).start();
        return userCreated;
    }

    @Override
    public User updateUser(UserUpdate user) {
        if (user == null) {
            throw Utils.noDataValid();
        }
        if (isNullOrEmptyWithTrim(user.getOldPassword()) || isNullOrEmptyWithTrim(user.getEmail())) {
            throw Utils.noDataValid();
        }
        Optional<User> oldUser = userRepository.findByEmail(user.getEmail());
        if (oldUser.isEmpty()) {
            throw new CustomException(ErrorMessage.USER_NO_EXISTS.getMessage());
        }
        if (!oldUser.get().getPassword().equals(user.getOldPassword())) {
            throw new CustomException("Contraseña incorrecta");
        }
        if (isNotNullOrEmptyWithTrim(user.getNewPassword()) && isNotNullOrEmptyWithTrim(user.getNewPasswordConfirmation())) {
            if (!user.getNewPassword().equals(user.getNewPasswordConfirmation())) {
                throw new CustomException("Contraseñas no coinciden");
            }
            oldUser.get().setPassword(user.getNewPassword());
        }
        if (isNotNullOrEmptyWithTrim(user.getNewCode())) {
            oldUser.get().setCode(user.getNewCode());
            oldUser.get().setUserType(findUserTypeByCode(user.getNewCode(), userTypeRepository));
        }
        return userRepository.save(oldUser.get());
    }

    @Override
    public User loginUser(UserLogin userLogin) {
        if(userLogin == null){
            throw noDataValid();
        }
        Optional<User> user = userRepository.findByEmail(userLogin.getEmail());
        if(isNullOrEmptyWithTrim(userLogin.getEmail()) || isNullOrEmptyWithTrim(userLogin.getPassword()) || user.isEmpty()){
            throw noDataValid();
        }
        String password = userLogin.getPassword();
        if(user.get().getPassword().length() > 10) {
            password = SHA256(password);
        }
        if(!user.get().getPassword().equals(password)){
            throw new CustomException("Credenciales incorrectas");
        }
        return user.get();
    }


    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            throw noDataValid();
        }
        Optional<User> user = userRepository.findById(email);
        if (user.isEmpty()) {
            throw new CustomException(ErrorMessage.USER_NO_EXISTS.getMessage());
        }
        return user.get();
    }

    @Override
    public User updateUser(User newUser, String email) {
        if (newUser == null || !isNotNullOrEmptyWithTrim(email)) {
            throw noDataValid();
        }
        if (userTypeRepository.findById(newUser.getUserType().getId()).isEmpty()) {
            throw new CustomException(ErrorMessage.USER_TYPE_NO_EXISTS.getMessage());
        }
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isEmpty()) {
            throw new CustomException(ErrorMessage.USER_NO_EXISTS.getMessage());
        }
        User userToUpdate = userOptional.get();
        if (isNotNullOrEmptyWithTrim(newUser.getPassword())) {
            userToUpdate.setPassword(newUser.getPassword());
        }
        if (isNotNullOrEmptyWithTrim(String.valueOf(newUser.getUserType()))) {
            userToUpdate.setUserType(newUser.getUserType());
        }
        return userRepository.save(userToUpdate);

    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByCode(String code) {
        return userRepository.findByCodeContaining(code);
    }

    public List<User> getAllStudents() {
        UserType userType = new UserType();
        userType.setName(Defines.ROLE_STUDENT.getMessage());
        return (List<User>) userRepository.findByUserType(userType);
    }
    public static String SHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
