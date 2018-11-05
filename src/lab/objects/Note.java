package lab.objects;

import java.time.LocalDateTime;

/**
 * Created by Alexandra on 05.11.2018.
 */
public class Note {
    private String name;
    private String group;
    private String text;
    private LocalDateTime time;

    public Note(String name, String text){
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
