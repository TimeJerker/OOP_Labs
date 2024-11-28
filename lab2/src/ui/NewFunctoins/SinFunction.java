package ui.NewFunctoins;

import function.MathFunction;

public class SinFunction implements MathFunction {
    private final double constant;
    public SinFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.sin(x);
    }
}
