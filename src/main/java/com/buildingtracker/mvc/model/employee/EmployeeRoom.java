package com.buildingtracker.mvc.model.employee;

import com.buildingtracker.mvc.model.building.Room;
import com.buildingtracker.mvc.model.employee.Employee;
import jakarta.persistence.*;

@Entity
@Table(name = "Employee_room")
public class EmployeeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    public EmployeeRoom() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
