package edu.annauniv.dist.webtech.quiz_backend.Controllers;

import edu.annauniv.dist.webtech.quiz_backend.Views.NotificationView;
import edu.annauniv.dist.webtech.quiz_backend.Views.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationView notificationView;
    @Autowired
    private email email;

    @PostMapping("/send")
    public String sendNotification(@RequestBody Map<String, String> payload) {
        System.out.println(payload.get("token"));
        String token = payload.get("token");
        String title = payload.get("title");
        String body = payload.get("body");
        email.sendEmail("jeyabalan764@gmail.com","You have a new Test","Attend the Test without fail");
        return notificationView.sendPushNotification(token, title, body);
    }
}