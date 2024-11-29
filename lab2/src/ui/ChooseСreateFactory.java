package ui;

import javax.swing.*;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedFunctionOperationService;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChooseСreateFactory extends JDialog {
    public ChooseСreateFactory(JFrame owner, TabulatedFunctionOperationService factoryService) {
        super(owner, "Настройки", true); // Модальное окно
        setSize(400, 200);
        setLocationRelativeTo(null);

        JRadioButton arrayFactoryButton = new JRadioButton("Фабрика на основе массива", factoryService.factoryGetter() instanceof ArrayTabulatedFunctionFactory);
        JRadioButton listFactoryButton = new JRadioButton("Фабрика на основе связного списка", factoryService.factoryGetter() instanceof LinkedListTabulatedFunctionFactory);

        ButtonGroup group = new ButtonGroup();
        group.add(arrayFactoryButton);
        group.add(listFactoryButton);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(_ -> {
            if (arrayFactoryButton.isSelected()) {
                factoryService.factorySetter(new ArrayTabulatedFunctionFactory());
            } else if (listFactoryButton.isSelected()) {
                factoryService.factorySetter(new LinkedListTabulatedFunctionFactory());
            }
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(arrayFactoryButton);
        panel.add(listFactoryButton);
        panel.add(saveButton);
        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
