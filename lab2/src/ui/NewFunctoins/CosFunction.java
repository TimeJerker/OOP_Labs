package ui.NewFunctoins;

import function.MathFunction;
import java.lang.Math;

public class CosFunction implements MathFunction {
    private final double constant;
    public CosFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.cos(x);
    }
}
