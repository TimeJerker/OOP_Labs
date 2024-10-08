package function;



public class ConstantFunction implements MathFunction {
    private final double ct;

    public ConstantFunction(double ct) {
        this.ct = ct;
    }

    public double getterCt() {
        return ct;
    }

    @Override
    public double apply(double x) {
        return ct;
    }

}


