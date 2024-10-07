package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import function.MethodSimpleIteration;
import org.junit.jupiter.api.Test;


class MethodSimpleIterationTest {
    @Test
    public void testSimpleIteration1(){
        MethodSimpleIteration identityFunction = new MethodSimpleIteration(0.01, 100, x->(1/Math.pow(x,2))-3);

        assertEquals(-2.880, identityFunction.findRoot(-2.5), 0.001);
        assertEquals(-2.880, identityFunction.findRoot(-10), 0.001);
    }
    @Test
    public void testSimpleIteration2(){
        MethodSimpleIteration identityFunction = new MethodSimpleIteration(0.01, 100, x->1/(Math.sqrt(x+3)));

        assertEquals(0.532, identityFunction.findRoot(100), 0.001);
    }
    @Test
    void testSimpleIterationDivergence() {
        // Уравнение, которое может не сходиться
        MethodSimpleIteration identityFunction = new MethodSimpleIteration(0.01, 100, x->1/Math.pow(x, 2));

        // Проверяем, что при запуске метода findRoot выбрасывается исключение
        assertThrows(IllegalStateException.class, () -> identityFunction.findRoot(2.0));
    }
}