package com.company;

public class Car {

    int hp;
    int countOfWheel;
    String name;
    String model;

    Engine engine;

    private class Engine {
        int maxHp;
        double volume;

        Engine(){
            volume = 1.0;
        }

        Engine(double volume){
            this.volume = volume;
        }

        int getMaxHp() {
            if (volume > 0.0 && volume <= 2.0) {
                return 150;
            } else if (volume > 2.0 && volume <= 3.0) {
                return 200;
            } else if (volume < 3.0 && volume <= 5) {
                return 300;
            } else {
                return 500;
            }
        }

    }

    
}
