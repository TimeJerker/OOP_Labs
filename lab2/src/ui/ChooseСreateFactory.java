package ui;


import function.TabulatedFunction;
import operations.TabulatedFunctionOperationService;

import javax.swing.*;
import java.awt.*;

public class ChooseСreateFactory extends JDialog {
    private TabulatedFunction function;
    private final TabulatedFunctionOperationService factoryService;
    private JFrame owner;
    public ChooseСreateFactory(JFrame owner, TabulatedFunctionOperationService factoryService) {
        super(owner, "Создание табулированной функции", true);
        this.owner = owner;
        this.factoryService = factoryService;
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton arrayFactoryButton = new JButton("Создать функцию по массивам");
        arrayFactoryButton.addActionListener(_ -> openTableController());

        JButton listFactoryButton = new JButton("Создать функцию по математической функции");
        listFactoryButton.addActionListener(_ -> openMathFunctionController());
        JPanel panel = new JPanel();
        panel.add(arrayFactoryButton);
        panel.add(listFactoryButton);
        add(panel, BorderLayout.CENTER);
    }

    private void openTableController() {
        TableController arraysWindow = new TableController(owner, factoryService.factoryGetter());
        arraysWindow.setVisible(true);
        function = arraysWindow.getTabulatedFunction();
    }

    private void openMathFunctionController() {
        MathFunctionController mathWindow = new MathFunctionController(owner, factoryService.factoryGetter());
        mathWindow.setVisible(true);
        function = mathWindow.getTabulatedFunction();
        dispose();
    }

    public function.TabulatedFunction getTabulatedFunction() {
        return function;
    }
}
