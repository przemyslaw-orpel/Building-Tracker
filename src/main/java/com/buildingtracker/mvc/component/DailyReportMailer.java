package com.buildingtracker.mvc.component;

import com.buildingtracker.mvc.model.user.User;
import com.buildingtracker.mvc.service.MailService;
import com.buildingtracker.mvc.service.building.BuildingsService;
import com.buildingtracker.mvc.service.building.RoomService;
import com.buildingtracker.mvc.service.employee.EmployeeService;
import com.buildingtracker.mvc.service.level.LevelService;
import com.buildingtracker.mvc.service.user.UserService;
import jakarta.mail.MessagingException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@EnableScheduling
public class DailyReportMailer {
    private static final String DASHBOARD_HTML = "dashboard.html";
    private static final String EMP_TOT = "{empTot}";
    private static final String BUILD_TOT = "{buildTot}";
    private static final String ROOM_TOT = "{roomTot}";
    private static final String INSIDE_EMP = "{insideEmp}";
    private static final String TODAY_EMP = "{todayEmp}";
    private static final String MONTH_EMP = "{monthEmp}";
    private static final String REP_DATE = "{repDate}";
    private static final String REP_DAIL_SUB = "Daily report";
    private static final String TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static final String HTML_PATH = "static/mail/";
    private final MailService mailService;
    private final UserService userService;
    private final EmployeeService emplService;
    private final BuildingsService buildService;
    private final RoomService roomService;
    private final LevelService levelService;

    public DailyReportMailer(MailService mailService, UserService userService, EmployeeService emplService, BuildingsService buildService, RoomService roomService, LevelService levelService) {
        this.mailService = mailService;
        this.userService = userService;
        this.emplService = emplService;
        this.buildService = buildService;
        this.roomService = roomService;
        this.levelService = levelService;
    }

    @Scheduled(cron = "0 55 23 * * ?")
    public void sendDailyReport() throws IOException {
        List<User> users = userService.findAll();
        String reportDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMATTER));
        String html = getHtmlByFileName(DASHBOARD_HTML);
        String subject = REP_DAIL_SUB + " " + reportDate;

        if(html != null) {
            // Set report data
            String modifiedHtml = html
                    .replace(EMP_TOT, Integer.toString(emplService.getTotalEmpl()))
                    .replace(BUILD_TOT, Integer.toString(buildService.getTotalBuild()))
                    .replace(ROOM_TOT, Integer.toString(roomService.getTotalRoom()))
                    .replace(INSIDE_EMP, Integer.toString(levelService.getTotalInside()))
                    .replace(TODAY_EMP, Integer.toString(levelService.getTotalToday()))
                    .replace(MONTH_EMP, Integer.toString(levelService.getTotalMonth()))
                    .replace(REP_DATE, reportDate);

            // Send mail to users
            users.forEach(u -> {
                try {
                    mailService.sendMail(u.getEmail(), subject, modifiedHtml, true);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private String getHtmlByFileName(String file) throws IOException {
        Resource resource = new ClassPathResource(HTML_PATH + file);
        String htmlContent = null;
        // Check if the resource exists
        if (resource.exists()) {
            // Read the content of the HTML file
            htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }
        return htmlContent;
    }

}
