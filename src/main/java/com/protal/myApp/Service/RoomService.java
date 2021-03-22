package com.protal.myApp.Service;

import com.protal.myApp.Entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAll();

    void saveRoom(Room room);

    void updateRoomCapacity(Room room, Integer capacity);
}
