import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Main {
    public static void main(String[] args) {
        TabulatedFunction equation = new TabulatedFunction(-3., 3., new double[]{-2.,-1., 0.,1.,2.,3.,4.});

        for(int i = 0 ; i < equation.getPointsCount(); i ++) {
            System.out.println(equation.getPointX(i) + " " + equation.getPointY(i));
        }
        equation.addPoint(new FunctionPoint(0.5, 45));
        equation.deletePoint(5);
        equation.setPointX(0, -5);
        equation.setPointY(4, 1000);
        double er = equation.getFunctionValue(2.5);
        equation.setPoint(2, new FunctionPoint(-0.7, -42.875)); //

        System.out.println("After modification:");

        for(int i = 0 ; i < equation.getPointsCount(); i ++) {
            System.out.println(equation.getPointX(i) + " " + equation.getPointY(i));
        }
        System.out.println(er);
    }
}

class TabulatedFunctionTest{

    TabulatedFunction eq =new TabulatedFunction(-3,3, new double[]{-2.,-1., 0.,1.,2.,3.,4.});

    @Test
    public void tabulatedFunctionAddPointTest(){
        Double[][] Arr= {{-3., -2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {0.5, 45.}, {1., 2.}, {2., 3.}, {3., 4.}};
        eq.addPoint(new FunctionPoint(0.5, 45));
        for(int i = 0; i < eq.getPointsCount(); i++) {
            Assertions.assertEquals(eq.getPointX(i),Arr[i][0]);
            Assertions.assertEquals(eq.getPointY(i),Arr[i][1]);
        }
    }
    @Test
    public void tabulatedFunctionDeletePointTest(){
        Double[][] Arr= {{-3., -2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {1., 2.}, {3., 4.}};
        eq.deletePoint(5);
        for(int i = 0; i < eq.getPointsCount(); i++) {
            Assertions.assertEquals(eq.getPointX(i),Arr[i][0]);
            Assertions.assertEquals(eq.getPointY(i),Arr[i][1]);
        }
    }
    @Test
    public void tabulatedFunctionSetPointXTest(){
        Double[][] Arr= {{-5., -2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {1., 2.}, {2., 3.}, {3., 4.}};
        eq.setPointX(0, -5);
        for(int i = 0; i < eq.getPointsCount(); i++) {
            Assertions.assertEquals(eq.getPointX(i),Arr[i][0]);
            Assertions.assertEquals(eq.getPointY(i),Arr[i][1]);
        }
    }
    @Test
    public void tabulatedFunctionSetPointYTest(){
        Double[][] Arr= {{-3., -2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {1., 1000.}, {2., 3.}, {3., 4.}};
        eq.setPointY(4, 1000);
        for(int i = 0; i < eq.getPointsCount(); i++) {
            Assertions.assertEquals(eq.getPointX(i),Arr[i][0]);
            Assertions.assertEquals(eq.getPointY(i),Arr[i][1]);
        }
    }
    @Test
    public void tabulatedFunctionSetPointTest(){
        Double[][] Arr= {{-3., -2.}, {-2., -1.}, {-0.7, -42.875}, {0., 1.}, {1., 2.}, {2., 3.}, {3., 4.}};
        eq.setPoint(2, new FunctionPoint(-0.7, -42.875));
        for(int i = 0; i < eq.getPointsCount(); i++) {
            Assertions.assertEquals(eq.getPointX(i),Arr[i][0]);
            Assertions.assertEquals(eq.getPointY(i),Arr[i][1]);
        }
    }

    @Test
    public void tabulatedFunctionGetFunctionValueTest(){
        double er = eq.getFunctionValue(2.5);
        Assertions.assertEquals(er, 3.5);
    }
}