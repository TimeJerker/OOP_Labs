package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import function.MethodSimpleIteration;
import org.junit.jupiter.api.Test;


class MethodSimpleIterationTest {
    @Test
    public void testSimpleIteration(){
        MethodSimpleIteration identityFunction = new MethodSimpleIteration(0.01, 100, x->(x/2)-1 );

        assertEquals(-2.0078125, identityFunction.findRoot(-3));
        assertEquals(-1.994140625, identityFunction.findRoot(10));
        assertEquals(-1.9921875, identityFunction.findRoot(0));
    }
}