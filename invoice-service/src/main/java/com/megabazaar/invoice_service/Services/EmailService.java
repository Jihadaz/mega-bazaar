package com.megabazaar.invoice_service.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendInvoiceEmail(String to, String subject, String text) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text);
//
//            mailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendInvoiceEmail(String to, String subject, String text) {
        logger.info("Preparing to send email to: {}", to);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            logger.info("Sending email to: {} with subject: {}", to, subject);
            mailSender.send(mimeMessage);
            logger.info("Email successfully sent to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}", to, e);
        }
    }
}
