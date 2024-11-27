package ui;

import javax.swing.*;

public class MainTable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TableController window = new TableController();
            window.setVisible(true);
        });
    }
}
