package lab.controllers;

import java.lang.Object.*;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab.interfaces.impl.CollectionNotepad;
import lab.objects.Lang;
import lab.objects.Note;
import javafx.scene.control.cell.PropertyValueFactory;
import lab.utils.DialogManager;
import lab.utils.GroupsManager;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;


public class Controller {
    private CollectionNotepad notepadImpl = new CollectionNotepad();

    private Stage mainStage;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChange;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnArchive;
    @FXML
    private Button saveNote;
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
    private ComboBox comboGroups;
    @FXML
    private Label labelCount;


    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;
    private Stage editDialogStage;
    // ObservableList<Note> backupList;


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

        initLoader();

    }

    private void fillData() {
        fillTable();
        fillLangComboBox();
    }

    private void fillTable() {
        notepadImpl.fillTestData();
        table.setItems(notepadImpl.getNoteList());
    }

    public void fillLangComboBox() {
        Lang langGen = new Lang(0, "Общие");
        Lang langToDo = new Lang(1, "ToDo");
        Lang langWork = new Lang(2, "Работа");
        Lang langPers = new Lang(3, "Личное");
        Lang langArchive = new Lang(4, "Архив");


        comboGroups.getItems().add(langGen);
        comboGroups.getItems().add(langToDo);
        comboGroups.getItems().add(langWork);
        comboGroups.getItems().add(langPers);
        comboGroups.getItems().add(langArchive);

        if (GroupsManager.getCurrentLangs() == null) {// по-умолчанию показывать выбранный русский язык (можно текущие настройки языка сохранять в файл)
            comboGroups.getSelectionModel().select(0);
        } else {
            comboGroups.getSelectionModel().select(GroupsManager.getCurrentLangs().getIndex());
        }
    }

    private void setupClearButtonField(CustomTextField customTextField) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        notepadImpl.getNoteList().addListener((ListChangeListener<Note>) c -> updateCountLabel());

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editDialogController.setNote((Note) table.getSelectionModel().getSelectedItem());
                showDialog();
            }
        });

        comboGroups.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lang selectedLang = (Lang) comboGroups.getSelectionModel().getSelectedItem();
                GroupsManager.setCurrentLangs(selectedLang);
                notepadImpl.search(GroupsManager.getCurrentLangs().getName());
                if (selectedLang.getName().equals("Архив")) {
                    btnAdd.setVisible(false);
                    btnArchive.setVisible(false);
                } else {
                    btnAdd.setVisible(true);
                    btnArchive.setVisible(true);
                }

            }
        });

       saveNote.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               FileChooser fileChooser = new FileChooser();
               fileChooser.setTitle("Save Note");
               File file = fileChooser.showSaveDialog(new Stage());
               if (file != null) {
                   try {
                       FileUtils.writeLines(file,notepadImpl.getNoteList());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

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
        Note selectedNote = (Note) table.getSelectionModel().getSelectedItem();
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
                if (!noteIsSelected(selectedNote)) {
                    return;
                }
                editDialogController.setNote((Note) table.getSelectionModel().getSelectedItem());
                showDialog();
                break;
            case "btnArchive":
                if (!noteIsSelected(selectedNote)) {
                    return;
                }
                notepadImpl.remove((Note) table.getSelectionModel().getSelectedItem());
                break;
            case "btnDelete":
                if (!noteIsSelected(selectedNote)) {
                    return;
                }
                notepadImpl.delete((Note) table.getSelectionModel().getSelectedItem());
                break;
        }

    }

    private boolean noteIsSelected(Note selectedNote) {
        if (selectedNote == null) {
            DialogManager.showInfDialog("Ошибка", "Выберите запись");
            return false;
        }
        return true;
    }

    private void showDialog() {
//addpassw.setWebsite(selectedWebsite);
        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinWidth(520);
            editDialogStage.setMinHeight(250);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
            editDialogController.fillLangComboBox();

        }

        editDialogStage.showAndWait(); // для ожидания закрытия окна

        //editDialogStage.show();
    }

    public void actionSearch(ActionEvent actionEvent) {
        System.out.println(notepadImpl.getBackupList().toString());
        notepadImpl.getNoteList().clear();

        for (Note note : notepadImpl.getBackupList()) {
            if (note.getName().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    note.getText().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    note.getTime().toLowerCase().contains(txtSearch.getText().toLowerCase())) {
                notepadImpl.getNoteList().add(note);
            }
        }
    }

}
