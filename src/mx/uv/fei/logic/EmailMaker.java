package mx.uv.fei.logic;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailMaker {
    public void sendPassword(String destinationEmail, String password){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(prop, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("spger", "S13q,V&tIg");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("spger69@gmail.com"));
            message.setRecipients(
              Message.RecipientType.TO, InternetAddress.parse(destinationEmail));
            message.setSubject("Quíubo");

            String msg = "Esta es tu contraseña para ingresar al sistema: " + password;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("path/to/file"));
            multipart.addBodyPart(attachmentBodyPart);

            String msgStyled = "This is my <b style='color:red;'>bold-red email</b> using JavaMailer";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
