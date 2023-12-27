package com.buildingtracker.mvc.service.level;

import com.buildingtracker.mvc.model.level.Level;
import com.buildingtracker.mvc.model.level.LevelAccess;
import com.buildingtracker.mvc.model.level.LevelArea;
import com.buildingtracker.mvc.repository.level.LevelAccessRepository;
import com.buildingtracker.mvc.repository.level.LevelAreaRepository;
import com.buildingtracker.mvc.repository.level.LevelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LevelService {
    private final LevelAreaRepository levelAreaRepo;
    private final LevelRepository levelRepo;
    private final LevelAccessRepository levelAccessRepo;

    public LevelService(LevelAreaRepository levelAreaRepo, LevelRepository levelRepo, LevelAccessRepository levelAccessRepo) {
        this.levelAreaRepo = levelAreaRepo;
        this.levelRepo = levelRepo;
        this.levelAccessRepo = levelAccessRepo;
    }
    //////////////////////////////////////////////////////////////////////
    //Level methods

    public Level findLevelByBuilLevel(String building, int level) {
        return levelRepo.findByLevelAndBuildingName(level, building);
    }


    public List<LevelArea> findAreaByLevelId(int levelId) {
        return levelAreaRepo.findAllByLevelId(levelId);
    }

    public List<Level> findAllLevel() {
        return levelRepo.findAll();
    }

    public Level findLevelById(int id) {
        return levelRepo.findById(id).orElse(null);
    }

    public void updateLevel(Level level) {
        levelRepo.save(level);
    }

    public List<Level> findLevelsByBuildId(int buildId) {
        return levelRepo.findByBuildId(buildId);
    }

    public boolean deleteLevel(Level level) {
        try {
            levelRepo.delete(level);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //////////////////////////////////////////////////////////////////////
    //Level area methods
    public List<LevelArea> findAllLevelArea() {
        return levelAreaRepo.findAll();
    }

    public LevelArea findLevelAreaById(int id) {
        return levelAreaRepo.findById(id).orElse(null);
    }

    public boolean deleteLevelArea(LevelArea area) {
        try {
            levelAreaRepo.delete(area);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateLevelArea(LevelArea area) {
        levelAreaRepo.save(area);
    }

    //////////////////////////////////////////////////////////////////////
    //Level access methods
    public List<LevelAccess> findAllEmpInsideLevel(Level level) {
        return levelAccessRepo.findAllInidseLevel(level);
    }

    public List<LevelAccess> findAllEmpInside() {
        return levelAccessRepo.findAllByExitTimeIsNull();
    }

    public LevelAccess findLevelAccessById(int id) {
        return levelAccessRepo.findById(id).orElse(null);
    }

    public void updateLevelAccess(LevelAccess la) {
        levelAccessRepo.save(la);
    }

    public int getTotalInside(){
        return levelAccessRepo.getTotalInside();
    }

    public int getTotalToday(){
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime tomorrowStart = todayStart.plusDays(1);
        return levelAccessRepo.getTotalTime(todayStart, tomorrowStart);
    }

    public int getTotalMonth(){
        LocalDateTime startOfMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT);
        LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);
        return levelAccessRepo.getTotalTime(startOfMonth, startOfNextMonth);
    }

    public List<LevelAccess> getLaToday(){
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime tomorrowStart = todayStart.plusDays(1);
        return levelAccessRepo.getLaByRange(todayStart, tomorrowStart);
    }

    public List<LevelAccess> getLaByRang(LocalDateTime start, LocalDateTime end){
        return levelAccessRepo.getLaByRange(start, end);
    }

}
