package lab.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.controllers.Controller;
import lab.interfaces.impl.CollectionNotepad;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //addpassw.setWebsite(selectedWebsite);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../fxml/sample.fxml"));
        //        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));

        Parent fxmlMain = fxmlLoader.load();
        Controller mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("Notes");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(new Scene(fxmlMain, 850, 500));
        primaryStage.show();

        //testData();
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
