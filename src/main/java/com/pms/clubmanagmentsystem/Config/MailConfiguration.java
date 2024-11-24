//package com.pms.clubmanagmentsystem.Config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfiguration {
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("<EMAIL>");
//        mailSender.setPassword("<PASSWORD>");
//        Properties properties = JavaMailSender.getProperties();
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        return mailSender;
//    }
//}
