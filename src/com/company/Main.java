package com.company;

public class Main {

    public static void main(String[] args) {
        Building building = new Building();
        building.createBuilding();

        Elevator elevator = new Elevator(building);
        elevator.start();
    }
}
