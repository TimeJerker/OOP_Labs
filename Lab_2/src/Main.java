import functions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Main {
    public static void main(String[] args) {
        TabulatedFunction equation = new TabulatedFunction(-3., 3., 7);

        for(int i = 0 ; i < equation.getPointsCount(); i ++) {
            System.out.println(equation.getPointX(i) + " " + equation.getPointY(i));
        }
        equation.addPoint(new FunctionPoint(0.5, 45));
        equation.deletePoint(5);
        equation.setPointX(0, -5);
        equation.setPointY(4, 1000); //
        equation.setPoint(2, new FunctionPoint(-0.7, -42.875)); //

        System.out.println("...After modification:");

        for(int i = 0 ; i < equation.getPointsCount(); i ++) {
            System.out.println(equation.getPointX(i) + " " + equation.getPointY(i));
        }
    }
}

//public class TabulatedFunctionTest{
//    @Test
//    public void tabulatedFunctionTest(){
////        TabulatedFunction equation = new TabulatedFunction(-3, 3, new double[] {-2, -1, 0, 1, 2, 3, 4});
////        equation.addPoint(new FunctionPoint(2, -5));
////        Double[][] arr = {{-3.,-2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {1., 2.}, {2., -5.}, {3.,4.}};
////
////        for(int i = 0; i < equation.getPointsCount(); i++) {
////            Assertions.assertArrayEquals(arr[i], equation[i]);
////        }
//        Double[][] Arr= {{-3., 2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {1., 2.}, {2., 3.}, {3., 4.}};
//        TabulatedFunction eq =new TabulatedFunction(-3,3, new double[]{-2.,-1.,-0.,1.,2.,3.,4.});
//        eq.addPoint(new FunctionPoint(2,-5));
//        Double[][] arr= {{-3., 2.}, {-2., -1.}, {-1., 0.}, {0., 1.}, {1., 2.}, {2., -5.}, {3., 4.}};
//        Assertions.assertArrayEquals(arr,Arr);
//    }
//
//}
//}