package com.buildingtracker.mvc.service.building;

import com.buildingtracker.mvc.model.building.Building;
import com.buildingtracker.mvc.model.building.BuildingArea;
import com.buildingtracker.mvc.model.building.Level;
import com.buildingtracker.mvc.model.building.LevelArea;
import com.buildingtracker.mvc.repository.building.BuildingAreaRepository;
import com.buildingtracker.mvc.repository.building.BuildingRepository;
import com.buildingtracker.mvc.repository.building.LevelAreaRepository;
import com.buildingtracker.mvc.repository.building.LevelRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingsService {
    private final BuildingRepository buildingRepo;
    private final BuildingAreaRepository buildingAreaRepo;
    private final LevelAreaRepository levelAreaRepo;
    private final LevelRepository levelRepo;

    public BuildingsService(BuildingRepository buildingRepo, BuildingAreaRepository buildingAreaRepo, LevelAreaRepository levelAreaRepo, LevelRepository levelRepo) {
        this.buildingRepo = buildingRepo;
        this.buildingAreaRepo = buildingAreaRepo;
        this.levelAreaRepo = levelAreaRepo;
        this.levelRepo = levelRepo;
    }

    public List<Building> findAll(){
        return buildingRepo.findAll();
    }

    public Building findByName(String name){
        return buildingRepo.findByName(name);
    }

    public List<BuildingArea> findAreaByBuildId(int buildingId){
        return buildingAreaRepo.findByBuildingId(buildingId);
    }

    public Level findLevelByBuilLevel(String building, int level){
        return levelRepo.findByLevelAndBuildingName(level, building);
    }

    public List<LevelArea> findAreaByLevelId(int levelId){
        return levelAreaRepo.findAllByLevelId(levelId);
    }

}
