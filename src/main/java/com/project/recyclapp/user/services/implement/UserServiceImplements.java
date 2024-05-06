package com.project.recyclapp.user.services.implement;

import com.project.commons.exceptions.CustomException;
import com.project.commons.messages.ErrorMessage;
import com.project.recyclapp.user.models.User;
import com.project.recyclapp.user.repository.DocumentTypeRepository;
import com.project.recyclapp.user.repository.UserRepository;
import com.project.recyclapp.user.repository.UserTypeRepository;
import com.project.recyclapp.user.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.commons.Utils.*;

@Service
public class UserServiceImplements implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw noDataValid();
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS_EMAIL.getMessage());
        }
        if (userRepository.findByCode(user.getCode()).isPresent()) {
            throw new CustomException(ErrorMessage.ALREADY_EXISTS_CODE.getMessage());
        }
        if (user.getUserType() == null || user.getUserType().getId() == null){
            throw new CustomException("Tipo de usuario no valido");
        }
        if (userTypeRepository.findById(user.getUserType().getId()).isEmpty()) {
            throw new CustomException(ErrorMessage.USER_TYPE_NO_EXISTS.getMessage());
        }
        if (user.getDocumentType() == null || user.getDocumentType().getId() == null){
            throw new CustomException("Tipo de documento no valido");
        }
        if (documentTypeRepository.findById(user.getDocumentType().getId()).isEmpty()) {
            throw new CustomException(ErrorMessage.DOCUMENT_TYPE_NO_EXISTS.getMessage());
        }
        return userRepository.save(user);
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
        if (documentTypeRepository.findById(newUser.getDocumentType().getId()).isEmpty()) {
            throw new CustomException(ErrorMessage.DOCUMENT_TYPE_NO_EXISTS.getMessage());
        }
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isEmpty()) {
            throw new CustomException(ErrorMessage.USER_NO_EXISTS.getMessage());
        }
        User userToUpdate = userOptional.get();
        if (isNotNullOrEmptyWithTrim(newUser.getPhone())) {
            userToUpdate.setPhone(newUser.getPhone());
        }
        if (isNotNullOrEmptyWithTrim(newUser.getPassword())) {
            userToUpdate.setPassword(newUser.getPassword());
        }
        if (isNotNullOrEmptyWithTrim(String.valueOf(newUser.getUserType()))) {
            userToUpdate.setUserType(newUser.getUserType());
        }
        return userRepository.save(userToUpdate);

    }

    @Override
    public void deleteUser(String code) {
        userRepository.deleteById(code);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
