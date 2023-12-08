package com.buildingtracker.mvc.service.employee;

import com.buildingtracker.mvc.model.employee.EmployeeRoom;
import com.buildingtracker.mvc.repository.employee.EmployeeRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRoomService {
    private final EmployeeRoomRepository empRoomRepo;

    public EmployeeRoomService(EmployeeRoomRepository employeeRoomRepo) {
        this.empRoomRepo = employeeRoomRepo;
    }

    public List<EmployeeRoom> findAllByBuildingAndFloor(String building, int level) {
        return empRoomRepo.findAllByBuildingNameAndLevel(building, level);
    }

    public List<EmployeeRoom> findAll() {
        return empRoomRepo.findAll();
    }

    public EmployeeRoom findById(int id) {
        return empRoomRepo.findById(id).orElse(null);
    }

    public void update(EmployeeRoom employeeRoom) {
        empRoomRepo.save(employeeRoom);
    }

    public boolean delete(EmployeeRoom empRoom) {
        try {
            empRoomRepo.delete(empRoom);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
