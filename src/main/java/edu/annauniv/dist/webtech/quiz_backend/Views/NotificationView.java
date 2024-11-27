package edu.annauniv.dist.webtech.quiz_backend.Views;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationView {

    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    public String sendPushNotification(String expoPushToken, String title, String body) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        Map<String, Object> notification = new HashMap<>();
        notification.put("to", expoPushToken);
        notification.put("title", title);
        notification.put("body", body);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(notification, headers);

        ResponseEntity<String> response = restTemplate.exchange(EXPO_PUSH_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}