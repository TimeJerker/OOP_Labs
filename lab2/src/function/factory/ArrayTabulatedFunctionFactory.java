package function.factory;

import function.ArrayTabulatedFunction;
import function.TabulatedFunction;

public class ArrayTabulatedFunctionFactory {
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }
}
