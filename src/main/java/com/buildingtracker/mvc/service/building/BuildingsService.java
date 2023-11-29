package com.buildingtracker.mvc.service.building;

import com.buildingtracker.mvc.model.building.*;
import com.buildingtracker.mvc.repository.building.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class BuildingsService {
    private final BuildingRepository buildingRepo;
    private final BuildingAreaRepository buildingAreaRepo;
    private final LevelAreaRepository levelAreaRepo;
    private final LevelRepository levelRepo;
    private final LevelAccessRepository levelAccessRepo;
    public BuildingsService(BuildingRepository buildingRepo, BuildingAreaRepository buildingAreaRepo, LevelAreaRepository levelAreaRepo, LevelRepository levelRepo, LevelAccessRepository levelAccessRepo) {
        this.buildingRepo = buildingRepo;
        this.buildingAreaRepo = buildingAreaRepo;
        this.levelAreaRepo = levelAreaRepo;
        this.levelRepo = levelRepo;
        this.levelAccessRepo = levelAccessRepo;
    }

    public List<Building> findAll(){
        return buildingRepo.findAll();
    }

    public Building findById(int id){
        return buildingRepo.findById(id).orElse(null);
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

    public void updateBuilding(Building building){
        buildingRepo.save(building);
    }


    public boolean delete(Building building){
        try {
            buildingRepo.delete(building);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<BuildingArea> findAllArea(){
        return buildingAreaRepo.findAll();
    }

    public BuildingArea findAreaById(int id){
        return buildingAreaRepo.findById(id).orElse(null);
    }

    public boolean deleteArea(BuildingArea area){
        try {
            buildingAreaRepo.delete(area);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void updateArea(BuildingArea area){
        buildingAreaRepo.save(area);
    }

    public List<Level> findAllLevel(){
        return levelRepo.findAll();
    }

    public Level findLevelById(int id){
        return levelRepo.findById(id).orElse(null);
    }

    public void updateLevel(Level level){
        levelRepo.save(level);
    }

    public boolean deleteLevel(Level level){
        try {
            levelRepo.delete(level);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<LevelArea> findAllLevelArea(){
        return levelAreaRepo.findAll();
    }

    public LevelArea findLevelAreaById(int id){
        return levelAreaRepo.findById(id).orElse(null);
    }

    public boolean deleteLevelArea(LevelArea area){
        try {
            levelAreaRepo.delete(area);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void updateLevelArea(LevelArea area){
        levelAreaRepo.save(area);
    }

    public void saveFileToResources(MultipartFile file) throws IOException {
        // Get the resources folder path
        Resource resource = new ClassPathResource("static"); // You can adjust the path based on your project structure
        String resourcesPath = resource.getFile().getAbsolutePath();

        // Specify the path where you want to save the file in the resources folder
        String filePath = resourcesPath + File.separator + "img" + File.separator + file.getOriginalFilename();

        // Copy the file to the specified path
        FileCopyUtils.copy(file.getBytes(), new FileOutputStream(filePath));
    }

    public List<Level> findLevelsByBuildId(int buildId){
        return levelRepo.findByBuildId(buildId);
    }

    public List<LevelAccess> findAllEmpInsideLevel(Level level){
        return levelAccessRepo.findAllInidseLevel(level);
    }

}
