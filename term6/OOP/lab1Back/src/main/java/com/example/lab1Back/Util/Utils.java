package com.example.lab1Back.Util;

import java.util.Random;

public class Utils {
    private final static Random random = new Random();

    public static Integer randomId() {
        return random.nextInt(Integer.MAX_VALUE);
    }
}
