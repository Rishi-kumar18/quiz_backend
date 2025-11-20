package edu.annauniv.dist.webtech.quiz_backend.Controllers;

import edu.annauniv.dist.webtech.quiz_backend.Views.NotificationView;
import edu.annauniv.dist.webtech.quiz_backend.Views.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(MarksController.class);

    @Autowired
    NotificationView notificationView;
    @Autowired
    private email email;

    @PostMapping("/send")
    public String sendNotification(@RequestBody Map<String, String> payload) {
        log.info("Request /notifications/send token=", payload.get("token"));
        String token = payload.get("token");
        String title = payload.get("title");
        String body = payload.get("body");
        String semail = payload.get("email");
        email.sendEmail("rk18102004@gmail.com","You have a new Test","Attend the Test without fail");
        return notificationView.sendPushNotification(token, title, body);
    }
}