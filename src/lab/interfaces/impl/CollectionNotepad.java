package lab.interfaces.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab.interfaces.Notepad;
import lab.objects.Note;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandra on 05.11.2018.
 */
public class CollectionNotepad implements Notepad {

    private ObservableList<Note> noteList = FXCollections.observableArrayList();
    private ObservableList<Note> backupList = FXCollections.observableArrayList();
    private ObservableList<Note> toDoList = FXCollections.observableArrayList();
    private ObservableList<Note> workList = FXCollections.observableArrayList();
    private ObservableList<Note> persList = FXCollections.observableArrayList();
    private ObservableList<Note> archiveList = FXCollections.observableArrayList();


    public void search(String groupName) {
        getNoteList().clear();
        if (groupName.equals("ToDo")) getNoteList().addAll(getToDoList());
        if (groupName.equals("Работа")) getNoteList().addAll(getWorkList());
        if (groupName.equals("Личное")) getNoteList().addAll(getPersList());
        if (groupName.equals("Общие")) getNoteList().addAll(getBackupList());
        if (groupName.equals("Архив")) getNoteList().addAll(getArchiveList());

    }

    @Override
    public void add(Note note) {
        getNoteList().add(note);
        getBackupList().add(note);
        if (note.getGroup().equals("ToDo")) getToDoList().add(note);
        if (note.getGroup().equals("Работа")) getWorkList().add(note);
        if (note.getGroup().equals("Личное")) getPersList().add(note);
    }

    @Override
    public void update(Note note) {

    }

    @Override
    public void remove(Note note) {
        getNoteList().remove(note);
        getBackupList().remove(note);
        if (note.getGroup().equals("ToDo")) getToDoList().remove(note);
        if (note.getGroup().equals("Работа")) getWorkList().remove(note);
        if (note.getGroup().equals("Личное")) getPersList().remove(note);
        note.setGroup("Архив");
        getArchiveList().add(note);
    }

    @Override
    public void delete(Note note) {
        if (getNoteList().contains(note)) {
            getNoteList().remove(note);
            getBackupList().remove(note);
        }
        if (note.getGroup().equals("ToDo")) getToDoList().remove(note);
        if (note.getGroup().equals("Работа")) getWorkList().remove(note);
        if (note.getGroup().equals("Личное")) getPersList().remove(note);
        if (note.getGroup().equals("Архив")) getArchiveList().remove(note);

    }

    public ObservableList<Note> getNoteList() {
        return noteList;
    }

    public void print() {
        int number = 0;
        System.out.println();
        for (Note note : getNoteList()) {
            number++;
            System.out.println(number + ". " + note.getName() + ":  " + note.getText() + "  " + note.getTime());
        }
    }

    public void fillTestData() {
       /* this.add(new Note("43first", "Работа", "fffffffffffffff"));
        this.add(new Note("11second", "Личное", "ssssssssssssss"));
        this.add(new Note("78third", "Работа", "ysu,tdc.ij"));
        this.add(new Note("15", "Личное", "aertyui"));
        this.add(new Note("16", "Личное", "rxdtufguj"));
        this.add(new Note("78", "Личное", "fyyyyyyyyf"));*/

        List<String> temp = null;
        File file = new File("C:\\Users\\Alexandra\\IdeaProjects\\labWork2\\resources\\заметочки.txt");
        try {
            temp = FileUtils.readLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String note:temp) {
            String temps[] = note.split("'");
            add(new Note(temps[1], temps[3], temps[5], temps[7] ));
        }
    }

    public ObservableList<Note> getBackupList() {
        return backupList;
    }

    public ObservableList<Note> getToDoList() {
        return toDoList;
    }

    public ObservableList<Note> getWorkList() {
        return workList;
    }

    public ObservableList<Note> getPersList() {
        return persList;
    }

    public ObservableList<Note> getArchiveList() {
        return archiveList;
    }
}
