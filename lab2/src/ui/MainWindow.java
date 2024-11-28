package ui;

import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private TabulatedFunctionOperationService factoryService;
    private ElectionFactories settingsWindow;

    public MainWindow() {
        setTitle("Главное окно");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        factoryService = new TabulatedFunctionOperationService();

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));  // Сетка 5 на 1 с отступами

        JButton settingsButton = new JButton("Открыть настройки");
        settingsButton.addActionListener(_ -> openSettingsWindow());

        JButton operationsButton = new JButton("Элементарные операции с функциями");
        operationsButton.addActionListener(_ -> openTabulatedFunctionOperationsWindow());

        JButton differentialOperation = new JButton("Операция дифференцирования над функцией");
        differentialOperation.addActionListener(_ -> TabulatedFunctionDifferentialOperationsWindow());

        buttonPanel.add(settingsButton);
        buttonPanel.add(operationsButton);
        buttonPanel.add(differentialOperation);


        add(buttonPanel, BorderLayout.CENTER);

        setTitle("Главное меню");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Завершить выполнение программы при закрытии главного окна
            }
        });
        setVisible(true);
    }

    private JButton createRoundedButton(String text, Font font, Color background, Color foreground, Cursor cursor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(cursor);
        button.setContentAreaFilled(false);
        return button;
    }

    private void openSettingsWindow() {
        if (settingsWindow == null || !settingsWindow.isShowing()) {
            settingsWindow = new ElectionFactories(this, factoryService);
            settingsWindow.setVisible(true);
        }
    }

    private void openTabulatedFunctionOperationsWindow() {
        new OperationsWindow(this, factoryService);
    }

    private void TabulatedFunctionDifferentialOperationsWindow() {
        new DifferentialOperations(this, new TabulatedDifferentialOperator(factoryService.factoryGetter()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}