package com.protal.myApp.Service;

import com.protal.myApp.Entity.Room;
import com.protal.myApp.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void updateRoomCapacity(Room room, Integer capacity) {
        room.setCapacity(capacity);
        roomRepository.save(room);
    }
}
