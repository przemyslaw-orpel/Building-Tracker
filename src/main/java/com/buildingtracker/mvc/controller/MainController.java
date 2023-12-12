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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

        List<LevelAccess> todayLa = levelService.getLaToday();

        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endTime = LocalDateTime.now();
        TreeMap<String, Integer> chartData = new TreeMap<>();

        // Step 10-minutes
        while (startTime.isBefore(endTime)) {
            LocalDateTime intervalEnd = startTime.plus(10, ChronoUnit.MINUTES);
            int employeesInside = countEmployeesInside(todayLa, startTime, intervalEnd);
            chartData.put(startTime.format(DateTimeFormatter.ofPattern("HH:mm")), employeesInside);
            startTime = intervalEnd;
        }

        model.addAttribute("empTot", empTot);
        model.addAttribute("buildTot", buildTot);
        model.addAttribute("roomTot", roomTot);
        model.addAttribute("insideEmp", insideEmp);
        model.addAttribute("todayEmp", todayEmp);
        model.addAttribute("monthEmp", monthEmp);
        model.addAttribute("buildTotEmp", buildTotEmp);
        model.addAttribute("chartData", chartData);
        return "dashboard.html";
    }

    private static int countEmployeesInside(List<LevelAccess> levelAccessList, LocalDateTime startTime, LocalDateTime endTime) {
        int count = 0;
        for (LevelAccess levelAccess : levelAccessList) {
            LocalDateTime entryTime = levelAccess.getEntryTime();
            LocalDateTime exitTime = levelAccess.getExitTime();
            if (entryTime != null && exitTime != null) {
                if ((entryTime.isBefore(startTime) || entryTime.isEqual(startTime)) && ( exitTime.isAfter(startTime) || exitTime.isEqual(startTime))) {
                    count++;
                }
            } else if (entryTime != null) {
                if (entryTime.isBefore(startTime) || entryTime.isEqual(startTime)) {
                    count++;
                }
            }
        }
        return count;
    }
}
