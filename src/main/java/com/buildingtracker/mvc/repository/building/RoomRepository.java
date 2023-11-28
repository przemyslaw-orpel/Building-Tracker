package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT r FROM Room r WHERE r.levelArea.level.building.name=:buildName and " +
            "r.levelArea.level.level=:level")
    List<Room> findAllByBuildingNameAndLevel(String buildName, int level);

    @Query("SELECT r FROM Room r WHERE r.levelArea.level.building.id=:buildId")
    List<Room> findAllByBuildingId(int buildId);
}
