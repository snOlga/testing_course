package com.example.demo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.demo.model.CrewMember;
import com.example.demo.model.Location;
import com.example.demo.model.Mood;
import com.example.demo.model.Ship;

@SpringBootTest // using only for loading to container
public class ModelTests {

    @Test
    void testShipName() throws Exception {
        String name = "name";
        Ship ship = new Ship(name, 0, 1, Location.EarthSky);
        Assert.isTrue(name.equals(ship.getName()), "");
    }

    @Test
    void testNullShipName() {
        try {
            new Ship(null, 0, 1, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testEmptyShipName() {
        try {
            new Ship("", 0, 1, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testLocation() throws Exception {
        Location location = Location.EarthSky;
        Ship ship = new Ship("name", 0, 1, location);
        Assert.isTrue(ship.getLocation() == location, "");
    }

    @Test
    void testNullLocation() {
        try {
            new Ship("name", 0, 1, null);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFuel() throws Exception {
        int fuel = 10;
        Ship ship = new Ship("name", fuel, 1, Location.EarthSky);
        Assert.isTrue(ship.getFuel() == fuel, "");
    }

    @Test
    void testNullFuel() {
        Integer fuel = null;
        try {
            new Ship("name", fuel, 1, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testNegativeFuel() {
        try {
            new Ship("name", -1, 1, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testMaxCrew() throws Exception {
        int crew = 10;
        Ship ship = new Ship("name", 0, crew, Location.EarthSky);
        Assert.isTrue(ship.getMaxCrew() == crew, "");
    }

    @Test
    void testNullMaxCrew() {
        Integer crew = null;
        try {
            new Ship("name", 0, crew, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testNegativeMaxCrew() {
        try {
            new Ship("name", 0, -1, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testZeroMaxCrew() {
        try {
            new Ship("name", 0, 0, Location.EarthSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testSetCrew() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        Assert.isTrue(ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 1, "");
    }

    @Test
    void testSetMoreMaxCrew() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        CrewMember member2 = new CrewMember("name2", Mood.Happy);
        ship.setCrewMember(member);
        try {
            ship.setCrewMember(member2);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testSetSameCrew() throws Exception {
        Ship ship = new Ship("name", 1, 2, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        CrewMember member2 = member;
        ship.setCrewMember(member);
        try {
            ship.setCrewMember(member2);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testSetNullCrew() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        try {
            ship.setCrewMember(null);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFireCrew() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        Assert.isTrue(ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 1, "");
        ship.fireCrewMember(member);
        Assert.isTrue(!ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 0, "");
    }

    @Test
    void testHireCrewAgain() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        Assert.isTrue(ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 1, "");
        ship.fireCrewMember(member);
        Assert.isTrue(!ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 0, "");
        ship.setCrewMember(member);
        Assert.isTrue(ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 1, "");
    }

    @Test
    void testFireSameCrew() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        Assert.isTrue(ship.getCurrentCrew().contains(member), "");
        Assert.isTrue(ship.getCurrentCrew().size() == 1, "");
        ship.fireCrewMember(member);
        try {
            ship.fireCrewMember(member);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFireNullCrew() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        try {
            ship.fireCrewMember(null);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testNameCrewMember() throws Exception {
        String name = "name";
        CrewMember member = new CrewMember(name, Mood.Dreaming);
        Assert.isTrue(name.equals(member.getName()), "");
    }

    @Test
    void testNullCrewMember() {
        try {
            new CrewMember(null, Mood.Dreaming);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testEmptyCrewMember() {
        try {
            new CrewMember("", Mood.Dreaming);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testMood() throws Exception {
        Mood mood = Mood.Dreaming;
        CrewMember member = new CrewMember("name", mood);
        Assert.isTrue(member.getMood() == mood, "");
    }

    @Test
    void testNullMood() {
        try {
            new CrewMember("name", null);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFly() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        ship.setCrewMember(new CrewMember("name", Mood.Dreaming));
        ship.fly(Location.Space);
        Assert.isTrue(ship.getLocation() == Location.Space, "");
        Assert.isTrue(ship.getFuel() == 0, "");
    }

    @Test
    void testFlyWithZeroFuel() throws Exception {
        Ship ship = new Ship("name", 0, 1, Location.EarthSky);
        ship.setCrewMember(new CrewMember("name", Mood.Dreaming));
        try {
            ship.fly(Location.Space);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFlyToUnknownPlace() throws Exception {
        Ship ship = new Ship("name", 1, 1, Location.EarthSky);
        ship.setCrewMember(new CrewMember("name", Mood.Dreaming));
        try {
            ship.fly(null);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFlyWithNoCaptain() throws Exception {
        Ship ship = new Ship("name", 0, 1, Location.EarthSky);
        try {
            ship.fly(Location.Space);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFlyWithNoCaptain2() throws Exception {
        Ship ship = new Ship("name", 10, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        ship.fireCrewMember(member);
        try {
            ship.fly(Location.Space);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFlyWithNoCaptain3() throws Exception {
        Ship ship = new Ship("name", 10, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        ship.fly(Location.Space);
        ship.fireCrewMember(member);
        try {
            ship.fly(Location.MarsSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testFlyToSameSpace() throws Exception {
        Ship ship = new Ship("name", 10, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        ship.fly(Location.Space);
        try {
            ship.fly(Location.Space);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }

    @Test
    void testDecreaseFuel() throws Exception {
        int fuel = 10;
        Ship ship = new Ship("name", fuel, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        ship.fly(Location.Space);
        Assert.isTrue(ship.getFuel() < fuel, "");
    }

    @Test
    void testFlyNoFuel() throws Exception {
        int fuel = 1;
        Ship ship = new Ship("name", fuel, 1, Location.EarthSky);
        CrewMember member = new CrewMember("name", Mood.Dreaming);
        ship.setCrewMember(member);
        ship.fly(Location.Space);
        try {
            ship.fly(Location.MarsSky);
            fail();
        } catch (Exception e) {
            Assert.isTrue(true, "");
        }
    }
}
