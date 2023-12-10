package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.level.LevelAccess;
import com.buildingtracker.mvc.model.report.BuildTotEmp;
import com.buildingtracker.mvc.service.building.BuildingsService;
import com.buildingtracker.mvc.service.building.RoomService;
import com.buildingtracker.mvc.service.employee.EmployeeService;
import com.buildingtracker.mvc.service.level.LevelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final EmployeeService emplService;
    private final BuildingsService buildService;
    private final RoomService roomService;
    private final LevelService levelService;

    public MainController(EmployeeService emplService, BuildingsService buildService, RoomService roomService, LevelService levelService) {
        this.emplService = emplService;
        this.buildService = buildService;
        this.roomService = roomService;
        this.levelService = levelService;
    }

    @GetMapping("/dashboard")
    String getDashboard(Model model){
        int empTot = emplService.getTotalEmpl();
        int buildTot = buildService.getTotalBuild();
        int roomTot = roomService.getTotalRoom();

        int insideEmp = levelService.getTotalInside();
        int todayEmp = levelService.getTotalToday();
        int monthEmp = levelService.getTotalMonth();
        List<LevelAccess> insideLa = levelService.findAllEmpInside();

        Map<String, Long> insideBuild = insideLa.stream()
                .map(LevelAccess::getLevel)
                .map(l -> l.getBuilding().getName())
                .collect(Collectors.groupingBy(
                        buildingName  -> buildingName,
                        Collectors.counting()
                ));

        List<BuildTotEmp> buildTotEmp = insideBuild.entrySet().stream()
                .map(entry -> new BuildTotEmp(entry.getKey(), entry.getValue().intValue()))
                .sorted(Comparator.comparingInt(BuildTotEmp::getTotEmp).reversed())
                .collect(Collectors.toList());

        model.addAttribute("empTot", empTot);
        model.addAttribute("buildTot", buildTot);
        model.addAttribute("roomTot", roomTot);
        model.addAttribute("insideEmp", insideEmp);
        model.addAttribute("todayEmp", todayEmp);
        model.addAttribute("monthEmp", monthEmp);
        model.addAttribute("buildTotEmp", buildTotEmp);

        return "dashboard.html";
    }
}
