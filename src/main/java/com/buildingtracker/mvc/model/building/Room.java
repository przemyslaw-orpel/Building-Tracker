package com.buildingtracker.mvc.model.building;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

//    @ManyToOne()
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "building_id")
//    private Building building;

//    @Column(name = "level")
//    private int level;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn( name = "level_area_id")
    private LevelArea levelArea;

    @Column(name = "emp_space")
    private int empSpace;


    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    public Room() {
    }

    public Room(String name, int empSpace, LevelArea area, RoomType roomType) {
        this.name = name;
        this.empSpace = empSpace;
        this.levelArea = area;
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LevelArea getLevelArea() {
        return levelArea;
    }

    public void setLevelArea(LevelArea levelArea) {
        this.levelArea = levelArea;
    }

    public int getEmpSpace() {
        return empSpace;
    }

    public void setEmpSpace(int empSpace) {
        this.empSpace = empSpace;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
