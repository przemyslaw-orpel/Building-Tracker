package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.building.*;
import com.buildingtracker.mvc.model.employee.EmployeeRoom;
import com.buildingtracker.mvc.service.employee.EmployeeRoomService;
import com.buildingtracker.mvc.service.building.BuildingsService;
import com.buildingtracker.mvc.service.building.RoomService;
import jakarta.servlet.ServletContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BuildingController {
    private final BuildingsService buildingsService;
    private final RoomService roomService;
    private final EmployeeRoomService employeeRoomService;
    private ServletContext servletContext;

    public BuildingController(BuildingsService buildingsService, RoomService roomService, EmployeeRoomService employeeRoomService) {
        this.buildingsService = buildingsService;
        this.roomService = roomService;
        this.employeeRoomService = employeeRoomService;
    }

    @GetMapping("/")
    String getFlorPage(@RequestParam String building, @RequestParam int level, Model model) {

        Level levelObj = buildingsService.findLevelByBuilLevel(building, level);
        List<LevelArea> areas = buildingsService.findAreaByLevelId(levelObj.getId());

        List<Room> rooms = roomService.findAllByBuildingAndFloor(building, level);
        List<EmployeeRoom> empRooms = employeeRoomService.findAllByBuildingAndFloor(building, level);
        model.addAttribute("rooms", rooms);
        model.addAttribute("empRooms", empRooms);
        model.addAttribute("building", building);
        model.addAttribute("level", levelObj);
        model.addAttribute("areas", areas);
        return "level/view_level.html";
    }

    //////////////////////////////////////////////////////////////////////
    //Buildings
    @GetMapping("/building")
    String getBuildingView(Model model, @RequestParam String name) {
        Building build = buildingsService.findByName(name);
        if (build == null)
            return "building/view_build.html";

        List<BuildingArea> areas = buildingsService.findAreaByBuildId(build.getId());
        model.addAttribute("build", build);
        model.addAttribute("areas", areas);
        return "building/view_build.html";
    }

    @GetMapping("/buildings")
    String getBuildings(Model model) {
        List<Building> builds = buildingsService.findAll();
        model.addAttribute("builds", builds);
        return "building/list_build.html";
    }

    @GetMapping("/build")
    String getBuilding(Model model, @RequestParam int id) {
        Building building = buildingsService.findById(id);
        model.addAttribute("building", building);
        return "building/details_build.html";
    }

    @GetMapping("/build/edit")
    String editBuilding(Model model, @RequestParam int id) throws IOException {
        Building building = buildingsService.findById(id);

        model.addAttribute("building", building);
        return "building/edit_build.html";
    }


    @PostMapping("/build/save")
    String saveBuilding(Model model, @RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam(required = false) MultipartFile file,
                        @RequestParam(required = false) String fileName, @RequestParam int levels) throws IOException {
        Building building;
        if (id != null) {
            building = buildingsService.findById(id);
            building.setName(name);
            building.setFileName(fileName);
            building.setLevels(levels);
        } else {
            building = new Building(name, levels, file.getOriginalFilename());
            buildingsService.saveFileToResources(file);

        }
        buildingsService.updateBuilding(building);

        return "redirect:/build?id=" + building.getId();
    }

    @DeleteMapping("/building/delete")
    ResponseEntity<String> deleteBuilding(@RequestParam int id) {
        Building building = buildingsService.findById(id);
        boolean isDeleted = buildingsService.delete(building);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }

    @GetMapping("/build/add")
    String newBuilding(Model model) {
        return "building/add_build.html";
    }

    //////////////////////////////////////////////////////////////////////
    //Buildings area

    @GetMapping("/builds/area")
    String getBuildingsArea(Model model) {
        List<BuildingArea> area = buildingsService.findAllArea();
        model.addAttribute("area", area);
        return "building/list_area_build";
    }

    @GetMapping("/build/area")
    String detailsAreaBuilding(Model model, @RequestParam int id) throws IOException {
        BuildingArea area = buildingsService.findAreaById(id);

        model.addAttribute("area", area);
        return "building/details_area_build.html";
    }

    @GetMapping("/build/area/edit")
    String editBuildingsArea(Model model, @RequestParam int id) {
        BuildingArea area = buildingsService.findAreaById(id);
        List<Building> builds = buildingsService.findAll();
        model.addAttribute("area", area);
        model.addAttribute("builds", builds);
        return "building/edit_area_build";
    }

    @GetMapping("/build/area/add")
    String addBuildingsArea(Model model) {
        List<Building> builds = buildingsService.findAll();
        model.addAttribute("builds", builds);
        return "building/add_area_build";
    }

    @PostMapping("/build/area/save")
    String editBuildingsArea(@RequestParam(required = false) Integer id, @RequestParam String coords,
                             @RequestParam int level, @RequestParam int buildId) {
        Building build = buildingsService.findById(buildId);
        BuildingArea area;
        if (id != null) {
            area = buildingsService.findAreaById(id);
            area.setBuilding(build);
            area.setCoords(coords);
            area.setOverlay(level);
        } else {
            area = new BuildingArea(build, coords, level);
        }
        buildingsService.updateArea(area);
        return "redirect:/build/area?id=" + area.getId();
    }

    @DeleteMapping("/building/area/delete")
    ResponseEntity<String> deleteBuildingArea(@RequestParam int id) {
        BuildingArea area = buildingsService.findAreaById(id);
        boolean isDeleted = buildingsService.deleteArea(area);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }

}

