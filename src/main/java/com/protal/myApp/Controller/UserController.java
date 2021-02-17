package com.protal.myApp.Controller;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.protal.myApp.Utils.CompressUtils.compressBytes;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        System.out.println("user is " + user.getName() + " " + user.getLastName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userService.findAll();
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping(value = "/updateUserData")
    public ResponseEntity updateUserValues(@RequestParam(value = "imageFile", required = false) MultipartFile file, @RequestParam("name") String name,
                                         @RequestParam("lastName") String lastName, @RequestParam("telephone") Long telephone,
                                         @RequestParam("password") String password, @RequestParam("id") Integer id) throws IOException {
        User user = userService.findById(id);
        if (user != null) {
            byte[] image = null;
            if (file != null) {
                image = compressBytes(file.getBytes());
                System.out.println("image update");
                userService.updateUserValues(image, name, lastName, telephone,password, id);
            } else {
                System.out.println("no image update");
                userService.updateUserValues(user.getImage(), name, lastName, telephone,password, id);
            }
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}
