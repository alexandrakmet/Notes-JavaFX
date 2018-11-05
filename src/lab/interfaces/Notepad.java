package lab.interfaces;

import lab.objects.Note;

/**
 * Created by Alexandra on 05.11.2018.
 */
public interface Notepad {

    void add(Note note);

    void update(Note note);

    void remove(Note note);

    void delete(Note note);
}
