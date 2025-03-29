package com.example.demo;

public class BasicTrigonometry {

    private final int N = 10;

    public double sin(double x) {
        // return solve(x, N);
        return Math.sin(x);
    }

    private double solve(double x, int n) {
        if (n < 0)
            return 0;
        return (Math.pow(-1.00, n) * Math.pow(x, (2 * n + 1)) / factorial(2 * n + 1)) + solve(x, n - 1);
    }

    private long factorial(int a) {
        if (a <= 1)
            return 1;
        return a * factorial(a - 1);
    }
}
