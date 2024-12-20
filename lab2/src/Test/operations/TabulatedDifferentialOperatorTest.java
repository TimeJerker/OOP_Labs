package Test.operations;

import function.ArrayTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.SqrFunction;
import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TabulatedDifferentialOperatorTest {

    @Test
    void testConstructor1(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        assertInstanceOf(LinkedListTabulatedFunctionFactory.class, operator.getFactory());
    }

    @Test
    void testConstructor2(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        assertInstanceOf(ArrayTabulatedFunctionFactory.class, operator.getFactory());
    }

    @Test
    void testSet(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
        operator.setFactory(new LinkedListTabulatedFunctionFactory());
        assertInstanceOf(LinkedListTabulatedFunctionFactory.class, operator.getFactory());
    }

    @Test
    void testDeriveLinkedList(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(new LinkedListTabulatedFunction(x -> Math.pow(x,2) + x + 1, -2, 1, 4));

        double[] xValues = new double[] {-2.0, -1.25, -0.5, 0.25};
        double[] yValues = new double[] {-2.25, -0.75, 0.75, 0.75};

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i));
        }
    }

    @Test
    void testDeriveWithNewArrayFunction(){
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction function = operator.derive(new ArrayTabulatedFunction(x ->  Math.pow(x,2) + x + 1, -2, 1, 4));

        double[] xValues = new double[] {-2.0, -1.25, -0.5, 0.25};
        double[] yValues = new double[] {-2.25, -0.75, 0.75, 0.75};

        for (int i = 0; i < function.getCount(); i++) {
            assertEquals(xValues[i], function.getX(i));
            assertEquals(yValues[i], function.getY(i));
        }
    }

    @Test
    void testDeriveSynchronously() {
        TabulatedDifferentialOperator SetOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction NewArrayTabFunc = SetOperator.deriveSynchronously(new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 5));
        double[] xValues = {0, 2, 4, 6, 8};
        double[] yValues = {2, 6, 10, 14, 14};
        for (int i = 0; i < NewArrayTabFunc.getCount(); ++i) {
            assertEquals(xValues[i], NewArrayTabFunc.getX(i));
            assertEquals(yValues[i], NewArrayTabFunc.getY(i), 0.0001);
        }
    }
}