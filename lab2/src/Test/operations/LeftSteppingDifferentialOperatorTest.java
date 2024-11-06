package Test.operations;

import function.MathFunction;
import function.SqrFunction;
import operations.LeftSteppingDifferentialOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeftSteppingDifferentialOperatorTest {
    @Test
    public void testDerivativeOfSqrFunction() {
        MathFunction sqrFunction = new SqrFunction();

        double step = 1e-5;
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(step);

        MathFunction derivative = operator.derive(sqrFunction);

        assertEquals(4.0, derivative.apply(2.0), 1e-3);
    }
}
