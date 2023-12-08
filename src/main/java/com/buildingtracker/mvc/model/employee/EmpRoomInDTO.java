package com.buildingtracker.mvc.model.employee;

import com.buildingtracker.mvc.model.building.Room;

public class EmpRoomInDTO {
    private int id;
    private Employee employee;
    private Room room;
    private boolean isInside;

    public EmpRoomInDTO(int id, Employee employee, Room room, boolean isInside) {
        this.id = id;
        this.employee = employee;
        this.room = room;
        this.isInside = isInside;
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

    public boolean isInside() {
        return isInside;
    }

    public void setInside(boolean inside) {
        isInside = inside;
    }


}
