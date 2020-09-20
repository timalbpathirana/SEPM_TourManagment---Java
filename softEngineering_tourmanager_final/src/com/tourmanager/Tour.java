package com.tourmanager;

import java.util.ArrayList;
import java.util.List;

public class Tour {
    private static  int nextID = 1;
    private Integer id;
    private String name;
    private TourType type;
    private Double duration;
    private List<Location> locations = new ArrayList<>();

    public Tour(String name, TourType type) {
        this.name = name;
        this.type = type;
        this.duration = 0d;
        this.id = nextID++;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TourType getType() {
        return type;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
        for (Location location : locations) {
            this.duration += location.getTime();
        }
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", duration=" + duration +
                ", locations=" + locations +
                '}';
    }
}
