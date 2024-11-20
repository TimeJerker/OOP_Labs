package ui;

import java.net.URL;
import java.util.ResourceBundle;

import function.Point;
import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;
import function.factory.TabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;


public class table {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createButton;

    @FXML
    private TextField countField;

    @FXML
    private TableView<Point> dataTableView;

    @FXML
    private TableColumn<Point, Double> xColumn;

    @FXML
    private TableColumn<Point, Double> yColumn;

    private ObservableList<Point> data;
    private boolean tableCreated = false;
    private TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();


    @FXML
    void initialize() {
        data = FXCollections.observableArrayList();
        dataTableView.setItems(data);


        xColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        yColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        dataTableView.setEditable(true);

        createButton.setOnAction(e -> createTable());
    }


    private void createTable() {
        try {
            int numPoints = Integer.parseInt(countField.getText());
            if (numPoints < 2) {
                throw new IllegalArgumentException("Количество точек должно быть больше или равно 2.");
            }
            data.clear();
            for (int i = 0; i < numPoints; i++) {
                data.add(new Point(0.0, 0.0));
            }
            tableCreated = true;
            createButton.setText("Создать функцию");
        } catch (NumberFormatException e) {
            ExceptionHandler.handle(new NumberFormatException("Некорректный формат ввода. Введите целое число."));
        } catch (IllegalArgumentException e) {
            ExceptionHandler.handle(new IllegalArgumentException());
        }
    }
}

