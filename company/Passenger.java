package com.company;

import java.util.Random;

class Passenger {
    int passTargetFloor = 1 + new Random().nextInt(Building.FLOOR_QUANTITY -1);
}
