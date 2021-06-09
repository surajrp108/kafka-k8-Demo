package com.srp.users.service;

import java.util.Map;

public interface NotificationService {

    void send(String message);

    void send(String template, Map<String, String> paramValues);
}
