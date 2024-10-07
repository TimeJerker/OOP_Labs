package function;

import org.junit.jupiter.api.Test;

public class SqrFunction implements MathFunction {
    public double apply(double x) {
        return java.lang.Math.pow(x, 2);
    }
}


