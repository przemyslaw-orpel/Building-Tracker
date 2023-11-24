package com.buildingtracker.mvc.repository.employee;

import com.buildingtracker.mvc.model.employee.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Integer> {
}
