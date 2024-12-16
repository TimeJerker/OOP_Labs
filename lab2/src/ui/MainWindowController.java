package ui;

import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindowController extends JFrame {
    private final int WIDTH_WINDOW = 600;
    private final int HEIGHT_WINDOW = 400;
    private TabulatedFunctionOperationService factoryService;
    private ChooseСreateFactory settingsWindow;

    public MainWindowController() {
        setTitle("Главное окно");
        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(237, 199, 183));

        factoryService = new TabulatedFunctionOperationService();
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(200, 0, new Color(237, 199, 183), 0, getHeight(), new Color(172, 59, 97));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));  // Сетка 5 на 1 с отступами

        JButton settingsButton = new RoundedButton("Открыть настройки", new Color(172, 59, 97));
        settingsButton.addActionListener(_ -> openSettingsWindow());

        JButton operationsButton = new RoundedButton("Элементарные операции с функциями", new Color(172, 59, 97));
        operationsButton.addActionListener(_ -> openOperationsWindow());

        JButton differentialOperation = new RoundedButton("Операция дифференцирования над функцией", new Color(172, 59, 97));
        differentialOperation.addActionListener(_ -> openDifferentialOperations());

        buttonPanel.add(settingsButton);
        buttonPanel.add(operationsButton);
        buttonPanel.add(differentialOperation);

        add(buttonPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Кастомная кнопка с округлыми углами
    public static class RoundedButton extends JButton {
        public RoundedButton(String label, Color textColor) {
            super(label);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(textColor);
            setBackground(new Color(238, 226, 220));
            setFont(new Font("MerriWeather", Font.PLAIN, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
        }
    }


    private void openSettingsWindow() {
        if (settingsWindow == null || !settingsWindow.isShowing()) {
            settingsWindow = new ChooseСreateFactory(this, factoryService);
            settingsWindow.setVisible(true);
        }
    }

    private void openOperationsWindow() {
        new OperationsWindow(this, factoryService);
    }

    private void openDifferentialOperations() {
        new DifferentialOperations(this, new TabulatedDifferentialOperator(factoryService.factoryGetter()));
    }

}