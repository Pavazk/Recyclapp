package com.project.recyclapp.commons;

import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.RegisterEvent;
import com.project.recyclapp.modules.users.models.User;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailManager {

    public static void sendEmailInviteEvent(Event event, List<User> participants){
        new Thread(() -> {
            try {
                final String email = "yosoypav4@gmail.com";
                final String password = "lzlm msoo kspd yogy";
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
                for(User user : participants){
                    // Create email
                    MimeMessage mCorreo = new MimeMessage(mSession);
                    mCorreo.setFrom(new InternetAddress(email));
                    mCorreo.setSubject("Invitación al evento \""+event.getName()+"\" ");
                    mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                    mCorreo.setText(getStyledEmailBody(event, user.getName()), "ISO-8859-1", "html");
                    // Send email
                    Transport mTransport = mSession.getTransport("smtp");
                    mTransport.connect(email, password);
                    mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
                    mTransport.close();
                    System.out.println("Fue enviado el correo a: "+ user.getEmail());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private static String getStyledEmailBody(Event event, String recipientName) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body {" +
                "   font-family: Arial, sans-serif;" +
                "   background-color: #f4f4f4;" +
                "   padding: 20px;" +
                "   max-width: 600px;" +
                "   margin: 0 auto;" +
                "   border-radius: 10px;" +
                "   box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);" +
                "}" +
                "h1 {" +
                "   color: #333333;" +
                "   text-align: center;" +
                "}" +
                "p {" +
                "   color: #555555;" +
                "   text-align: justify;" +
                "}" +
                "a {" +
                "   color: #007bff;" +
                "   text-decoration: none;" +
                "}" +
                ".btn {" +
                "   display: inline-block;" +
                "   padding: 10px 20px;" +
                "   background-color: #007bff;" +
                "   color: #ffffff;" +
                "   border-radius: 5px;" +
                "   text-decoration: none;" +
                "}" +
                ".btn:hover {" +
                "   background-color: #0056b3;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Nuevo evento disponible</h1>" +
                "<p>¡Hola " + recipientName + "!</p>" +
                "<p>Te informamos que " + event.getOwner().getName() + " te ha invitado a participar en su nuevo evento. ¡No te lo pierdas!</p>" +
                "<p>Para más detalles sobre el evento, por favor ingresa a nuestra aplicación.</p>" +
                "<p>Si tienes alguna pregunta o necesitas asistencia, no dudes en ponerte en contacto con nuestro equipo de soporte.</p>" +
                "<p>¡Esperamos que disfrutes del evento y que juntos podamos hacer del mundo un lugar mejor!</p>" +
                "<p>Gracias por usar nuestro servicio.</p>" +
                "</body>" +
                "</html>";
    }


    public static void sendEmailCreateUser(User user) {
        try {
            final String email = "yosoypav4@gmail.com";
            final String password = "lzlm msoo kspd yogy";
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
            System.out.println("Fue enviado el correo a: "+ user.getEmail());
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
