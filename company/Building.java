package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Building {
    static final int FLOOR_QUANTITY = 5 + new Random().nextInt(11-5);
    List<Floor> floors = new ArrayList<>(FLOOR_QUANTITY);
    int passTotal;

    void createBuilding(){
        Floor floor;
        for (int i = 0; i < FLOOR_QUANTITY; i++){
           floor = new Floor(i+1);
           floor.fillFloorWithPass();
           floor.mangePassDirection();
           floors.add(floor);
        }
        totalPassCount();
    }

    private void totalPassCount(){
        passTotal = 0;
        for (Floor f: floors) passTotal += f.passengers.size();
    }
}
