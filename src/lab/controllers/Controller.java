package lab.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab.interfaces.impl.CollectionNotepad;
import lab.objects.Note;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;


public class Controller {
    private CollectionNotepad notepadImpl = new CollectionNotepad();
    private CollectionNotepad notepadImpl2 = new CollectionNotepad();

    private Stage mainStage;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnDelete;
    @FXML
    private CustomTextField txtSearch;
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
    ObservableList<Note> backupList;


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void initialize() {
        columnName.setCellValueFactory(new PropertyValueFactory<Note, String>("name")); //PropertyValueFactory<Note, String>("name"));
        columnGroup.setCellValueFactory(new PropertyValueFactory<Note, String>("group"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, String>("time"));
        columnText.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));
        setupClearButtonField(txtSearch);
        initListeners();

        fillData();
        table.setItems(notepadImpl.getNoteList());

        initLoader();

    }

    private void fillData() {
        notepadImpl.fillTestData();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(notepadImpl.getNoteList());
    }

    private void setupClearButtonField(CustomTextField customTextField){
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListeners(){
        notepadImpl.getNoteList().addListener((ListChangeListener<Note>) c -> updateCountLabel());

        table.setOnMouseClicked(event -> {
            if(event.getClickCount()==2){
                editDialogController.setNote((Note)table.getSelectionModel().getSelectedItem());
                showDialog();
            }
        });
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

        switch (clickedButton.getId()) {
            case "btnAdd":
                editDialogController.setNote(new Note());
                showDialog();
                notepadImpl.add(editDialogController.getNote());
                /*notepadImpl2.fillTestData();
                table.setItems(notepadImpl2.getNoteList());*/
                break;
            case "btnChange":
                editDialogController.setNote((Note) table.getSelectionModel().getSelectedItem());
                showDialog();
                break;
            case "btnDelete":
                notepadImpl.delete((Note) table.getSelectionModel().getSelectedItem());
                break;
        }

    }

    private void showDialog(){
//addpassw.setWebsite(selectedWebsite);

        if (editDialogStage==null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinWidth(520);
            editDialogStage.setMinHeight(250);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }

      editDialogStage.showAndWait(); // для ожидания закрытия окна

        //editDialogStage.show();
    }

    public void actionSearch(ActionEvent actionEvent){
        notepadImpl.getNoteList().clear();

        for (Note note: backupList) {
            if(note.getName().toLowerCase().contains(txtSearch.getText().toLowerCase())||
                    note.getText().toLowerCase().contains(txtSearch.getText().toLowerCase())||
                    note.getTime().toLowerCase().contains(txtSearch.getText().toLowerCase())){
                notepadImpl.getNoteList().add(note);
            }
        }
    }

}
