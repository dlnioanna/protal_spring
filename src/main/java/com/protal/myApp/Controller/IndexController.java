package com.protal.myApp.Controller;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IndexController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index() {
        User user=userService.findById(1);
        return "index";
    }

//    @GetMapping("/")
//    public ResponseEntity<User> index() {
//        User user=userService.findById(1);
//        return new ResponseEntity<User>(user, HttpStatus.OK);
//    }
}
