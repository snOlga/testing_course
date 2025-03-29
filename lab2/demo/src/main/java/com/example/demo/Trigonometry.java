package com.example.demo;

public class Trigonometry {

    private BasicTrigonometry basicTrigonometry;

    public Trigonometry(BasicTrigonometry basicTrigonometry) {
        this.basicTrigonometry = basicTrigonometry;
    }

    public Double cos(Double x) {
        return 1 - basicTrigonometry.sin(x);
    }

    public Double sec(Double x) {
        return 1 / (cos(x));
    }

    public Double sin(Double x) {
        return basicTrigonometry.sin(x);
    }
}
