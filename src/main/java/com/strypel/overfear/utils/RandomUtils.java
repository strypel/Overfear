package com.strypel.overfear.utils;

import java.util.Random;

public class RandomUtils {
    public static double rnd(double min,double max){
        Random random = new Random();
        int rnd = (int) (min + (max - min) * random.nextDouble());
        return rnd;
    }
}
