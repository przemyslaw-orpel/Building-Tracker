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

    public BuildingsService(BuildingRepository buildingRepo, BuildingAreaRepository buildingAreaRepo) {
        this.buildingRepo = buildingRepo;
        this.buildingAreaRepo = buildingAreaRepo;
    }

    //////////////////////////////////////////////////////////////////////
    //Building methods
    public List<Building> findAll() {
        return buildingRepo.findAll();
    }

    public Building findById(int id) {
        return buildingRepo.findById(id).orElse(null);
    }

    public Building findByName(String name) {
        return buildingRepo.findByName(name);
    }

    public void updateBuilding(Building building) {
        buildingRepo.save(building);
    }

    public boolean delete(Building building) {
        try {
            buildingRepo.delete(building);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //////////////////////////////////////////////////////////////////////
    //Building area methods

    public BuildingArea findAreaById(int id) {
        return buildingAreaRepo.findById(id).orElse(null);
    }

    public List<BuildingArea> findAreaByBuildId(int buildingId) {
        return buildingAreaRepo.findByBuildingId(buildingId);
    }

    public List<BuildingArea> findAllArea() {
        return buildingAreaRepo.findAll();
    }

    public boolean deleteArea(BuildingArea area) {
        try {
            buildingAreaRepo.delete(area);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateArea(BuildingArea area) {
        buildingAreaRepo.save(area);
    }


    public void saveFileToResources(MultipartFile file) throws IOException {
        // Get the resources folder path
        Resource resource = new ClassPathResource("static");
        String resourcesPath = resource.getFile().getAbsolutePath();

        // Specify the path where you want to save the file in the resources folder
        String filePath = resourcesPath + File.separator + "img" + File.separator + file.getOriginalFilename();

        // Copy the file to the specified path
        FileCopyUtils.copy(file.getBytes(), new FileOutputStream(filePath));
    }
}
