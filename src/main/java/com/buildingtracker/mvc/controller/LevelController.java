package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.building.Building;
import com.buildingtracker.mvc.model.building.Level;
import com.buildingtracker.mvc.model.building.LevelArea;
import com.buildingtracker.mvc.model.building.Room;
import com.buildingtracker.mvc.service.building.BuildingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class LevelController {
    private final BuildingsService buildingsService;

    public LevelController(BuildingsService buildingsService) {
        this.buildingsService = buildingsService;
    }


    //////////////////////////////////////////////////////////////////////
    //Levels
    @GetMapping("levels")
    String getLevels(Model model) {
        List<Level> levels = buildingsService.findAllLevel();

        model.addAttribute("levels", levels);
        return "level/list_level";
    }

    @GetMapping("level/edit")
    String editLevels(Model model, @RequestParam int id) {
        Level level = buildingsService.findLevelById(id);
        List<Building> builds = buildingsService.findAll();
        model.addAttribute("builds", builds);
        model.addAttribute("level", level);
        return "level/edit_level";
    }

    @GetMapping("level")
    String detailsLevel(Model model, @RequestParam int id) {
        Level level = buildingsService.findLevelById(id);
        model.addAttribute("level", level);
        return "level/details_level";
    }

    @GetMapping("level/add")
    String addLevels(Model model) {
        List<Building> builds = buildingsService.findAll();
        model.addAttribute("builds", builds);
        return "level/add_level";
    }

    @PostMapping("/level/save")
    String saveLevel(Model model, @RequestParam(required = false) Integer id, @RequestParam int buildId, @RequestParam(required = false) MultipartFile file,
                     @RequestParam(required = false) String fileName, @RequestParam int levels) throws IOException {
        Building build = buildingsService.findById(buildId);
        Level level;
        ;
        if (id != null) {
            level = buildingsService.findLevelById(id);
            level.setBuilding(build);
            level.setLevel(levels);
            level.setFileName(fileName);
        } else {
            level = new Level(build, file.getOriginalFilename(), levels);
            buildingsService.saveFileToResources(file);

        }
        buildingsService.updateLevel(level);
        return "redirect:/level?id=" + level.getId();
    }


    @DeleteMapping("/level/delete")
    ResponseEntity<String> deleteLevel(@RequestParam int id) {
        Level level = buildingsService.findLevelById(id);
        boolean isDeleted = buildingsService.deleteLevel(level);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }


    //////////////////////////////////////////////////////////////////////
    //Levels area
    @GetMapping("/levels/area")
    String getLevelArea(Model model) {
        List<LevelArea> area = buildingsService.findAllLevelArea();
        model.addAttribute("area", area);
        return "level/list_area_level";
    }

    @GetMapping("/level/area")
    String detailsLevelArea(Model model, @RequestParam int id) {
        LevelArea area = buildingsService.findLevelAreaById(id);
        model.addAttribute("area", area);
        return "level/details_area_level.html";
    }

    @GetMapping("/level/area/edit")
    String editLevelArea(Model model, @RequestParam int id) {

        LevelArea area = buildingsService.findLevelAreaById(id);
        List<Level> levels = buildingsService.findAllLevel();

        model.addAttribute("area", area);
        model.addAttribute("levels", levels);
        return "level/edit_area_level";
    }

    @GetMapping("/level/area/add")
    String addLevelArea(Model model) {
        List<Level> levels = buildingsService.findAllLevel();
        model.addAttribute("levels", levels);
        return "level/add_area_level";
    }

    @PostMapping("/level/area/save")
    String editLevelArea(@RequestParam(required = false) Integer id, @RequestParam String coords,
                         @RequestParam int levelId, @RequestParam int areaId) {
        Level level = buildingsService.findLevelById(levelId);

        LevelArea area;
        if (id != null) {
            area = buildingsService.findLevelAreaById(id);
            area.setCoords(coords);
            area.setLevel(level);
            area.setOverlay(areaId);
        } else {
            area = new LevelArea(level, coords, areaId);
        }
        buildingsService.updateLevelArea(area);
        return "redirect:/level/area?id=" + area.getId();
    }

    @DeleteMapping("/level/area/delete")
    ResponseEntity<String> deleteLevelArea(@RequestParam int id) {
        LevelArea area = buildingsService.findLevelAreaById(id);
        boolean isDeleted = buildingsService.deleteLevelArea(area);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }

    @GetMapping("/levels/building")
    @ResponseBody
    List<Level> getLevelsByBuildingId(@RequestParam int buildId) {
        List<Level> levels = buildingsService.findLevelsByBuildId(buildId);
        return levels;
    }

    @GetMapping("/api/levels/area")
    @ResponseBody
    List<LevelArea> getLevelsAreaByBuildingId(@RequestParam int levelId) {
         List<LevelArea> areas = buildingsService.findAreaByLevelId(levelId);
        return areas;
    }


}
