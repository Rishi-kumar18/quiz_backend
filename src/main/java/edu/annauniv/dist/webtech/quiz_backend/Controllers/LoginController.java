package edu.annauniv.dist.webtech.quiz_backend.Controllers;


import edu.annauniv.dist.webtech.quiz_backend.Models.UserModel;
import edu.annauniv.dist.webtech.quiz_backend.Views.UserView;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    UserView userView = new UserView();

    @GetMapping("/")
    public List<UserModel> listUser(){
        List<UserModel> listUser = userView.listUser();
        return listUser;
    }

    @PostMapping("")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        Map<String, Object> res = userView.login(email, password);
        return res;
    }
}
