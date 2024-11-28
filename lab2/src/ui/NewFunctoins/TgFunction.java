package ui.NewFunctoins;

import java.lang.Math;
import function.MathFunction;

public class TgFunction implements MathFunction {
    private final double constant;
    public TgFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.tan(x);
    }
}
