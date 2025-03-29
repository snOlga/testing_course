package com.example.demo;

public class BasicLogarithm {
    
    public Double ln(Double x) {
        double y = (x - 1) / (x + 1);
        // return 2 * (y + Math.pow(y, 3) / 3 + Math.pow(y, 5) / 5 + Math.pow(y, 7) / 7);
        return Math.log(x);
    }
}
