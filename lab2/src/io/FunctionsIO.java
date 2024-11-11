package io;

import function.Point;
import function.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public final class FunctionsIO {
    private FunctionsIO(){
        throw new UnsupportedOperationException();
    }
    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function){
        PrintWriter file = new PrintWriter(writer);

        int count = function.getCount();

        file.println(count);

        for(Point point : function){
            double x = point.x;
            double y = point.y;
            file.printf("%f %f\n",x,y);
        }
        file.flush();
    }
}