package lab.interfaces;

import lab.objects.Note;

public interface Notepad {

    void add(Note note);

    void update(Note note);

    void remove(Note note);

    void delete(Note note);
}
