package com.example.demo;

public class Sin {
    private static final int N = 10;

    public static double solve(double x) {
        return solve(((x % 360) * Math.PI / 180.00), N);
    }

    private static double solve(double x, int n) {
        if (n < 0)
            return 0;
        return (Math.pow(-1.00, n) * Math.pow(x, (2 * n + 1)) / factorial(2 * n + 1)) + solve(x, n - 1);
    }

    private static long factorial(int a) {
        if (a <= 1)
            return 1;
        return a * factorial(a - 1);
    }
}
