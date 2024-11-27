package ui.NewFunctoins;

import ui.MyMap;

@MyMap(name = "Синус", priority = 3)
public class SinFunction implements Derivative {
    private final double constant;
    public SinFunction(double constant){
        this.constant = constant;
    }
    public SinFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.sin(x);
    }

    @Override
    public double derivative(double x) {
        return constant*Math.cos(x);
    }
}
