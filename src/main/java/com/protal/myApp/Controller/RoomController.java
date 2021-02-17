package com.protal.myApp.Controller;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.Room;
import com.protal.myApp.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping(path = "/rooms")
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> roomList = roomService.findAll();
        return new ResponseEntity<List<Room>>(roomList, HttpStatus.OK);
    }

}
