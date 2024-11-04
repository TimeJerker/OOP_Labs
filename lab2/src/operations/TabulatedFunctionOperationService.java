package operations;

import function.Point;
import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService {
    private TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory){
        this.factory = factory;
    }

    public TabulatedFunctionOperationService(){
        factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory factoryGetter(){
        return factory;
    }

    public void factorySetter(TabulatedFunctionFactory factory){
        this.factory = factory;
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        Point[] points = new Point[tabulatedFunction.getCount()];

        int i = 0;
        for (Point point : tabulatedFunction) {
            points[i] = point;
            i++;
        }

        return points;
    }


}
