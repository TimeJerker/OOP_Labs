package function.factory;

import function.LinkedListTabulatedFunction;
import function.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory {
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }
}
