package com.learning.basics;

public class Calculator {

    private Calculator() {
    }

    private static Calculator instance = new Calculator();

    public static Calculator getInstance() {
        return instance;
    }

    public int add(int a, int b) {
        return a + b;
    }
}
