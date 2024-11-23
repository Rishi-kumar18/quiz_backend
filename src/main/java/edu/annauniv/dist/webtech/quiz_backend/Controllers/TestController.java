package edu.annauniv.dist.webtech.quiz_backend.Controllers;


import edu.annauniv.dist.webtech.quiz_backend.Models.UserModel;
import edu.annauniv.dist.webtech.quiz_backend.Views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {
    UserView userView = new UserView();
    @GetMapping("/")
    public Map<String,Object> test(){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> subresult = new HashMap<>();
        subresult.put("Status", "Ok");
        result.put("result", subresult);
        return result;
    }

    @GetMapping("/user")
    public List<UserModel> listUser(){
        List<UserModel> listUser = userView.listUser();
        return listUser;
    }

    @PostMapping("/user")
    public Map<String , Object> login(@RequestParam String rollno, @RequestParam String name){
        Map<String,Object> res = userView.login(name,rollno);
        return res;
    }
}
