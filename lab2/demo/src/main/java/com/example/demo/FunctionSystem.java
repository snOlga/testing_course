package com.example.demo;

import java.util.function.DoubleUnaryOperator;

public class FunctionSystem implements DoubleUnaryOperator {

    private Trigonometry trigonometry;
    private Logarithm logarithm;

    public FunctionSystem(Trigonometry trigonometry, Logarithm logarithm) {
        this.trigonometry = trigonometry;
        this.logarithm = logarithm;
    }

    public Double count(Double x) {
        if (x <= 0)
            return firstFunc(x);
        else
            return secondFunc(x);
    }

    private Double firstFunc(Double x) {
        return Math.pow(
                Math.pow(Math.pow(Math.pow(trigonometry.sin(x), 3), 3), 2)
                        * (trigonometry.sec(x) * trigonometry.sec(x)),
                3);
    }

    private Double secondFunc(Double x) {
        double log5 = logarithm.log5(x);
        double log2 = logarithm.log2(x);
        double ln = logarithm.ln(x);
        return Math.pow(((Math.pow(log5, 2)) - log5) / log5, 2)
                + Math.pow(ln * log2, 3);
    }

    @Override
    public double applyAsDouble(double operand) {
        return count(operand);
    }
}
