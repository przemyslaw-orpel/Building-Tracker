package com.buildingtracker.mvc.repository.building;


import com.buildingtracker.mvc.model.building.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
}
