package edu.annauniv.dist.webtech.quiz_backend.Controllers;


import edu.annauniv.dist.webtech.quiz_backend.Models.LoginModel;
import edu.annauniv.dist.webtech.quiz_backend.Views.LoginView;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    LoginView loginView = new LoginView();

    @GetMapping("/")
    public List<LoginModel> listUser(){
        List<LoginModel> listUser = loginView.listUser();
        return listUser;
    }

    @PostMapping("")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        Map<String, Object> res = loginView.login(email, password);
        return res;
    }
}
