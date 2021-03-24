package com.protal.myApp.Controller;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.protal.myApp.Utils.CompressUtils.compressBytes;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.OPTIONS)
    public ResponseEntity authenticate() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity authenticateEmail(@RequestBody String email) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.OPTIONS)
    public ResponseEntity handleRegistration() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestParam(value = "imageFile", required = false) MultipartFile file, @RequestParam("name") String name,
                                   @RequestParam("lastName") String lastName, @RequestParam("telephone") Long telephone,
                                   @RequestParam("email") String email, @RequestParam("username") String username,
                                   @RequestParam("password") String password) throws IOException {
        byte[] image = null;
        try {
            if (file != null) {
                image = compressBytes(file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean userExists = userService.checkIfUserExists(username, email);
        System.out.println("user exists "+userExists);
        // List<User> userList = userService.findByUsernameOrEmail(username, email);
        if (!userExists) {
            User newUser = new User(name, lastName, telephone, email,
                    username, password, User.ROLE_USER);
            System.out.println("user image "+image);
            newUser.setImage(image);
            userService.saveUser(newUser);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
