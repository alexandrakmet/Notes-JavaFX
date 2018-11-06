package lab.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lab.interfaces.impl.CollectionNotepad;
import lab.objects.Note;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class Controller {
    private CollectionNotepad notepadImpl = new CollectionNotepad();


    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView table;
    @FXML
    private TableColumn<Note, String> columnName;
    @FXML
    private TableColumn<Note, String> columnGroup;
    @FXML
    private TableColumn<Note, String> columnDate;
    @FXML
    private TableColumn<Note, String> columnText;

    @FXML
    private Label labelCount;


    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;
    private Stage editDialogStage;

    @FXML
    private void initialize() {
        columnName.setCellValueFactory(new PropertyValueFactory<Note, String>("name")); //PropertyValueFactory<Note, String>("name"));
        columnGroup.setCellValueFactory(new PropertyValueFactory<Note, String>("group"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, String>("time"));
        columnText.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));

        notepadImpl.getNoteList().addListener((ListChangeListener<Note>) c -> updateCountLabel());

        notepadImpl.fillTestData();

        table.setItems(notepadImpl.getNoteList());

        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    private void updateCountLabel() {
        labelCount.setText("Количество записей: " + notepadImpl.getNoteList().size());
    }


    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;
        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        switch (clickedButton.getId()) {
            case "btnAdd":
                editDialogController.setNote(new Note());
                showDialog(parentWindow);
                notepadImpl.add(editDialogController.getNote());
                break;
            case "btnChange":
                editDialogController.setNote((Note) table.getSelectionModel().getSelectedItem());
                showDialog(parentWindow);
                break;
            case "btnDelete":
                notepadImpl.delete((Note) table.getSelectionModel().getSelectedItem());
                break;
        }

        System.out.println(selectedNote.toString());
    }

    private void showDialog(Window parentWindow){
        /*try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/edit.fxml"));
            stage.setTitle("Редактирование заметки");
            stage.setMinWidth(520);
            stage.setMinHeight(250);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        if (editDialogStage==null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinWidth(520);
            editDialogStage.setMinHeight(250);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(parentWindow);
        }

      editDialogStage.showAndWait(); // для ожидания закрытия окна

        //editDialogStage.show();
    }
}
