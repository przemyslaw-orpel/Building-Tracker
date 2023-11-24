package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.Level;
import com.buildingtracker.mvc.model.building.LevelArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelAreaRepository extends JpaRepository<LevelArea, Integer> {
    List<LevelArea> findAllByLevelId(int level);

}
