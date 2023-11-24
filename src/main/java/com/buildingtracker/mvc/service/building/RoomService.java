package com.buildingtracker.mvc.service.building;


import com.buildingtracker.mvc.model.building.Room;
import com.buildingtracker.mvc.repository.building.RoomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepo;

    public RoomService(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<Room> findAllByBuildingAndFloor(String buildName, int level){
        return roomRepo.findAllByBuildingNameAndLevel(buildName, level);
    }

    public List<Room> findAll(){
        Sort sort = Sort.by(Sort.Order.asc("building.name"), Sort.Order.asc("level"));

        return roomRepo.findAll(sort);
    }
}
