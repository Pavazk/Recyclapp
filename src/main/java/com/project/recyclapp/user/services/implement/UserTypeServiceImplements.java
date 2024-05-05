package com.project.recyclapp.user.services.implement;

import com.project.commons.Utils;
import com.project.commons.exceptions.CustomException;
import com.project.commons.messages.ErrorMessage;
import com.project.recyclapp.user.models.UserType;
import com.project.recyclapp.user.repository.UserTypeRepository;
import com.project.recyclapp.user.services.interfaces.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.commons.Utils.noDataValid;

@Service
public class UserTypeServiceImplements implements UserTypeService {

    @Autowired
    private UserTypeRepository repository;

    @Override
    public UserType createUserType(UserType userType) {
        if (userType == null) {
            throw noDataValid();
        }
        return repository.save(userType);
    }

    @Override
    public UserType getUserTypeById(Integer id) {
        if (id == null) {
            throw noDataValid();
        }
        Optional<UserType> userType = repository.findById(id);
        if (userType.isEmpty()) {
            throw new CustomException(ErrorMessage.USER_TYPE_NO_EXISTS.getMessage());
        }
        return userType.get();
    }

    @Override
    public UserType updateUserType(UserType newUserType, Integer id) {
        if (id == null) {
            throw noDataValid();
        }
        Optional<UserType> userType = repository.findById(id);
        if (userType.isEmpty()) {
            throw new CustomException(ErrorMessage.USER_TYPE_NO_EXISTS.getMessage());
        }
        if (Utils.isNotNullOrEmptyWithTrim(userType.get().getName())) {
            userType.get().setName(newUserType.getName());
        }
        return repository.save(userType.get());
    }

    @Override
    public void deleteUserType(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserType> getAllUserType() {
        return (List<UserType>) repository.findAll();
    }
}
