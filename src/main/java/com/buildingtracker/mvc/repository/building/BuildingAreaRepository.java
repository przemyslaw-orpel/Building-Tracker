package com.buildingtracker.mvc.repository.building;

import com.buildingtracker.mvc.model.building.BuildingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingAreaRepository extends JpaRepository<BuildingArea, Integer> {
    List<BuildingArea> findByBuildingId(int buildingId);
}
