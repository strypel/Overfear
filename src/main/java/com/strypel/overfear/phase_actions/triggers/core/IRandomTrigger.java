package com.strypel.overfear.phase_actions.triggers.core;

import java.util.Random;

public interface IRandomTrigger {
    double probability();
    default boolean randomIsTrue(){
        return getRandomBoolean(probability());
    }
    static boolean getRandomBoolean(double probability) {
        Random random = new Random();
        return random.nextDouble() < probability;
    }
}
