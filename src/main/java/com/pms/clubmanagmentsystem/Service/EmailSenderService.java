//package com.pms.clubmanagmentsystem.Service;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Service;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Date;
//import java.util.Properties;
//
//@Service
//public class EmailSenderService {
//
//    public void sendEmail(String to, String subject, String body) {
//        String host = "smtp.gmail.com";
//        String from = "habeeb.fayez@gmail.com";
//        String password = "fvaiuzwyaisrqbzp"; // Replace with your Gmail password
//
//        // Create properties, get Session
//        Properties props = new Properties();
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.smtp.auth", "true"); // Enable authentication
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//
//        try {
//            // Instantiate a message
//            Message msg = new MimeMessage(session);
//
//            // Set message attributes
//            msg.setFrom(new InternetAddress(from));
//            InternetAddress[] address = {new InternetAddress(to)};
//            msg.setRecipients(Message.RecipientType.TO, address);
//            msg.setSubject(subject);
//            msg.setSentDate(new Date());
//
//            // Set message content
//            msg.setText(body);
//
//            // Send the message
//            Transport.send(msg);
//        } catch (MessagingException mex) {
//            // Prints all nested (chained) exceptions as well
//            mex.printStackTrace();
//        }
//    }
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("habeeb.fayez@gmail.com");
//        mailSender.setPassword("fvaiuzwyaisrqbzp");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//}
