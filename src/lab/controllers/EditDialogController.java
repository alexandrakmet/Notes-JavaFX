package lab.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab.objects.Lang;
import lab.objects.Note;
import lab.utils.DialogManager;
import lab.utils.GroupsManager;

import java.time.LocalDateTime;


/**
 * Created by Alexandra on 04.11.2018.
 */
public class EditDialogController {
    @FXML
    private Button editOk;

    @FXML
    private Button editCancel;

    @FXML
    private TextArea txtNote;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox comboGroup;

    private Note note;


    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
        txtName.setText(note.getName());
        txtNote.setText(note.getText());

    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        this.note.setTime();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if(!checkValues()) {return;}
        comboGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lang selectedLang = (Lang) comboGroup.getSelectionModel().getSelectedItem();
                GroupsManager.setCurrentLang(selectedLang);

            }
        });
        Lang selectedLang = (Lang) comboGroup.getSelectionModel().getSelectedItem();
        note.setName(txtName.getText());
        note.setText(txtNote.getText());
        note.setGroup(selectedLang.getName());
        actionClose(actionEvent);
    }

    private boolean checkValues(){
        if(txtName.getText().length()==0||txtNote.getText().length()==0|| (Lang)comboGroup.getSelectionModel().getSelectedItem()==null){
            DialogManager.showInfDialog("Ошибка", "Заполните поля");
            return false;
        }
        return true;
    }

    public void fillLangComboBox() {
        Lang langGen = new Lang(0, "Общие");
        Lang langToDo = new Lang(0, "ToDo");
        Lang langWork = new Lang(0, "Работа");
        Lang langPers = new Lang(0, "Личное");

        comboGroup.getItems().add(langGen);
        comboGroup.getItems().add(langToDo);
        comboGroup.getItems().add(langWork);
        comboGroup.getItems().add(langPers);

        if (GroupsManager.getCurrentLang() == null){// по-умолчанию показывать выбранный русский язык (можно текущие настройки языка сохранять в файл)
            comboGroup.getSelectionModel().select(0);
        }else{
            comboGroup.getSelectionModel().select(GroupsManager.getCurrentLang().getIndex());
        }
    }
}

