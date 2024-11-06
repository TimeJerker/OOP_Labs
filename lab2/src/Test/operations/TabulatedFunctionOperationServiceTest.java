package Test.operations;

import function.ArrayTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.Point;
import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
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

    @Test
    void testPointsArrayTabulatedFunction() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {5.0, 6.0, 7.0, 8.0};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xArray, yArray);

        Point[] points = TabulatedFunctionOperationService.asPoints(arrayTabulatedFunction);

        assertEquals(xArray.length, points.length);

        for (int i = 0; i < xArray.length; i++) {
            assertEquals(xArray[i], points[i].x, 1e-9);
            assertEquals(yArray[i], points[i].y, 1e-9);
        }
    }

    @Test
    public void testMultiply() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {4.0, 5.0, 6.0};

        TabulatedFunction f1 = arrayFactory.create(xValues, yValues);
        TabulatedFunction f2 = linkedListFactory.create(xValues, yValues);

        TabulatedFunction result = service.multiply(f1, f2);
        double[] expectedY = {16.0, 25.0, 36.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void testDivision() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {5.0, 6.0, 6.0};
        double[] yValues2 = {1.0, 2.0, 3.0};

        TabulatedFunction f1 = arrayFactory.create(xValues, yValues1);
        TabulatedFunction f2 = linkedListFactory.create(xValues, yValues2);

        TabulatedFunction result = service.division(f1, f2);
        double[] expectedY = {5.0, 3.0, 2.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void asPointsTest() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 2.0, 3.0};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        assertEquals(3, points.length);
    }

    @Test
    public void addTest() {
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {1.0, 2.0, 3.0};
        double[] yValues2 = {4.0, 5.0, 6.0};

        TabulatedFunction f1 = factory.create(xValues, yValues1);
        TabulatedFunction f2 = factory.create(xValues, yValues2);

        TabulatedFunction result = service.add(f1, f2);
        double[] expectedY = {5.0, 7.0, 9.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void subtractTest() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {5.0, 6.0, 7.0};
        double[] yValues2 = {1.0, 2.0, 3.0};

        TabulatedFunction f1 = arrayFactory.create(xValues, yValues1);
        TabulatedFunction f2 = linkedListFactory.create(xValues, yValues2);

        TabulatedFunction result = service.subtract(f1, f2);
        double[] expectedY = {4.0, 4.0, 4.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void MultiplyTest() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {5.0, 6.0, 7.0};
        double[] yValues2 = {1.0, 2.0, 3.0};

        TabulatedFunction f1 = arrayFactory.create(xValues, yValues1);
        TabulatedFunction f2 = linkedListFactory.create(xValues, yValues2);

        TabulatedFunction result = service.multiply(f1, f2);
        double[] expectedY = {5.0, 12.0, 21.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }

    @Test
    public void DivisionTest() {
        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory linkedListFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(arrayFactory);

        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues1 = {5.0, 6.0, 6.0};
        double[] yValues2 = {1.0, 2.0, 3.0};

        TabulatedFunction f1 = arrayFactory.create(xValues, yValues1);
        TabulatedFunction f2 = linkedListFactory.create(xValues, yValues2);

        TabulatedFunction result = service.division(f1, f2);
        double[] expectedY = {5.0, 3.0, 2.0};

        for (int i = 0; i < result.getCount(); i++) {
            assertEquals(expectedY[i], result.getY(i));
        }
    }
}