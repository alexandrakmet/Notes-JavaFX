package lab.interfaces.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab.interfaces.Notepad;
import lab.objects.Note;

import java.util.ArrayList;

/**
 * Created by Alexandra on 05.11.2018.
 */
public class CollectionNotepad implements Notepad {

    private ObservableList<Note> noteList = FXCollections.observableArrayList();

    @Override
    public void add(Note note) {
        noteList.add(note);
    }

    @Override
    public void update(Note note) {

    }

    @Override
    public void remove(Note note) {

    }

    @Override
    public void delete(Note note) {
        noteList.remove(note);
    }

    public ObservableList<Note> getNoteList() {
        return noteList;
    }

    public void print() {
        int number = 0;
        System.out.println();
        for (Note note : noteList) {
            number++;
            System.out.println(number + ". " + note.getName() + ":  " + note.getText()+"  "+note.getTime());
        }
    }

    public void fillTestData() {
        noteList.add(new Note("43first", "fffffffffffffff"));
        noteList.add(new Note("11second", "ssssssssssssss"));
        noteList.add(new Note("78third", "ysu,tdc.ij"));
        noteList.add(new Note("15", "aertyui"));
        noteList.add(new Note("16", "rxdtufguj"));
        noteList.add(new Note("78", "fyyyyyyyyf"));
    }
}
