package com.protal.myApp.Controller;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userService.findAll();
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN_CS')")
    @PostMapping(value = "/getUsers")
    public ResponseEntity<List<User>> getUserByUsernameOrEmail(@RequestParam(name = "username", required = false) String username,
                                                               @RequestParam(name = "email", required = false) String email) {
        List<User> userList = userService.findByUsernameOrEmail(username, email);
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    //κάθε χρήστης επεξεργάζεται τα δικά του στοιχεία
    @PostMapping(value = "/updateUserData")
    public ResponseEntity updateUserValues(@RequestParam(value = "imageFile", required = false) MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("telephone") Long telephone,
                                           @RequestParam("password") String password,
                                           @RequestParam("id") Integer id) throws IOException {
       boolean succesfulUpdate=userService.updateAccount(file,name,lastName,telephone,password,id);
       if(succesfulUpdate){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //οι admin_cs επεξεργάζονται τα στοιχεία άλλων χρηστών
    @PreAuthorize("hasRole('ADMIN_CS')")
    @PostMapping(value = "/editUser")
    public ResponseEntity editUserValues(@RequestParam("name") String name,
                                         @RequestParam("lastName") String lastName,
                                         @RequestParam("telephone") Long telephone,
                                         @RequestParam("password") String password,
                                         @RequestParam("role") String role,
                                         @RequestParam("username") String username,
                                         @RequestParam("email") String email,
                                         @RequestParam("id") Integer id) {
        boolean succesfulEdit = userService.editUserValues(name,lastName,telephone,password,role,username,email,id);
        if (succesfulEdit) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
