package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByBuildingNameAndLevel(String buildName, int level);
}
