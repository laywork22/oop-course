/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystudentlistah;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author laywork
 */
public class MyStudentListViewController implements Initializable {
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField codeField;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableColumn<Studente, String> nameClm;
    @FXML
    private MenuItem saveButton;
    @FXML
    private TableView<Studente> studentTable;
    @FXML
    private TableColumn<Studente, String> surnameClm;
    @FXML
    private TableColumn<Studente, String> codeClm;
    @FXML
    private MenuBar menu;
    
    private ObservableList<Studente> studentList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        studentList = FXCollections.observableArrayList();
        
        studentTable.setItems(studentList);
        
        //ogni volta che deve fare il rendering delle proprietà
        // se si modifica non viene mostrato sulla tabella
        //name non cambia perchè non è una Property
        nameClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getName()));
        
        //nameClm.setCellValueFactory(r -> r.getValue().nameProperty() );

        
        //cerca le proprietà con quel nome (metodi get"value" ..
        surnameClm.setCellValueFactory(new PropertyValueFactory("surname"));
        
        codeClm.setCellValueFactory(new PropertyValueFactory("code"));
        
        nameClm.setCellFactory(TextFieldTableCell.forTableColumn());
    }    

    @FXML
    private void addStudent(ActionEvent event) {
        //disabilitare il pulsante aggiunti se almeno un campo sia vuoto
        studentList.add(new Studente(nameField.getText(), surnameField.getText(), codeField.getText()));
        
    }

    @FXML
    private void removeStudent(ActionEvent event) {
        Studente s = studentTable.getSelectionModel().getSelectedItem();
        studentList.remove(s);
    }

    @FXML
    private void openFile(ActionEvent event) {
    }

    @FXML
    private void saveFile(ActionEvent event) {
    }

    @FXML
    private void exit(ActionEvent event) {
    }

    @FXML
    private void updateName(TableColumn.CellEditEvent<Studente, String> event) {
        Studente s = event.getRowValue();
        
        s.setName(event.getNewValue());
    }

    @FXML
    private void updateSurname(TableColumn.CellEditEvent<Studente, String> event) {
    }

    @FXML
    private void updateCode(TableColumn.CellEditEvent<Studente, String> event) {
    }
    
}
