package com.srp.users.service.impl;

import com.srp.users.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Map;

@ApplicationScoped
@Named("EmailService")
public class EmailServiceImpl implements NotificationService {
    private Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void send(String message) {
        log.info("Received notification for email with text: " + message);
    }

    @Override
    public void send(String templateName, Map<String, String> paramValues) {
        log.info("Received notification for email with template text: " + templateName);
    }
}
