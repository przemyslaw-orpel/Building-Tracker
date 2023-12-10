package com.buildingtracker.mvc.service.building;


import com.buildingtracker.mvc.model.building.Room;
import com.buildingtracker.mvc.model.building.RoomType;
import com.buildingtracker.mvc.repository.building.RoomRepository;
import com.buildingtracker.mvc.repository.building.RoomTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepo;
    private final RoomTypeRepository roomTypeRepo;

    public RoomService(RoomRepository roomRepo, RoomTypeRepository roomTypeRepo) {
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
    }

    //////////////////////////////////////////////////////////////////////
    //Room methods
    public List<Room> findAllByBuildingAndFloor(String buildName, int level) {
        return roomRepo.findAllByBuildingNameAndLevel(buildName, level);
    }

    public List<Room> findAll() {
        Sort sort = Sort.by(Sort.Order.asc("levelArea.level.building.name"), Sort.Order.asc("levelArea.level.level"));
        return roomRepo.findAll(sort);
    }

    public Room findById(int id) {
        return roomRepo.findById(id).orElse(null);
    }

    public void update(Room room) {
        roomRepo.save(room);
    }

    public List<Room> findAllByBuilding(int buildId) {
        return roomRepo.findAllByBuildingId(buildId);
    }

    @Transactional
    public Boolean delete(Room room) {
        try {
            roomRepo.delete(room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTotalRoom(){
        return roomRepo.getTotalRoom();
    }

    //////////////////////////////////////////////////////////////////////
    //RoomType methods
    public List<RoomType> findAllTypes() {
        return roomTypeRepo.findAll();
    }

    public RoomType findTypeById(int id) {
        return roomTypeRepo.findById(id).orElse(null);
    }

}
