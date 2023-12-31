package com.buildingtracker.mvc.model.level;

import com.buildingtracker.mvc.model.level.Level;
import jakarta.persistence.*;

@Entity
@Table(name = "Level_area")
public class LevelArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "coords")
    private String coords;

    @Column(name = "overlay")
    private int overlay;

    public LevelArea() {
    }

    public LevelArea(Level level, String coords, int overlay) {
        this.level = level;
        this.coords = coords;
        this.overlay = overlay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }


}
