package Test;

import static org.junit.jupiter.api.Assertions.*;
import function.ConstantFunction;
import org.junit.jupiter.api.Test;

public class ConstantFunctionTest {
    @Test
    public void testConstantFunction() {
        ConstantFunction constantFunction = new ConstantFunction(100);

        assertEquals(constantFunction.getterCt(), constantFunction.apply(10), 0.001);
        assertEquals(constantFunction.getterCt(), constantFunction.apply(0), 0.001);
    }

}