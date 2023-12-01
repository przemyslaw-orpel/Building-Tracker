package com.buildingtracker.mvc.repository.employee;

import com.buildingtracker.mvc.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.id NOT IN ( SELECT la.employee.id FROM LevelAccess la WHERE la.exitTime is null or la.entryTime is null )")
    List<Employee> findAllEmpOutside();
}
