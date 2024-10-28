package Test.operations;

import function.ArrayTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.Point;
import operations.TabulatedFunctionOperationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionOperationServiceTest {

    @Test
    void testPointsLinkedListTabulatedFunction() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {4.0, 5.0, 6.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xArray, yArray);

        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertEquals(xArray.length, points.length);

        for (int i = 0; i < points.length; i++) {
            assertEquals(xArray[i], points[i].x);
            assertEquals(yArray[i], points[i].y);
        }
    }
}