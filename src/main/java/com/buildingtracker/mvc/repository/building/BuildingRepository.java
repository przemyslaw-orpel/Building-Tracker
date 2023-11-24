package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Building findByName(String name);
}
