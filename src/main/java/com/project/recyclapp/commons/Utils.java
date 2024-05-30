package com.project.recyclapp.commons;

import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.commons.messages.Defines;
import com.project.recyclapp.commons.messages.ErrorMessage;
import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.models.UserType;
import com.project.recyclapp.modules.users.repository.UserTypeRepository;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

import java.security.SecureRandom;

public class Utils {
    private Utils() {
        //Se crea el constructor privado pues sera una clase que contenga solo metodos estaticos.
    }

    public static boolean isNotNullOrEmptyWithTrim(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isNullOrEmptyWithTrim(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static CustomException noDataValid() {
        return new CustomException(ErrorMessage.DATA_NO_VALID.getMessage());
    }

    public static CustomException throwException(String value) {
        return new CustomException(value);
    }

    public static UserType findUserTypeByCode(String code, UserTypeRepository userTypeRepository) {
        String program = code.substring(0,2);
        String role = switch (program) {
            case "10" -> Defines.ROLE_TEACHER.getMessage();
            case "00" -> Defines.ROLE_ADMIN.getMessage();
            case "19" -> Defines.ROLE_SUPER_ADMIN.getMessage();
            default -> Defines.ROLE_STUDENT.getMessage();
        };
        Optional<UserType> userTypeFind = userTypeRepository.findByName(role);
        if (userTypeFind.isPresent()) {
            return userTypeFind.get();
        }
        UserType userType = new UserType();
        userType.setName(role);
        return userTypeRepository.save(userType);
    }

    public static String generatePassword() {
        final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        final String NUMBER = "0123456789";
        final String SPECIAL_CHAR = "!@#$%&";
        final String ALL_CHAR = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHAR;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);
        boolean hasLower = false, hasUpper = false, hasDigit = false, hasSpecial = false;

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(ALL_CHAR.length());
            char randomChar = ALL_CHAR.charAt(index);
            password.append(randomChar);

            if (CHAR_LOWER.contains(Character.toString(randomChar))) {
                hasLower = true;
            } else if (CHAR_UPPER.contains(Character.toString(randomChar))) {
                hasUpper = true;
            } else if (NUMBER.contains(Character.toString(randomChar))) {
                hasDigit = true;
            } else if (SPECIAL_CHAR.contains(Character.toString(randomChar))) {
                hasSpecial = true;
            }
        }
        if (!hasLower || !hasUpper || !hasDigit || !hasSpecial) {
            return generatePassword(); // Regenerate the password if it doesn't meet the criteria
        }

        return password.toString();
    }
}
