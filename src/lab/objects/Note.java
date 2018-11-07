package lab.objects;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Alexandra on 05.11.2018.
 */
public class Note {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty group = new SimpleStringProperty("");
    private SimpleStringProperty text = new SimpleStringProperty("");
    private SimpleStringProperty time = new SimpleStringProperty("");

    public Note(){}

    public Note(String name, String group,String time, String text) {
        this.name = new SimpleStringProperty(name);
        this.text = new SimpleStringProperty(text);
        this.group = new SimpleStringProperty(group);
        this.time = new SimpleStringProperty(time);
    }

    public Note(String name, String group, String text) {
        this.name = new SimpleStringProperty(name);
        this.text = new SimpleStringProperty(text);
        this.group = new SimpleStringProperty(group);
        /**DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

         String formatDateTime = now.format(formatter);*/

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.time = new SimpleStringProperty(now.format(formatter));
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGroup() {
        return group.get();
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setTime() {
       LocalDateTime now = LocalDateTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       this.time = new SimpleStringProperty(now.format(formatter));

    }

    public SimpleStringProperty groupProperty() {
        return group;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty textProperty() {
        return text;
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    @Override
    public String toString() {
        return "Название: '" + getName() +
                "',  группа: '" + getGroup() +
                "',  дата создания: '" + getTime() +
                "',  содержание: '" + getText()+"'";
    }
}
