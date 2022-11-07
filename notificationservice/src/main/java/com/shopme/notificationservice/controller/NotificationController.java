package com.shopme.notificationservice.controller;

import com.shopme.notificationservice.model.MessageDTO;
import com.shopme.notificationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    private EmailService service;

    @PreAuthorize("#oauth2.hasScope('notification')")
    @PostMapping("/send_notification")
    public void sendEmail(@RequestBody MessageDTO messageDTO) {
        service.sendEmail(messageDTO);
    }
}
