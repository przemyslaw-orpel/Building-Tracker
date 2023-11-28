package com.buildingtracker.mvc.controller;

import com.buildingtracker.mvc.model.building.Building;
import com.buildingtracker.mvc.model.building.Room;
import com.buildingtracker.mvc.model.employee.Employee;
import com.buildingtracker.mvc.model.employee.EmployeeRoom;
import com.buildingtracker.mvc.model.employee.Workplace;
import com.buildingtracker.mvc.service.employee.EmployeeRoomService;
import com.buildingtracker.mvc.service.employee.EmployeeService;
import com.buildingtracker.mvc.service.building.BuildingsService;
import com.buildingtracker.mvc.service.building.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeRoomService employeeRoomService;
    private final EmployeeService employeeService;
    private final BuildingsService buildingsService;
    private final RoomService roomService;


    public EmployeeController(EmployeeRoomService employeeRoomService, EmployeeService employeeService, BuildingsService buildingsService, RoomService roomService) {
        this.employeeRoomService = employeeRoomService;
        this.employeeService = employeeService;
        this.buildingsService = buildingsService;
        this.roomService = roomService;
    }

    @GetMapping("/employees")
    String getEmployee(Model model) {
        List<EmployeeRoom> empRooms = employeeRoomService.findAll();
        model.addAttribute("empRooms", empRooms);
        return "employee/list_emp.html";
    }

    @GetMapping("/employee/edit")
    String editEmployee(Model model, @RequestParam int id) {
        EmployeeRoom empRoom = employeeRoomService.findById(id);
        List<Workplace> wrkpls = employeeService.findAllWorkplaces();
        List<Building> builds = buildingsService.findAll();
        List<Room> rooms = roomService.findAll();

        model.addAttribute("empRoom", empRoom);
        model.addAttribute("wrkpls", wrkpls);
        model.addAttribute("builds", builds);
        model.addAttribute("rooms", rooms);
        return "employee/edit_emp.html";
    }

    @GetMapping("/employee/add")
    String editEmployee(Model model) {
        List<Workplace> wrkpls = employeeService.findAllWorkplaces();
        List<Building> builds = buildingsService.findAll();
        List<Room> rooms = roomService.findAll();

        model.addAttribute("wrkpls", wrkpls);
        model.addAttribute("builds", builds);
        model.addAttribute("rooms", rooms);
        return "employee/add_emp.html";
    }

    @GetMapping("/employee")
    String getEmployee(Model model, @RequestParam int id) {
        EmployeeRoom empRoom = employeeRoomService.findById(id);

        model.addAttribute("empRoom", empRoom);
        return "employee/details_emp.html";
    }

    @PostMapping("/employee/save")
    String saveEmployee(Model model, @RequestParam(required = false) Integer id, @RequestParam(required = false) Integer empId, @RequestParam String name,
                        @RequestParam int workplaceId, @RequestParam int roomId) {
        Workplace workplace = employeeService.findWorkplaceById(workplaceId);

        Employee employee;
        if (empId != null) {
            employee = employeeService.findById(empId);
            employee.setName(name);
            employee.setWorkplace(workplace);
        } else {
            employee = new Employee(name, workplace);
        }
        employeeService.update(employee);

        Room room = roomService.findById(roomId);
        EmployeeRoom empRoom;
        if (id != null) {
            empRoom = employeeRoomService.findById(id);
            empRoom.setEmployee(employee);
            empRoom.setRoom(room);
        } else {
            empRoom = new EmployeeRoom(employee, room);
        }
        employeeRoomService.update(empRoom);


        model.addAttribute("empRoom", empRoom);
        return "redirect:/employee?id=" + empRoom.getId();
    }

    @DeleteMapping("/employee/delete")
    ResponseEntity<String> deleteEmployee(@RequestParam int id) {
        EmployeeRoom empRoom = employeeRoomService.findById(id);
        Employee employee = empRoom.getEmployee();
        boolean isDeletedEmplRoom = employeeRoomService.delete(empRoom);
        boolean isDeletedEmpl = employeeService.delete(employee);
        if (isDeletedEmplRoom && isDeletedEmpl) {
            return ResponseEntity.ok("Deleted success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deleted fail");
        }
    }
}
