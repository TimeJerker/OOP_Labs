package ui;

import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import io.FunctionsIO;
import operations.TabulatedFunctionOperationService;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class OperationsWindow extends JDialog {
    private final TabulatedFunctionOperationService operationService;
    private TabulatedFunction firstFunction;
    private TabulatedFunction secondFunction;
    private TabulatedFunction resultFunction;

    private final JTable resultFunctionTable;

    private final DefaultTableModel firstTableModel;
    private final DefaultTableModel secondTableModel;
    private final DefaultTableModel resultTableModel;

    private final int operand_1 = 1;
    private final int operand_2 = 2;
    JFrame owner;

    public OperationsWindow(JFrame frame, TabulatedFunctionOperationService operationService) {
        super(frame, "Операции с табулированными функциями", true);
        owner = frame;
        this.operationService = operationService;
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        firstTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        secondTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        resultTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        JTable firstFunctionTable = createTable(firstTableModel, true, operand_1);
        JTable secondFunctionTable = createTable(secondTableModel, true, operand_2);
        resultFunctionTable = createTable(resultTableModel, false, -1);

        JPanel firstFunctionPanel = createFunctionPanel("Первая функция", firstFunctionTable,
                _ -> createFunction(1), _ -> loadFunction(1), _ -> saveFunction(1));
        JPanel secondFunctionPanel = createFunctionPanel("Вторая функция", secondFunctionTable,
                _ -> createFunction(2), _ -> loadFunction(2), _ -> saveFunction(2));
        JPanel resultFunctionPanel = createResultPanel();

        JPanel panel = new JPanel(){
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

        panel.setLayout(new GridLayout(4, 1));
        JButton sumButton = new RoundedButton("Сложение", new Color(172, 59, 97));
        JButton subtractButton = new RoundedButton("Вычитание", new Color(172, 59, 97));
        JButton multiplyButton = new RoundedButton("Умножение", new Color(172, 59, 97));
        JButton divideButton = new RoundedButton("Деление", new Color(172, 59, 97));
        sumButton.addActionListener(_ -> performOperation(1));
        subtractButton.addActionListener(_ -> performOperation(2));
        multiplyButton.addActionListener(_ -> performOperation(3));
        divideButton.addActionListener(_ -> performOperation(4));
        panel.add(sumButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(divideButton);

        JPanel functionsPanel = new JPanel(new GridLayout(1, 3));
        functionsPanel.add(firstFunctionPanel);
        functionsPanel.add(secondFunctionPanel);
        functionsPanel.add(resultFunctionPanel);
        add(functionsPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createFunctionPanel(String title, JTable table, ActionListener createListener, ActionListener loadListener, ActionListener saveListener) {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(100, 100, new Color(237, 199, 183), 0, getHeight(), new Color(237, 199, 183));

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(100, 100, new Color(237, 199, 183), 0, getHeight(), new Color(237, 199, 183));

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        JButton createButton = new RoundedButton("Создать", new Color(172, 59, 97));
        JButton loadButton = new RoundedButton("Загрузить", new Color(172, 59, 97));
        JButton saveButton = new RoundedButton("Сохранить", new Color(172, 59, 97));
        createButton.addActionListener(createListener);
        loadButton.addActionListener(loadListener);
        saveButton.addActionListener(saveListener);
        buttonPanel.add(createButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(100, 100, new Color(237, 199, 183), 0, getHeight(), new Color(237, 199, 183));

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Результат"));
        JScrollPane scrollPane = new JScrollPane(resultFunctionTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton saveButton = new RoundedButton("Сохранить", new Color(172, 59, 97));
        saveButton.addActionListener(_ -> saveFunction(3));
        panel.add(saveButton, BorderLayout.SOUTH);
        return panel;
    }

    private JTable createTable(DefaultTableModel tableModel, boolean editable, int operand) {
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable && column != 0;
            }
        };
        tableModel.addTableModelListener(e -> {
            if (operand == operand_1) {
                if (firstFunction != null && e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 1) {
                        try {
                            double newValue = Double.parseDouble(tableModel.getValueAt(row, column).toString());
                            firstFunction.setY(row, newValue);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else if (operand == operand_2) {
                if (secondFunction != null && e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 1) {
                        try {
                            double newValue = Double.parseDouble(tableModel.getValueAt(row, column).toString());
                            secondFunction.setY(row, newValue); // Синхронизация с функцией
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        return table;
    }

    private void performOperation(int operation) {
        if (firstFunction == null || secondFunction == null) {
            JOptionPane.showMessageDialog(this, "Обе функции должны быть созданы или загружены", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            switch (operation) {
                case 1:
                    resultFunction = operationService.add(firstFunction, secondFunction);
                    break;
                case 2:
                    resultFunction = operationService.subtract(firstFunction, secondFunction);
                    break;
                case 3:
                    resultFunction = operationService.multiply(firstFunction, secondFunction);
                    break;
                case 4:
                    resultFunction = operationService.division(firstFunction, secondFunction);
                    break;
            }
            updateTableWithFunction(resultTableModel, resultFunction);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при выполнении операции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createFunction(int operand){
        TabulatedFunctionFactory selectedFactory = operationService.factoryGetter();

        TabulatedFunction createdFunction = null;

        if (selectedFactory instanceof ArrayTabulatedFunctionFactory) {
            TableController arraysWindow = new TableController(owner, operationService.factoryGetter());
            arraysWindow.setVisible(true);
            createdFunction = arraysWindow.getTabulatedFunction();

        } else if (selectedFactory instanceof LinkedListTabulatedFunctionFactory) {
            MathFunctionController mathWindow = new MathFunctionController(owner, operationService.factoryGetter());
            mathWindow.setVisible(true);
            createdFunction = mathWindow.getTabulatedFunction();
        }

        if (createdFunction != null) {
            if (operand == 1) {
                firstFunction = createdFunction;
               updateTableWithFunction(firstTableModel, firstFunction);
            } else if (operand == 2) {
                secondFunction = createdFunction;
                updateTableWithFunction(secondTableModel, secondFunction);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Функция не была создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
       }
    }

    private void loadFunction(int operand) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try(FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                function.TabulatedFunction function = FunctionsIO.deserialize(bufferedInputStream);
                if (operand == 1) {
                    firstFunction = function;
                    updateTableWithFunction(firstTableModel, firstFunction);
                } else if (operand == 2) {
                    secondFunction = function;
                    updateTableWithFunction(secondTableModel, secondFunction);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFunction(int operand) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                function.TabulatedFunction function = (operand == 1) ? firstFunction : (operand == 2) ? secondFunction : resultFunction;
                FunctionsIO.serialize(bufferedOutputStream, function);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTableWithFunction(DefaultTableModel tableModel, function.TabulatedFunction function) {
        tableModel.setRowCount(0);
        for (int i = 0; i < function.getCount(); i++) {
            tableModel.addRow(new Object[]{function.getX(i), function.getY(i)});
        }
    }

    public static class RoundedButton extends JButton {
        public RoundedButton(String label, Color textColor) {
            super(label);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(textColor);
            setBackground(new Color(238, 226, 220));
            setFont(new Font("MerriWeather", Font.PLAIN, 16));
        }
    }
}