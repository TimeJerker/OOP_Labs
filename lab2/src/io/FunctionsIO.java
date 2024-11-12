package io;

import function.Point;
import function.TabulatedFunction;
import function.factory.TabulatedFunctionFactory;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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
    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        try {
            int count = Integer.parseInt(reader.readLine());

            double[] xValues = new double[count];
            double[] yValues = new double[count];

            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                String[] parts = line.split(" ");

                try {
                    xValues[i] = numberFormatter.parse(parts[0]).doubleValue();
                    yValues[i] = numberFormatter.parse(parts[1]).doubleValue();
                } catch (ParseException e) {
                    throw new IOException();
                }
            }
            return factory.create(xValues, yValues);
        } catch (IOException ex) {
            throw ex;
        } catch (NumberFormatException e) {
            throw new IOException();
        }
    }
}