package ui;

import functions.TabulatedFunction;
import io.FunctionsIO;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;
import ui.InputData.DoubleNumeric;
import ui.InputData.IntNumeric;
import ui.InputData.CellEditor;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class DifferentialOperations extends JDialog {
    private final TabulatedDifferentialOperator operationService;
    private TabulatedFunction function;
    private TabulatedFunction resultFunction;

    private final JTable functionTable;
    private final JTable resultFunctionTable;

    private final DefaultTableModel firstTableModel;
    private final DefaultTableModel resultTableModel;
    ChooseСreateFactory settings;
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

        functionTable = createTable(firstTableModel, true);
        resultFunctionTable = createTable(resultTableModel, false);

        JPanel firstFunctionPanel = createFunctionPanel("Функция", functionTable,
                _ -> createFunction(), _ -> loadFunction(), _ -> saveFunction(1), _ -> DeleteValueInTB(firstTableModel, function), _ -> InsertValueInTB(firstTableModel, function));
        JPanel resultFunctionPanel = createResultPanel();

        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(1, 1));

        JButton differentiateButton = createStyledButton("Дифференцировать");
        differentiateButton.addActionListener(_ -> performOperation());
        operationPanel.add(differentiateButton);

        JPanel functionsPanel = new JPanel(new GridLayout(1, 2));
        functionsPanel.add(firstFunctionPanel);
        functionsPanel.add(resultFunctionPanel);

        add(functionsPanel, BorderLayout.CENTER);
        add(operationPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel createFunctionPanel(String title, JTable table, ActionListener createListener, ActionListener loadListener, ActionListener saveListener, ActionListener deleteListener, ActionListener insertListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(createStyledButton("Создать", createListener), gbc);

        gbc.gridx = 1;
        buttonPanel.add(createStyledButton("Загрузить", loadListener), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(createStyledButton("Удалить", deleteListener), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(createStyledButton("Сохранить", saveListener), gbc);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Pointer при наведении
        return button;
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = createStyledButton(text);
        button.addActionListener(listener);
        return button;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(null, "Результат", 0, 0));

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
                        function.setPointY(row, newValue);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(table, "Введите корректное числовое значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        table.setRowHeight(25);
        JTableHeader header = table.getTableHeader();
        table.setDefaultEditor(Object.class, new CellEditor());
        return table;
    }

    private void performOperation() {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция должна быть создана или загружена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            resultFunction = (TabulatedFunction) operationService.derive((function.TabulatedFunction) function);
            updateTableWithFunction(resultTableModel, resultFunction);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при выполнении операции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createFunction() {
        if (settings == null || !settings.isShowing()) {
            settings = new ChooseСreateFactory(owner, new TabulatedFunctionOperationService(operationService.getFactory()));
            settings.setVisible(true);
        }
        TabulatedFunction createdFunction = (TabulatedFunction) settings.getTabulatedFunction();
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
            String fileName = file.getName().toLowerCase();
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
                if (fileName.endsWith(".json") || fileName.endsWith(".xml")) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream))) {
                        this.function = (TabulatedFunction) FunctionsIO.readTabulatedFunction(reader, operationService.getFactory());
                    }
                } else if (fileName.endsWith(".bin")) {
                    this.function = (TabulatedFunction) FunctionsIO.deserialize(bufferedInputStream);
                } else {
                    throw new IOException("Неподдерживаемый формат файла");
                }
                updateTableWithFunction(firstTableModel, function);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void saveFunction(int operand) {
        JFileChooser fileChooser = new JFileChooser();

        javax.swing.filechooser.FileNameExtensionFilter allFormatsFilter =
                new javax.swing.filechooser.FileNameExtensionFilter("Все файлы", "json", "xml", "bin");
        fileChooser.setFileFilter(allFormatsFilter);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON файлы", "json"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML файлы", "xml"));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Бинарные файлы", "bin"));

        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName().toLowerCase();

            String selectedExtension = "";
            javax.swing.filechooser.FileFilter selectedFilter = fileChooser.getFileFilter();
            if (selectedFilter.getDescription().contains("JSON")) {
                selectedExtension = ".json";
            } else if (selectedFilter.getDescription().contains("XML")) {
                selectedExtension = ".xml";
            } else if (selectedFilter.getDescription().contains("Бинарные")) {
                selectedExtension = ".bin";
            }

            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                TabulatedFunction function = (operand == 1) ? this.function : resultFunction;
                if (file.getName().endsWith(".json") || file.getName().endsWith(".xml")) {
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(bufferedOutputStream))) {
                        FunctionsIO.writeTabulatedFunction(writer, (function.TabulatedFunction) function);
                    }
                } else if (file.getName().endsWith(".bin")) {
                    FunctionsIO.serialize(bufferedOutputStream, (function.TabulatedFunction) function);
                } else {
                    throw new IOException("Неподдерживаемый формат файла");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка сохранения функции: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void InsertValueInTB(DefaultTableModel tableModel, TabulatedFunction function) {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField XField = new JTextField(10);
        JTextField YField = new JTextField(10);

        ((AbstractDocument) XField.getDocument()).setDocumentFilter(new DoubleNumeric());
        ((AbstractDocument) YField.getDocument()).setDocumentFilter(new DoubleNumeric());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Введите значение X:"));
        panel.add(XField);
        panel.add(new JLabel("Введите значение Y:"));
        panel.add(YField);
        int res = JOptionPane.showConfirmDialog(null, panel, "Введите значение функции X и Y:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String X = XField.getText();
        String Y = YField.getText();
        if (res == JOptionPane.OK_OPTION) {
            try {
                double x = Double.parseDouble(X);
                double y = Double.parseDouble(Y);
                function.setPointX(res,x);
                function.setPointY(res,y);
                updateTableWithFunction(tableModel, function);
                JOptionPane.showMessageDialog(this, "Добавлена точка (" + x + ", " + y + ")");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Некорректный ввод", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void DeleteValueInTB(DefaultTableModel tableModel, TabulatedFunction function) {
        if (function == null) {
            JOptionPane.showMessageDialog(this, "Функция не создана", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField indexField = new JTextField(10);
        ((AbstractDocument) indexField.getDocument()).setDocumentFilter(new IntNumeric());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Введите номер строки для удаления:"));
        panel.add(indexField);
        int res = JOptionPane.showConfirmDialog(null, panel, "Удаление точки", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String index = indexField.getText();
        if (res == JOptionPane.OK_OPTION) {
            try {
                int i = Integer.parseInt(index);
                function.deletePoint(i - 1);
                updateTableWithFunction(tableModel, function);
                JOptionPane.showMessageDialog(this, "Точка удалена");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Строка не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateTableWithFunction(DefaultTableModel tableModel, TabulatedFunction function) {
        tableModel.setRowCount(0);
        for (int i = 0; i < function.getPointsCount(); i++) {
            tableModel.addRow(new Object[]{function.getPointX(i), function.getPointY(i)});
        }
    }
}