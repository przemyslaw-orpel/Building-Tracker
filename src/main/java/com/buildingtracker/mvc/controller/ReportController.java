package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.level.LevelAccess;
import com.buildingtracker.mvc.model.user.User;
import com.buildingtracker.mvc.service.level.LevelService;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReportController {
    private final SessionRegistry sessionRegistry;
    private final LevelService levelService;

    public ReportController(SessionRegistry sessionRegistry, LevelService levelService) {
        this.sessionRegistry = sessionRegistry;
        this.levelService = levelService;
    }

    @GetMapping("/reports")
    String getReportPage() {
        return "redirect:/report/inside";
    }

    @GetMapping("/report/inside")
    String getEmplInsideBuilding(Model model) {
        List<LevelAccess> empIn = levelService.findAllEmpInside();
        model.addAttribute("empIn", empIn);
        return "report/inside_build";
    }

    @GetMapping("/report/user")
    String getActiveUser(Model model) {
        List<User> loggedUsers = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(p -> (User) p)
                .distinct()
                .toList();
        model.addAttribute("loggedUsers", loggedUsers);
        return "report/active_user";
    }

    @GetMapping("/report/history")
    String getHistroyAccess(@RequestParam(required = false) String range, Model model) {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(1);

        if (range != null) {
            String[] dateRange = range.split("-");
            if (dateRange.length == 2) {
                start = LocalDateTime.parse(dateRange[0], DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
                end = LocalDateTime.parse(dateRange[1], DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            }
        }
        List<LevelAccess> levelAccess = levelService.getLaByRang(start, end);
        model.addAttribute("levelAccess", levelAccess);
        model.addAttribute("start", start.format(DateTimeFormatter.ofPattern("M/dd HH:mm")));
        model.addAttribute("end", end.format(DateTimeFormatter.ofPattern("M/dd HH:mm")));

        return "report/history_access";
    }


}
