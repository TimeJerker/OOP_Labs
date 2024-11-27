package ui;

import function.*;
import ui.NewFunctoins.*;
import function.TabulatedFunction;
import function.factory.LinkedListTabulatedFunctionFactory;
import ui.Input.DoubleNumeric;
import ui.Input.IntNumeric;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class MathFunctionController extends JFrame {
    private final JComboBox<String> functionComboBox;
    private final JTextField leftBoundField;
    private final JTextField rightBoundField;
    private final JTextField pointsCountField;
    private final Map<String, MathFunction> functionMap;
    final int PANEL_ROWS = 5;
    final int PANEL_COLUMNS = 2;
    final int WIDTH_WINDOW = 600; //Ширина окна
    final int HEIGHT_WINDOW = 400; //Высота окна
    private final LinkedListTabulatedFunctionFactory factory;
    private TabulatedFunction tabulatedFunction;
    JFrame frame = new JFrame();
    public MathFunctionController() {
        factory = new LinkedListTabulatedFunctionFactory();
        this.functionMap = createFunctionMap();
        frame.setTitle("Создание табулированной функции");
        frame.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        // Панель для ввода параметров
        JPanel inputPanel = new JPanel(new GridLayout(PANEL_ROWS, PANEL_COLUMNS));
        // Выпадающий список функций
        JLabel functionLabel = new JLabel("Выберите функцию:");
        functionComboBox = new JComboBox<>(functionMap.keySet().toArray(new String[0]));
        // Поля для ввода интервалов и количества точек
        JLabel leftBoundLabel = new JLabel("Левая граница:");
        leftBoundField = new JTextField();
        ((AbstractDocument) leftBoundField.getDocument()).setDocumentFilter(new DoubleNumeric());
        JLabel rightBoundLabel = new JLabel("Правая граница:");
        rightBoundField = new JTextField();
        ((AbstractDocument) rightBoundField.getDocument()).setDocumentFilter(new DoubleNumeric());
        JLabel pointsCountLabel = new JLabel("Количество точек:");
        pointsCountField = new JTextField();
        ((AbstractDocument) pointsCountField.getDocument()).setDocumentFilter(new IntNumeric());
        // Добавляем компоненты на панель
        inputPanel.add(functionLabel);
        inputPanel.add(functionComboBox);
        inputPanel.add(leftBoundLabel);
        inputPanel.add(leftBoundField);
        inputPanel.add(rightBoundLabel);
        inputPanel.add(rightBoundField);
        inputPanel.add(pointsCountLabel);
        inputPanel.add(pointsCountField);

        JButton createButton = new JButton("Создать");
        createButton.addActionListener(new CreateFunctionListener());

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(createButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private Map<String, MathFunction> createFunctionMap() {
        Map<String, MathFunction> map = new TreeMap<>(); // TreeMap для сортировки по ключу
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
                // Получаем выбранную функцию
                String selectedFunctionName = (String) functionComboBox.getSelectedItem();
                MathFunction selectedFunction = functionMap.get(selectedFunctionName);
                // Получаем введённые значения
                double leftX = Double.parseDouble(leftBoundField.getText());
                double rightX = Double.parseDouble(rightBoundField.getText());
                int pointsCount = Integer.parseInt(pointsCountField.getText());
                // Проверяем корректность ввода
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
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MathFunctionController.this, "Некорректный ввод!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(MathFunctionController.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
