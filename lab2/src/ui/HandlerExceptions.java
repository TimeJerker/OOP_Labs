package ui;

import javax.swing.JOptionPane;

public class HandlerExceptions {

    public static int getPointCount(String input) {
        int pointCount;
        try {
            pointCount = Integer.parseInt(input);
            if (pointCount <= 1) {
                throw new IllegalArgumentException("Количество точек должно быть больше 1!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите корректное значение!");
        }

        return pointCount;
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
