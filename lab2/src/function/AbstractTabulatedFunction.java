package function;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthArrayException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected int count;

    protected abstract int floorIndexOfX (double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    public abstract double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY){
        //f(X) = f(X1)+( f(X2) — f(X1) )*(X — X1)/(X2 — X1)
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    @Override
    public int getCount(){
        return count;
    }
    @Override
    public double apply(double x){
        int id = indexOfX(x);

        if(x < getX(0)){
            return extrapolateLeft(x);
        }

        if(x > getX(getCount()-1)){
            return extrapolateRight(x);
        }

        if(id != -1){
            return getY(id);
        }

        id = floorIndexOfX(x);
        return interpolate(x, id);
    }


//    public static void Length(double[] xValues, double[] yValues) {
//        if (xValues.length != yValues.length) throw new IllegalArgumentException("The number of X and Y does not match");
//
//    }
//
//    public static void Sorted(double[] xValues) {
//        for (int i = 1; i < xValues.length; i++) {
//            if (xValues[i - 1] >= xValues[i]) throw new IllegalArgumentException("X is not ordered");;
//        }
//    }к

    public static void checkLengthIsTheSame(double[] xValues, double[] yValues){
        if(xValues.length != yValues.length) throw new DifferentLengthArrayException();
    }

    public static void checkSorted(double[] xValues){
        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i - 1] >= xValues[i]) throw new ArrayIsNotSortedException();
        }
    }
}
