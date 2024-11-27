package ui.NewFunctoins;

import ui.MyMap;

import java.lang.Math;

@MyMap(name = "Косинус", priority = 3)
public class CosFunction implements Derivative {
    private final double constant;
    public CosFunction(double constant){
        this.constant = constant;
    }
    public CosFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.cos(x);
    }

    @Override
    public double derivative(double x) {
        return -constant*Math.sin(x);
    }
}
