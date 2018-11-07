package lab.objects;

/**
 * Created by Alexandra on 07.11.2018.
 */
public class Lang {
    private String name;
    private int index;


    public Lang(int index, String name) {
        this.name = name;
        this.index = index;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return name;
    }
}
