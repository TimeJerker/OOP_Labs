package Test;

import function.NewtonMethod;
import org.junit.jupiter.api.Test;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewtonMethodTest {
    @Test
    public void NewtonMethodTest1(){
        NewtonMethod NM = new NewtonMethod(x->Math.pow(x,2),x->2*x,0.01, -5);
        double x1 = NM.findRoot(NM.getx0(), NM.getEps());
        assertEquals(-0.078, x1,0.001);
    }
    @Test
    public void NewtonMethodTest2(){
        NewtonMethod NM = new NewtonMethod(x->Math.pow(x,2),x->2*x,0.01, 100);
        double x1 = NM.findRoot(NM.getx0(), NM.getEps());
        assertEquals(0.098, x1,0.001);
    }

}