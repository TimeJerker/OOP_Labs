package Test.operations;

import function.MathFunction;
import function.SqrFunction;
import operations.MiddleSteppingDifferentialOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MiddleSteppingDifferentialOperatorTest {
    @Test
    public void testDerivativeOfSqrFunction() {
        MathFunction sqrFunction = new SqrFunction();
        double step = 1e-5;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);
        MathFunction derivative = operator.derive(sqrFunction);

        assertEquals(2.0, derivative.apply(1.0), 1e-3);
    }
}
