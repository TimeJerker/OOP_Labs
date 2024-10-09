package function;

import java.util.Arrays;
import function.ArrayTabulatedFunction;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction {
    private double[] xValues;
    private double[] yValues;
    public ArrayTabulatedFunction(double[] xValues, double[] yValues){
        ArrayTabulatedFunction.Length(xValues, yValues);
        ArrayTabulatedFunction.Sorted(xValues);

        if (xValues.length < 2) {
            throw new IllegalArgumentException("The number of elements is less than two");
        }

        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        count = this.xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
        xValues = new double[count];
        yValues = new double[count];
        if(xFrom > xTo){
            double temporary = xFrom;
            xFrom = xTo;
            xTo = temporary;
        }
        if(xFrom == xTo){
            for(int i = 0; i < count; i++){
                xValues[i] = xFrom;
                yValues[i] = source.apply(xFrom);
            }
        }
        else{
            xValues[0] = xFrom;
            yValues[0] = source.apply(xFrom);
            xValues[count-1] = xTo;
            yValues[count-1] = source.apply(xTo);

            double path = Math.abs(xTo -xFrom)/count;
            double leftStep = xFrom;

            for(int i = 0; i != count; i++){
                xValues[i] = leftStep;
                yValues[i] = source.apply(xValues[i]);
                leftStep += path;
            }
        }

        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = this.xValues.length;
    }

    @Override
    public double getX(int id){
        return xValues[id];
    }
    @Override
    public double getY(int id){
        return yValues[id];
    }

    @Override
    public void setY(int index, double value) {
        yValues[index] = value;
    }

    @Override
    protected int floorIndexOfX(double x) {
        for (int i = 1; i < count; i++) {
            if (x < xValues[i]) {
                return i - 1;
            }
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new IllegalArgumentException("X is out of interpolation bounds");
        }
        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public int getCount(){
        return this.count;
    }

    @Override
    public double leftBound(){
        return xValues[0];
    }

    @Override
    public double rightBound(){
        return xValues[count-1];
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; ++i) {
            if (xValues[i] == x) return i;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; ++i) {
            if (yValues[i] == y) return i;
        }
        return -1;
    }
    public static void Length(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) throw new IllegalArgumentException("The number of X and Y does not match");

    }
    public static void Sorted(double[] xValues) {
        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i - 1] >= xValues[i]) throw new IllegalArgumentException("X is not ordered");;
        }
    }
}
