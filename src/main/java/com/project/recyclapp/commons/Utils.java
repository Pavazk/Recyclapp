package com.project.recyclapp.commons;

import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.commons.messages.Defines;
import com.project.recyclapp.commons.messages.ErrorMessage;
import com.project.recyclapp.users.models.User;
import com.project.recyclapp.users.models.UserType;
import com.project.recyclapp.users.repository.UserTypeRepository;

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


    public static void sendEmailCreateUser(User user) {
        try {
            final String email = "yosoypav4@gmail.com";
            final String password = "bvuj fgir fwht ksqc";
            // Simple mail transfer protocol
            Properties mProperties = new Properties();
            mProperties.put("mail.smtp.host", "smtp.gmail.com");
            mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            mProperties.setProperty("mail.smtp.starttls.enable", "true");
            mProperties.setProperty("mail.smtp.port", "587");
            mProperties.setProperty("mail.smtp.user", email);
            mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            mProperties.setProperty("mail.smtp.auth", "true");
            Session mSession = Session.getDefaultInstance(mProperties);
            // Create email
            MimeMessage mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(email));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mCorreo.setSubject("Registro de usuario Recyclapp");
            mCorreo.setText(getBodyEmailCreateUser(user), "ISO-8859-1", "html");
            // Send email
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(email, password);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getBodyEmailCreateUser(User user){
        return "<html>" +
                "<head>" +
                "<style>" +
                "body {" +
                "   font-family: Arial, sans-serif;" +
                "   background-color: #f4f4f4;" +
                "   padding: 20px;" +
                "}" +
                "h1 {" +
                "   color: #333333;" +
                "}" +
                "p {" +
                "   color: #555555;" +
                "}" +
                "a {" +
                "   color: #007bff;" +
                "   text-decoration: none;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Bienvenido a Recyclapp</h1>" +
                "<p>¡Gracias por registrarte en Recyclapp! Ahora formas parte de nuestra comunidad dedicada a fomentar el reciclaje y cuidado del medio ambiente.</p>" +
                "<p>A continuación, te proporcionamos tus credenciales para acceder a la aplicación:</p>" +
                "<p><strong>Usuario:</strong> " + user.getEmail() + "</p>" +
                "<p><strong>Contraseña:</strong> " + user.getPassword() + "</p>" +
                "<p>Por favor, guarda esta información en un lugar seguro.</p>" +
                "<p>Si tienes alguna pregunta o necesitas asistencia, no dudes en ponerte en contacto con nuestro equipo de soporte.</p>" +
                "<p>¡Esperamos que disfrutes de nuestra aplicación y que juntos podamos hacer del mundo un lugar más limpio y sostenible!</p>" +
                "<p><a>Gracias por usar nuestro servicio</a></p>" +
                "</body>" +
                "</html>";
    }
}
