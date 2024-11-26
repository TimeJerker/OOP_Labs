package operations;

import concurrent.SynchronizedTabulatedFunction;
import function.Point;
import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction>{

    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        double[] xValues = new double[function.getCount()];
        double[] yValues = new double[function.getCount()];

        for (int k = 0; k < points.length-1; k++) {
            xValues[k] = function.getX(k);
            yValues[k] = (function.getY(k+1) - function.getY(k))/(function.getX(k+1)-function.getX(k));
        }
        xValues[points.length-1] = function.getX(points.length-1);
        yValues[points.length-1] = (function.getY(points.length-1) - function.getY(points.length-2))/(function.getX(points.length-1)-function.getX(points.length-2));

        return factory.create(xValues, yValues);
    }

    @Override
    public double apply(double x) {
        return 0;
    }

    public TabulatedFunction deriveSynchronously(TabulatedFunction func) {
        if(!(func instanceof SynchronizedTabulatedFunction)) {
            func = new SynchronizedTabulatedFunction(func);
        }

        SynchronizedTabulatedFunction syncFunction = (SynchronizedTabulatedFunction) func;
        return syncFunction.doSynchronously(_ -> derive(syncFunction));
    }
}
