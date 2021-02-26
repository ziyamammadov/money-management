//package com.ziya.moneymanagement.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import javax.mail.internet.MimeMessage;
//
//@Component
//public class EmailSender {
//    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
//    private final JavaMailSender javaMailSender;
//
//    public EmailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//
//    public void sendPlainText(String to, String subject, String text) {
//        try {
//            MimeMessage mail = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//            helper.setTo(to);
//            helper.setFrom("mmmdov.ziya@mail.ru");
//            helper.setSubject(subject);
//            helper.setText(text);
//            javaMailSender.send(mail);
//            LOGGER.info("Send email '{}' to: {}", subject, to);
//        } catch (Exception e) {
//            LOGGER.error(String.format("Problem with sending email to: %s, error message: %s", to, e.getMessage()));
//        }
//    }
//}
