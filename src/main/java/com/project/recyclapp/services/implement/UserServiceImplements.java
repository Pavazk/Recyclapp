package com.project.recyclapp.services.implement;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.models.User;
import com.project.recyclapp.repository.UserRepository;
import com.project.recyclapp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.recyclapp.commons.messages.Defines.EXT_UFPS;

@Service
public class UserServiceImplements implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(User newUser, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            if (Utils.isNotNullOrEmptyWithTrim(newUser.getPhone())) {
                userToUpdate.setPhone(newUser.getPhone());
            }
            if (Utils.isNotNullOrEmptyWithTrim(newUser.getEmail()) && newUser.getEmail().contains(EXT_UFPS.getMessage())) {
                userToUpdate.setEmail(newUser.getEmail());
            }
            if (Utils.isNotNullOrEmptyWithTrim(newUser.getPassword())) {
                userToUpdate.setPassword(newUser.getPassword());
            }
            if (Utils.isNotNullOrEmptyWithTrim(String.valueOf(newUser.getUserType()))) {
                userToUpdate.setUserType(newUser.getUserType());
            }
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

}
