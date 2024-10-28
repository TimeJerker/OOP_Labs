package operations;

import function.Point;
import function.TabulatedFunction;

public class TabulatedFunctionOperationService {

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
