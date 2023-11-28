package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    @Query("SELECT l FROM Level l WHERE l.building.id=:buildId")
    List<Level> findByBuildId(int buildId);

    Level findByLevelAndBuildingName( int level, String building);


}
