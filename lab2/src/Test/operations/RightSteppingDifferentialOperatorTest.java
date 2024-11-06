package Test.operations;

import function.MathFunction;
import function.SqrFunction;
import operations.RightSteppingDifferentialOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RightSteppingDifferentialOperatorTest {
    @Test
    public void testDerivativeOfSqrFunction() {
        MathFunction sqrFunction = new SqrFunction();

        double step = 1e-5;
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(step);

        MathFunction derivative = operator.derive(sqrFunction);

        assertEquals(0.0, derivative.apply(0.0), 1e-3);
    }
}
