package com.buildingtracker.mvc.service.employee;

import com.buildingtracker.mvc.model.employee.Employee;
import com.buildingtracker.mvc.model.employee.Workplace;
import com.buildingtracker.mvc.repository.employee.EmployeeRepository;
import com.buildingtracker.mvc.repository.employee.WorkplaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final WorkplaceRepository workplaceRepo;

    public EmployeeService(EmployeeRepository employeeRepo, WorkplaceRepository workplaceRepo) {
        this.employeeRepo = employeeRepo;
        this.workplaceRepo = workplaceRepo;
    }

    public Employee findById(int id){
        return employeeRepo.findById(id).orElse(null);
    }

    public List<Workplace> findAllWorkplaces(){
        return workplaceRepo.findAll();
    }

    public Workplace findWorkplaceById(int id){
        return workplaceRepo.findById(id).orElse(null);
    }

    public void update(Employee employee){
        employeeRepo.save(employee);
    }

    public boolean delete(Employee employee){
        try {
            employeeRepo.delete(employee);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Employee> findAllEmpOutside(){
        return employeeRepo.findAllEmpOutside();
    }
}
