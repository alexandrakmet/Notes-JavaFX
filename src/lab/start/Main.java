package lab.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.interfaces.impl.CollectionNotepad;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/sample.fxml"));
        primaryStage.setTitle("Notes");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(new Scene(root, 700, 550));
        primaryStage.show();

        testData();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void testData(){
        CollectionNotepad notepad = new CollectionNotepad();
        notepad.fillTestData();
        notepad.print();
    }
}
