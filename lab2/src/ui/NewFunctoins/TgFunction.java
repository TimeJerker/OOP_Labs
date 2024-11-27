package ui.NewFunctoins;

import java.lang.Math;
import ui.MyMap;

@MyMap(name = "Тангенс", priority = 3)
public class TgFunction implements Derivative {
    private final double constant;
    public TgFunction(double constant){
        this.constant = constant;
    }
    public TgFunction(){
        this.constant = 1;
    }
    @Override
    public double apply(double x) {
        return constant*Math.tan(x);
    }

    @Override
    public double derivative(double x) {
        return constant/Math.pow(Math.cos(x), 2);
    }
}
