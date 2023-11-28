package com.buildingtracker.mvc.model.building;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table( name = "Building_area")
public class BuildingArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "building_id")
    private Building building;

    @Column(name = "coords")
    private String coords;

    @Column(name = "overlay")
    private int overlay;

    public BuildingArea() {
    }

    public BuildingArea(Building building, String coords, int overlay) {
        this.building = building;
        this.coords = coords;
        this.overlay = overlay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public int getOverlay() {
        return overlay;
    }

    public void setOverlay(int overlay) {
        this.overlay = overlay;
    }
}
