package ui.InputData;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class CellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField;

    public CellEditor() {
        textField = new JTextField();
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleNumeric());
    }

    @Override
    public Object getCellEditorValue() {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText(value != null ? value.toString() : "");
        return textField;
    }
}
