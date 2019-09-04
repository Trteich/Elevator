package com.company;

import java.util.*;
import java.util.stream.Collectors;

class Elevator {
    private int currentFloor = 0;
    private int targetFloor = 0;
    private int direction = 1;
    private int capacity = 5;
    private int transportedPass = 0;

    private Building building;
    private List<Passenger> pickedUpPassengers = new ArrayList<>();

    Elevator(Building building) {
        this.building = building;
    }

    private void unloadPass(Floor floor) {

        List<Passenger> unloadedPass = pickedUpPassengers.stream()
                .filter(p -> p.passTargetFloor == floor.number).collect(Collectors.toList());
        pickedUpPassengers.removeAll(unloadedPass);
        floor.unlodedPass += unloadedPass.size();
        transportedPass += unloadedPass.size();

    }


    private void loadPass(Floor floor) {
        List<Passenger> passengers = floor.passengers;
        for (int i = 0; i < passengers.size() && pickedUpPassengers.size() != capacity; i++) {
            Passenger passenger = passengers.get(i);
            if (direction == 1 && passenger.passTargetFloor > floor.number) {
                pickedUpPassengers.add(passenger);
                setTargetFloor(passenger);
            }

            if (direction == -1 && passenger.passTargetFloor < floor.number) {
                pickedUpPassengers.add(passenger);
                setTargetFloor(passenger);
            }
        }
        passengers.removeAll(pickedUpPassengers);
    }


    void start() {

        ListIterator<Floor> iterator = building.floors.listIterator();
        Floor f = iterator.next();

        while (building.passTotal > transportedPass){

            currentFloor = f.number;

            if (!pickedUpPassengers.isEmpty()) {
                unloadPass(f);
//                if (transportedPass == building.passTotal) break;
            }

            if (currentFloor == targetFloor) {
                changeDirection(f, iterator);
            }

            if (pickedUpPassengers.size() < capacity && direction == 1 && f.upCount != 0) {
                loadPass(f);
                f.mangePassDirection();
            }

            if (pickedUpPassengers.size() < capacity && direction == -1 && f.downCount != 0) {
                loadPass(f);
                f.mangePassDirection();
            }


            Gui gui = new Gui();
            gui.out();
            f = nextFloor(iterator);

        }

    }

    private void changeDirection(Floor floor, ListIterator<Floor> iterator) {
        if (!floor.passengers.isEmpty()){
            if (floor.upCount > floor.downCount) direction = 1;
            else direction = -1;
        }
        if (floor.passengers.isEmpty()){
            if (building.floors.size()/2 >= floor.number) {
                direction = 1;
                targetFloor = building.floors.size();
            }
            else {
                direction = -1;
                targetFloor = 1;
            }
        }
        nextFloor(iterator);
    }

    private Floor nextFloor(ListIterator<Floor> iterator) {
        Floor f = null;
        if (direction == 1) f = iterator.next();
        if (direction == -1) f = iterator.previous();
        return f;

    }

    private void setTargetFloor(Passenger passenger) {
        if (direction == 1 && passenger.passTargetFloor > targetFloor) targetFloor = passenger.passTargetFloor;
        if (direction == -1 && passenger.passTargetFloor < targetFloor) targetFloor = passenger.passTargetFloor;
    }


    private class Gui {

        List<Floor> floors = building.floors;

        String[] elevatorVisualisation() {
            String[] elevatorVis;
            if (direction == 1) {
                elevatorVis = new String[]{"^", " ", " ", " ", " ", " ", "^"};
            } else {
                elevatorVis = new String[]{"v", " ", " ", " ", " ", " ", "v"};
            }

            for (int i = 1, k = 0; i < 6 && k != pickedUpPassengers.size(); i++, k++) {
                int m = pickedUpPassengers.get(k).passTargetFloor;
                elevatorVis[i] = Integer.toString(m);
            }

            return elevatorVis;
        }

        String[] floorVisualisation(Floor floor) {
            int unloadedPassengers = floor.unlodedPass;
            String[] floorVis = {Integer.toString(floor.number), "-", Integer.toString(unloadedPassengers), "|", " ", " ", " ", " ", " ", " ", " ", "|", "", "", "", "", "", "", "", "", "", ""};
            List<Passenger> passengers = floor.passengers;
            Passenger p;
            for (int i = 12, k = 0; k < passengers.size(); i++, k++) {
                p = passengers.get(k);
                floorVis[i] = Integer.toString(p.passTargetFloor);
            }

            return floorVis;
        }

        void out() {
            System.out.println("---- Next Step ----");
            String[] elevatorVisualisation = elevatorVisualisation();
            for (int i = floors.size() - 1; i >= 0; i--) {
                Floor floor = floors.get(i);
                String[] floorVisualisation = floorVisualisation(floor);
                if (i == currentFloor - 1) {
                    for (int k = 0, m = 4; k < elevatorVisualisation.length; k++, m++) {
                        floorVisualisation[m] = elevatorVisualisation[k];
                    }
                }
                for (String s : floorVisualisation) System.out.print(s + " ");
                System.out.println();
            }
        }


    }


}
