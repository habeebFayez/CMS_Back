package com.pms.clubmanagmentsystem;


import com.pms.clubmanagmentsystem.Entity.Club;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.Date;

import java.sql.Date;

// Send a simple, single part, text/plain e-mail
public class TestEmail {

    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String pass = passwordEncoder.encode("Aa.12345");
//        boolean passw = passwordEncoder.matches("Aa.12345", "$2a$10$OW1NQbp55f6VX.s2LDBuuuCfKcf6dpJq9dpOZzoSVyP4oH1VT.a9O");
//        System.out.println(pass);
//        System.out.println(passw);
        System.out.println(new Date(System.currentTimeMillis()));
    }
}
//
//    // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
//    String to = "habeebalrahmanfayezahmad.almseideen@st.uskudar.edu.tr";
//    String from = "habeeb.fayez@gmail.com";
//    // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
//    String host = "smtp.gmail.com";
//
//    // Create properties, get Session
//    Properties props = new Properties();

// If using static Transport.send(),
// need to specify which host to send it to
//        props.put("mail.smtp.host", host);
//                props.put("spring.mail.properties.mail.transport.protocol", "smtp");
//                props.put("mail.smtp.port", "587");
//                props.put("mail.smtp.starttls.enable", "true");
//                props.put("mail.debug", "true");
//                props.put("mail.smtp.auth", "true");
//                props.put("spring.mail.username","habeeb.fayez@gmail.com");
//                props.put("spring.mail.password", "fvaiuzwyaisrqbzp");
//
//                Session session = Session.getInstance(props, new Authenticator() {
//protected PasswordAuthentication getPasswordAuthentication() {
//        return new PasswordAuthentication(from,  "fvaiuzwyaisrqbzp");
//        }
//        });
//
//
//        try {
//        // Instantiatee a message
//        Message msg = new MimeMessage(session);
//
//        //Set message attributes
//        msg.setFrom(new InternetAddress(from));
//        InternetAddress[] address = {new InternetAddress(to)};
//        msg.setRecipients(Message.RecipientType.TO, address);
//        msg.setSubject("Test E-Mail through Java");
//        msg.setSentDate(new Date());
//
//        // Set message content
//        msg.setText("This is a test of sending a " +
//        "plain text e-mail through Java.\n" +
//        "Here is line 2.");
//
//        //Send the message
//        Transport.send(msg);
//        }
//        catch (MessagingException mex) {
//        // Prints all nested (chained) exceptions as well
//        mex.printStackTrace();
//        }
//        }