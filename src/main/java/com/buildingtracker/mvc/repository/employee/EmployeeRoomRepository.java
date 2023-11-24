package com.buildingtracker.mvc.repository.employee;

import com.buildingtracker.mvc.model.employee.EmployeeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRoomRepository extends JpaRepository<EmployeeRoom, Integer> {

    @Query("SELECT er FROM EmployeeRoom er INNER JOIN Room r ON  er.room.id = r.id WHERE r.building.id IN (" +
            "SELECT b.id FROM Building b WHERE b.name=:buildName) AND r.level=:level")
    List<EmployeeRoom> findAllByBuildingNameAndLevel(String buildName, int level);
}
