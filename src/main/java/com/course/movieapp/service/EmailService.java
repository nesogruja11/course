package com.course.movieapp.service;

import com.course.movieapp.model.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private MailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.getOrigin());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.getJavaMailSender().send(message);
    }

}