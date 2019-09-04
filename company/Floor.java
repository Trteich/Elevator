package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Floor {
    int number;
    int upCount;
    int downCount;
    int unlodedPass = 0;
    private int numOfPass =1 + new Random().nextInt(11 -1 );
    List<Passenger> passengers = new ArrayList<>();


    Floor(int number) {
        this.number = number;
    }

    void fillFloorWithPass() {
        Passenger p;
        while (passengers.size() != numOfPass) {
            p = new Passenger();
            if (p.passTargetFloor != number) passengers.add(p);
        }

    }

    void mangePassDirection() {
        upCount = 0;
        downCount = 0;
        for (Passenger p : passengers) {
            if (p.passTargetFloor > number) upCount++;
            if (p.passTargetFloor < number) downCount++;
        }
    }

}
