package lab.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab.objects.Note;

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

    private Note note;



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
        note.setName(txtName.getText());
        note.setText(txtNote.getText());
        actionClose(actionEvent);
    }
}

