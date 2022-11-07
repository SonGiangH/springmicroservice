package com.shopme.notificationservice.service;

import com.shopme.notificationservice.model.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

public interface EmailService {
    void sendEmail(MessageDTO messageDTO);
}

@Service
class EmailServiceImpl implements EmailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Override
    public void sendEmail(MessageDTO messageDTO) {
        try {
            logger.info("Start...sending email");
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            // Load template email with content
            Context context = new Context();
            context.setVariable("name", messageDTO.getToName());
            context.setVariable("content", messageDTO.getContent());
            String html = springTemplateEngine.process("welcome-email", context);

            // send email
            ///send email
            helper.setTo(messageDTO.getTo());
            helper.setText(html, true); // support html = true
            helper.setSubject(messageDTO.getSubject());
            helper.setFrom(messageDTO.getFrom());
            javaMailSender.send(message);

            logger.info("END... Email sent success");
        } catch (MessagingException e) {
            logger.error("Email sent with error: " + e.getMessage());
        }
    }
}
