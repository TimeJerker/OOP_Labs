package Test;

import function.LinkedListTabulatedFunction;
import function.MathFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionTest {

    @Test
    void testConstructorWithArrays(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(4, linkedList.getCount());

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(xArray[i], linkedList.getX(i));
            assertEquals(yArray[i], linkedList.getY(i));
        }
    }

    @Test
    void testOrderEliminationForX(){

        double[] xArray = {1.0, 2.0, 1.0, 3.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testCountEliminationForX(){

        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testQuantityEliminationForX(){

        double[] xArray = {1.0};
        double[] yArray = {0.0};

        assertThrows(IllegalArgumentException.class, () -> new LinkedListTabulatedFunction(xArray, yArray));
    }

    @Test
    void testWithExtrapolationLeft(){

        double[] xArray = {1.0, 2.0, 3.5, 8.0};
        double[] yArray = {0.0, 2.0, 3.0, 1.5};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(-1.4, linkedList.apply(0.3), 0.1);
    }

    @Test
    void testWithExtrapolationRight(){

        double[] xArray = {1.0, 2.0, 3.5, 8.0};
        double[] yArray = {0.0, 2.0, 3.0, 1.5};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(0.167, linkedList.apply(12.0), 0.001);
    }

    @Test
    void testInterpolate(){

        double[] xArray = {1.0, 2.0, 4.5, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(1.26, linkedList.apply(9.0), 0.01);
        assertEquals(2.25, linkedList.apply(3.0), 0.01);
    }

    @Test
    void testInterpolationException() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 2.0, 3.0};

        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);

        assertThrows(IllegalArgumentException.class, () -> linkedListFunction.interpolate(3.5, 1));
    }

    @Test
    void testInterpolationException2() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 2.0, 3.0};

        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(xValues, yValues);
        assertThrows(IllegalArgumentException.class, () -> linkedListFunction.interpolate(3.5, -1));
    }

    @Test
    void testGetter(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.6};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(3.0, linkedList.getX(2));
        assertEquals(1.6, linkedList.getY(-1));
    }

    @Test
    void testSetter(){

        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList1 = new LinkedListTabulatedFunction(xArray, yArray);
        LinkedListTabulatedFunction linkedList2 = new LinkedListTabulatedFunction(xArray, yArray);

        linkedList1.setY(-3, 1.0);
        linkedList2.setY(1, 5.0);

        assertEquals(1.0, linkedList1.getY(-3));
        assertEquals(5.0, linkedList2.getY(1));
    }

    @Test
    void testConstructorWithFunction(){
        MathFunction function = x -> Math.pow(x,2) - 1;
        double xFrom = 1.0;
        double xTo = 4.0;
        int count = 6;

        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(function, xFrom, xTo, count);

        double[] rightX = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
        double[] rightY = {0.0, 1.25, 3.0, 5.25, 8, 11.25};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(rightX[i], linkedList.getX(i), 1e-9);
            assertEquals(rightY[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithFunctionXFromBiggerThanXTo(){
        MathFunction function = x -> Math.pow(x,2) - 1;
        double xFrom = 4.0;
        double xTo = 1.0;
        int count = 6;

        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(function, xFrom, xTo, count);

        double[] rightX = {1.0, 1.5, 2.0, 2.5, 3.0, 3.5};
        double[] rightY = {0.0, 1.25, 3.0, 5.25, 8, 11.25};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(rightX[i], linkedList.getX(i), 1e-9);
            assertEquals(rightY[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testConstructorWithFunctionValuesEqual(){
        MathFunction function = x -> Math.pow(x,2) - 1;
        double xFrom = 1.0;
        double xTo = 1.0;
        int count = 6;

        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(function, xFrom, xTo, count);

        double[] rightX = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        double[] rightY = {0.0, 0.0, 0.0, 0.0, 0, 0};

        for (int i = 0; i < linkedList.getCount(); i++) {
            assertEquals(rightX[i], linkedList.getX(i), 1e-9);
            assertEquals(rightY[i], linkedList.getY(i), 1e-9);
        }
    }

    @Test
    void testWithIndexOfXY(){

        double[] xArray = {1.0, 3.0, 4.5, 9.0};
        double[] yArray = {0.0, 2.0, 1.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);


        assertEquals(0, linkedList.indexOfX(1.0));
        assertEquals(1, linkedList.indexOfY(2.0));
        assertEquals(-1, linkedList.indexOfY(5.0));
        assertEquals(-1, linkedList.indexOfX(0.0));
        assertThrows(IllegalArgumentException.class, () -> linkedList.interpolate(2.0,-1));
    }

    @Test
    void testWithBounds(){

        double[] xArray = {1.0, 3.0, 7.5, 11.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.1};
        LinkedListTabulatedFunction linkedList = new LinkedListTabulatedFunction(xArray, yArray);

        assertEquals(1.0, linkedList.leftBound());
        assertEquals(11.0, linkedList.rightBound());
    }
}