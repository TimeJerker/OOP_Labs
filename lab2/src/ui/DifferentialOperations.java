package ui;

import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import function.TabulatedFunction;
import io.FunctionsIO;
import operations.TabulatedDifferentialOperator;
import ui.InputData.CellEditor;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class DifferentialOperations extends JDialog {
    private final TabulatedDifferentialOperator operationService;
    private TabulatedFunction function;
    private TabulatedFunction resultFunction;

    private final JTable resultFunctionTable;

    private final DefaultTableModel firstTableModel;
    private final DefaultTableModel resultTableModel;
    JFrame owner;

    public DifferentialOperations(JFrame frame, TabulatedDifferentialOperator operationService) {
        super(frame, "Операции с табулированными функциями", true);
        owner = frame;
        this.operationService = operationService;
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        firstTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);
        resultTableModel = new DefaultTableModel(new Object[]{"x", "y"}, 0);

        JTable functionTable = createTable(firstTableModel, true);
        resultFunctionTable = createTable(resultTableModel, false);

        JPanel firstFunctionPanel = createFunctionPanel(functionTable,
                _ -> createFunction(), _ -> loadFunction(), _ -> saveFunction(1));
        JPanel resultFunctionPanel = createResultPanel();

        JPanel operationPanel = new JPanel(){
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
        operationPanel.setLayout(new GridLayout(1, 1));

        JButton differentiateButton = createStyledButton("Дифференцировать");
        differentiateButton.addActionListener(_ -> performOperation());
        operationPanel.add(differentiateButton);

        JPanel functionsPanel = new JPanel(new GridLayout(1, 2)){
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
        functionsPanel.add(firstFunctionPanel);
        functionsPanel.add(resultFunctionPanel);

        add(functionsPanel, BorderLayout.CENTER);
        add(operationPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createFunctionPanel(JTable table, ActionListener createListener, ActionListener loadListener, ActionListener saveListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout()){
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(createStyledButton("Создать", createListener), gbc);

        gbc.gridx = 1;
        buttonPanel.add(createStyledButton("Загрузить", loadListener), gbc);

        gbc.gridx = 2;
        buttonPanel.add(createStyledButton("Cохранить", saveListener), gbc);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new RoundedButton(text, new Color(172, 59, 97));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = createStyledButton(text);
        button.addActionListener(listener);
        return button;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(200, 0, new Color(237, 199, 183), 0, getHeight(), new Color(237, 199, 183));

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder( "Результат"));

        JScrollPane scrollPane = new JScrollPane(resultFunctionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = createStyledButton("Сохранить", _ -> saveFunction(2));
        panel.add(saveButton, BorderLayout.SOUTH);

        return panel;
    }

    private JTable createTable(DefaultTableModel tableModel, boolean editable) {
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable && column != 0;
            }

        };
        tableModel.addTableModelListener(e -> {
            if (function != null && e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == 1) {
                    try {
                        double newValue = Double.parseDouble(tableModel.getValueAt(row, column).toString());
                        function.setY(row, newValue);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        table.setRowHeight(25);
        table.setDefaultEditor(Object.class, new CellEditor());
        return table;
    }

    private void performOperation() {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция должна быть создана или загружена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            resultFunction = operationService.derive(function);
            updateTableWithFunction(resultTableModel, resultFunction);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при выполнении операции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createFunction() {
        TabulatedFunctionFactory selectedFactory = operationService.getFactory();

        function.TabulatedFunction createdFunction = null;

        if (selectedFactory instanceof ArrayTabulatedFunctionFactory) {
            TableController arraysWindow = new TableController(owner, operationService.getFactory());
            arraysWindow.setVisible(true);
            createdFunction = arraysWindow.getTabulatedFunction();

        } else if (selectedFactory instanceof LinkedListTabulatedFunctionFactory) {
            MathFunctionController mathWindow = new MathFunctionController(owner, operationService.getFactory());
            mathWindow.setVisible(true);
            createdFunction = mathWindow.getTabulatedFunction();
        }

        if (createdFunction != null) {
            function = createdFunction;
            updateTableWithFunction(firstTableModel, function);
        } else {
            JOptionPane.showMessageDialog(this, "Функция не была создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFunction() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try(FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                function = FunctionsIO.deserialize(bufferedInputStream);
                updateTableWithFunction(firstTableModel, function);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Неподдерживаемый формат. Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
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
                TabulatedFunction function = (operand == 1) ? this.function : resultFunction;
                FunctionsIO.serialize(bufferedOutputStream, function);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTableWithFunction(DefaultTableModel tableModel, TabulatedFunction function) {
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