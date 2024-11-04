package Test.operations;

import function.MathFunction;
import operations.DifferentialOperator;

public abstract class SteppingDifferentialOperator implements DifferentialOperator<MathFunction> {
    protected double step;

    public SteppingDifferentialOperator(double step){
        if(step <= 0 || Double.isInfinite(step) || Double.isNaN(step)){
            throw new IllegalArgumentException();
        }
        this.step = step;
    }
    public void stepSetter(double step){
        if(step <= 0 || Double.isInfinite(step) || Double.isNaN(step)){
            throw new IllegalArgumentException();
        }
        this.step = step;
    }
    public double stepGetter(){
        return step;
    }

    @Override
    public abstract MathFunction derive(MathFunction function);
}
