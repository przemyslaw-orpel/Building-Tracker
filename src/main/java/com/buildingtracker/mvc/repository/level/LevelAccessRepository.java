package com.buildingtracker.mvc.repository.level;

import com.buildingtracker.mvc.model.level.Level;
import com.buildingtracker.mvc.model.level.LevelAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LevelAccessRepository extends JpaRepository<LevelAccess, Integer> {

    @Query("SELECT la FROM LevelAccess la WHERE la.level=:level and la.exitTime is null")
    List<LevelAccess> findAllInidseLevel(Level level);

    List<LevelAccess> findAllByExitTimeIsNull();

    @Query("SELECT COUNT(la) FROM LevelAccess la WHERE la.exitTime IS NULL")
    int getTotalInside();


    @Query("SELECT COUNT(DISTINCT la.employee) FROM LevelAccess la WHERE la.entryTime >= :todayStart AND la.entryTime < :tomorrowStart")
    int getTotalTime(LocalDateTime todayStart, LocalDateTime tomorrowStart);

    @Query("SELECT la FROM LevelAccess la WHERE la.entryTime >= :todayStart AND la.entryTime < :tomorrowStart")
    List<LevelAccess> getLaByRange(LocalDateTime todayStart, LocalDateTime tomorrowStart);


}
