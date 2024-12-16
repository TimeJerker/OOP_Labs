package ui;

import function.*;
import function.factory.TabulatedFunctionFactory;
import ui.NewFunctoins.*;
import function.TabulatedFunction;
import function.factory.LinkedListTabulatedFunctionFactory;
import ui.InputData.DoubleNumeric;
import ui.InputData.IntNumeric;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class MathFunctionController extends JDialog {
    private final JComboBox<String> functionComboBox;
    private final JTextField leftBoundField;
    private final JTextField rightBoundField;
    private final JTextField pointsCountField;
    private final Map<String, MathFunction> functionMap;
    final int PANEL_ROWS = 5;
    final int PANEL_COLUMNS = 2;
    private final TabulatedFunctionFactory factory;
    private TabulatedFunction tabulatedFunction;
    JFrame frame = new JFrame();

    public MathFunctionController(JFrame frame, TabulatedFunctionFactory factory) {
        super(frame, true);
        this.factory = new LinkedListTabulatedFunctionFactory();
        this.functionMap = createFunctionMap();
        frame.setTitle("Создать табулированную функцию");
        setSize(600, 400);
        setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(200, 0, new Color(237, 199, 183), 0, getHeight(), new Color(172, 59, 97)); // Нижняя часть фона (более светлый серый)

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel functionLabel = new JLabel("Выберите функцию:");
        functionComboBox = new JComboBox<>(functionMap.keySet().toArray(new String[0]));
        JLabel leftBoundLabel = new JLabel("Левая граница:");
        leftBoundField = new JTextField();
        ((AbstractDocument) leftBoundField.getDocument()).setDocumentFilter(new DoubleNumeric());
        JLabel rightBoundLabel = new JLabel("Правая граница:");
        rightBoundField = new JTextField();
        ((AbstractDocument) rightBoundField.getDocument()).setDocumentFilter(new DoubleNumeric());
        JLabel pointsCountLabel = new JLabel("Количество точек:");
        pointsCountField = new JTextField();
        ((AbstractDocument) pointsCountField.getDocument()).setDocumentFilter(new IntNumeric());

        panel.add(functionLabel);
        panel.add(functionComboBox);
        panel.add(leftBoundLabel);
        panel.add(leftBoundField);
        panel.add(rightBoundLabel);
        panel.add(rightBoundField);
        panel.add(pointsCountLabel);
        panel.add(pointsCountField);

        JButton createButton = new JButton("Создать");
        createButton.addActionListener(new CreateFunctionListener());

        add(panel, BorderLayout.CENTER);
        add(createButton, BorderLayout.SOUTH);
        panel.setVisible(true);
    }

    private Map<String, MathFunction> createFunctionMap() {
        Map<String, MathFunction> map = new TreeMap<>();
        map.put("Квадратичная функция", new SqrFunction());
        map.put("Тождественная функция", new IdentityFunctions());
        map.put("Функция константы 0", new ZeroFunction());
        map.put("Функция константы 1", new UnitFunction());
        map.put("Синус", new SinFunction());
        map.put("Косинус", new CosFunction());
        map.put("Тангенс", new TgFunction());
        return map;
    }

    private class CreateFunctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedFunctionName = (String) functionComboBox.getSelectedItem();
                MathFunction selectedFunction = functionMap.get(selectedFunctionName);

                double leftX = Double.parseDouble(leftBoundField.getText());
                double rightX = Double.parseDouble(rightBoundField.getText());
                int pointsCount = Integer.parseInt(pointsCountField.getText());

                if (leftX >= rightX) {
                    throw new IllegalArgumentException("Левая граница должна быть меньше правой.");
                }
                if (pointsCount < 2) {
                    throw new IllegalArgumentException("Количество точек должно быть больше 1.");
                }
                double[] xValues = new double[pointsCount];
                double[] yValues = new double[pointsCount];
                double step = (rightX - leftX) / (pointsCount - 1);
                for (int i = 0; i < pointsCount; i++) {
                    xValues[i] = leftX + i * step;
                    yValues[i] = selectedFunction.apply(xValues[i]);
                }

                tabulatedFunction = factory.create(xValues, yValues);
                JOptionPane.showMessageDialog(MathFunctionController.this, "Функция создана!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MathFunctionController.this, "Некорректный ввод!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(MathFunctionController.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public TabulatedFunction getTabulatedFunction() {
        return tabulatedFunction;
    }

//    public static class Main1 {
//        public static void main(String[] args) {
//            // Убедитесь, что Swing компоненты создаются в потоке событий
//            SwingUtilities.invokeLater(() -> {
//                // Создаем экземпляр JFrame (родительское окно) для диалога.
//                JFrame frame = new JFrame();
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setVisible(false); // Скрываем главное окно
//
//                // Создаем экземпляр TableController
//                new MathFunctionController(frame, new LinkedListTabulatedFunctionFactory());
//            });
//        }
//    }
}
