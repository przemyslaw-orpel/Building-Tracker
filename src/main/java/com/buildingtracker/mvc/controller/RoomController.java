package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.building.*;
import com.buildingtracker.mvc.service.building.BuildingsService;
import com.buildingtracker.mvc.service.building.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {
    private final RoomService roomService;
    private final BuildingsService buildingsService;

    public RoomController(RoomService roomService, BuildingsService buildingsService) {
        this.roomService = roomService;
        this.buildingsService = buildingsService;
    }


    //////////////////////////////////////////////////////////////////////
    //Rooms
    @GetMapping("/rooms")
    String getRooms(Model model) {
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        return "room/list_room.html";
    }

    @GetMapping("/room/edit")
    String editRoom(Model model, @RequestParam int id) {
        Room room = roomService.findById(id);
        List<RoomType> types = roomService.findAllTypes();
        List<Building> builds = buildingsService.findAll();
        List<Level> levels = buildingsService.findAllLevel();
        List<LevelArea> levelAreas = buildingsService.findAllLevelArea();

        model.addAttribute("room", room);
        model.addAttribute("types", types);
        model.addAttribute("builds", builds);
        model.addAttribute("levels", levels);
        model.addAttribute("levelAreas", levelAreas);
        return "room/edit_room.html";
    }

    @GetMapping("/room")
    String getDetailsRoom(Model model, @RequestParam int id) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "room/details_room.html";
    }

    @GetMapping("/room/add")
    String addRoom(Model model) {
        List<RoomType> types = roomService.findAllTypes();
        List<Building> builds = buildingsService.findAll();

        model.addAttribute("types", types);
        model.addAttribute("builds", builds);
        return "room/add_room.html";
    }

    @PostMapping("/room/save")
    String saveRoom(@RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam int areaId,
                   @RequestParam int empSpace, @RequestParam int typeId) {
        LevelArea area = buildingsService.findLevelAreaById(areaId);
        RoomType type = roomService.findTypeById(typeId);

        Room room;
        if (id != null) {
            room = roomService.findById(id);
            room.setName(name);
            room.setEmpSpace(empSpace);
            room.setLevelArea(area);
            room.setRoomType(type);
        } else {
            room = new Room(name, empSpace, area, type);
        }

        roomService.update(room);

        return "redirect:/room?id=" + room.getId();
    }

    @GetMapping("/rooms/building")
    @ResponseBody
    List<Room> getRoomsByBuildingId(@RequestParam int buildId) {
        return roomService.findAllByBuilding(buildId);
    }


    @DeleteMapping("/room/delete")
    ResponseEntity<String> deleteRoom(@RequestParam int id) {
        Room room = roomService.findById(id);
        boolean isDeleted = roomService.delete(room);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }

}
