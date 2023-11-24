package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    Level findByLevelAndBuildingName( int level, String building);
}
