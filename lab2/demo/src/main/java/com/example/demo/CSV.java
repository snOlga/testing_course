package com.example.demo;

import java.util.function.DoubleUnaryOperator;

public class CSV {
    
public String csv(double x, double step, double max, DoubleUnaryOperator func) {
        String result = "";
        for (double i = x; i < max; i += step) {
            result += i + "," + func.applyAsDouble(i) + "\n";
        }
        return result;
    }
}
