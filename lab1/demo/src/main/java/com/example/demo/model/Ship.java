package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Ship implements IFly {
    private String name;
    private Integer fuel;
    private Integer maxCrew;
    private List<CrewMember> crew;
    private Location location;

    public Ship(String name, int fuel, int maxCrew, Location defaultLocation) throws Exception {
        if(name != null && !name.equals("") && defaultLocation != null && fuel >= 0 && maxCrew > 0) {
            this.name = name;
            this.fuel = fuel;
            this.maxCrew = maxCrew;
            this.location = defaultLocation;
            this.crew = new ArrayList<>();
        }
        else {
            throw new Exception();
        }
    }

    public String getName() {
        return name;
    }

    public int getFuel() {
        return fuel;
    }

    public int getMaxCrew() {
        return maxCrew;
    }

    public Location getLocation() {
        return location;
    }

    public List<CrewMember> getCurrentCrew() {
        return crew;
    }

    public List<CrewMember> getCrew() {
        return crew;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public void setCrewMember(CrewMember member) throws Exception {
        if (!crew.contains(member) && crew.size() < maxCrew && member != null)
            this.crew.add(member);
        else
            throw new Exception();

    }

    public void fireCrewMember(CrewMember member) throws Exception {
        if (crew.contains(member))
            this.crew.remove(member);
        else
            throw new Exception();
    }

    @Override
    public void fly(Location location) throws Exception {
        if (fuel > 0 && location != this.location && location != null && crew.size() > 0) {
            this.fuel--;
            this.location = location;
        }
        else
            throw new Exception();
    }
}
