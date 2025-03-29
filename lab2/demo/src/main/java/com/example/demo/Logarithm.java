package com.example.demo;

public class Logarithm {

    private BasicLogarithm basicLogarithm;

    public Logarithm(BasicLogarithm basicLogarithm) {
        this.basicLogarithm = basicLogarithm;
    }

    public Double log2(Double x) {
        return (basicLogarithm.ln(x) / basicLogarithm.ln(2.00));
    }

    public Double log5(Double x) {
        return (basicLogarithm.ln(x) / basicLogarithm.ln(5.00));
    }

    public Double ln(Double x) {
        return basicLogarithm.ln(x);
    }
}
