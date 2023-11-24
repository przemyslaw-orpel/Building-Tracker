package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.building.*;
import com.buildingtracker.mvc.model.employee.EmployeeRoom;
import com.buildingtracker.mvc.model.user.User;
import com.buildingtracker.mvc.service.building.BuildingsService;
import com.buildingtracker.mvc.service.EmployeeRoomService;
import com.buildingtracker.mvc.service.building.RoomService;
import com.buildingtracker.mvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private final RoomService roomService;
    private final EmployeeRoomService employeeRoomService;
    private final UserService userService;
    private final BuildingsService buildingsService;

    public MainController(RoomService roomService, EmployeeRoomService employeeRoomService, UserService userService, BuildingsService buildingsService) {
        this.roomService = roomService;
        this.employeeRoomService = employeeRoomService;
        this.userService = userService;
        this.buildingsService = buildingsService;
    }

    @GetMapping( "/")
    String getFlorPage(@RequestParam String building, @RequestParam int level, Model model){

        Level levelObj = buildingsService.findLevelByBuilLevel(building, level);
        List<LevelArea> areas= buildingsService.findAreaByLevelId(levelObj.getId());

        List<Room> rooms = roomService.findAllByBuildingAndFloor(building, level);
        List<EmployeeRoom> empRooms = employeeRoomService.findAllByBuildingAndFloor(building, level);
        model.addAttribute("rooms", rooms);
        model.addAttribute("empRooms", empRooms );
        model.addAttribute("building", building);
        model.addAttribute("level", levelObj);
        model.addAttribute("areas", areas);
        return "index.html";
    }

    @GetMapping("/employees")
    String getEmployee(Model model){
        List<EmployeeRoom> empRooms = employeeRoomService.findAll();
        model.addAttribute("empRooms", empRooms);
        return "employees.html";
    }

    @GetMapping("/building")
    String getBuilding(Model model, @RequestParam String name){
        Building build = buildingsService.findByName(name);
        if(build == null)
            return "building.html";

        List<BuildingArea> areas = buildingsService.findAreaByBuildId(build.getId());
        model.addAttribute("build", build);
        model.addAttribute("areas", areas);
        return "building.html";
    }

    @GetMapping("/buildings")
    String getBuildings(Model model){
        List<Building> builds = buildingsService.findAll();
        model.addAttribute("builds", builds);
        return "buildings.html";
    }

    @GetMapping("/rooms")
    String getRooms(Model model){
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        return "rooms.html";
    }

    @GetMapping("/users")
    String getUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users.html";
    }

    @GetMapping("/profile")
    String getMyProfile(Model model){
        return "profile.html";
    }

    @GetMapping("/login")
    String login(){
        return "login.html";
    }
}
