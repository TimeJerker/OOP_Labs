package Test;

import function.ZeroFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZeroFunctionTest {
    @Test
    public void testZeroFunction() {
        ZeroFunction zeroFunction = new ZeroFunction();

        assertEquals(zeroFunction.getterCt(), zeroFunction.apply(10));
        assertEquals(zeroFunction.getterCt(), zeroFunction.apply(0));
    }
}