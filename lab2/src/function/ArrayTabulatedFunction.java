package function;

import java.util.Arrays;

public abstract class ArrayTabulatedFunction extends AbstractTabulatedFunction {
    private double[] xValues;
    private double[] yValues;
    public ArrayTabulatedFunction(double[] xValues, double[] yValues){
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

            double path = Math.abs(xTo -xFrom +1)/count;
            double leftStep = xFrom;

            for(int i = 0; i != count; i++){
                xValues[i] = leftStep;
                yValues[i] = source.apply(xFrom);
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

}
