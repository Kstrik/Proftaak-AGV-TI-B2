package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.GridSelection.GridSelectionComponent;

import java.beans.EventHandler;

public class GUI extends Application
{
    public final int HEIGHT = 480;//480//900
    public final int WIDTH = 720;//720//1600
    Bluetooth bluetooth = new Bluetooth("COM5");


    @Override
    public void start(Stage primaryStage) throws Exception
    {
       Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//        bluetooth.initialize();
//
//        //Mode select
//        ComboBox comboBox = new ComboBox();
//        comboBox.getItems().addAll("Manual", "Follow Line", "Grid Route");
//        comboBox.getSelectionModel().select(0);
//
//
//        //Buttons
//        Button button = new Button("TestBlueTooth");
//        button.setOnAction(event -> {
//            System.out.println("Button Pressed");
//            bluetooth.writeString("12345");
//        });
//
//        HBox hbox = new HBox(button);
//        VBox vBox = new VBox(hbox, comboBox);
//
//
//
//        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
//                    if (key.getCode() == KeyCode.W) {
//                        bluetooth.writeString("W");
//                        System.out.println("w");
//                    }
//                    if (key.getCode() == KeyCode.A) {
//                        bluetooth.writeString("A");
//                        System.out.println("a");
//                    }
//                    if (key.getCode() == KeyCode.S) {
//                        bluetooth.writeString("S");
//                        System.out.println("s");
//                    }
//                    if (key.getCode() == KeyCode.D) {
//                        bluetooth.writeString("D");
//                        System.out.println("d");
//                    }
//                    if (key.getCode() == KeyCode.ESCAPE) {
//                        System.out.println("See you later!");
//                        primaryStage.close();
//                    }
//                }
//        );

//        GridSelectionComponent gridSelectionComponent = new GridSelectionComponent(WIDTH, HEIGHT);
//
//        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(gridSelectionComponent);

        //Scene
        primaryStage.setTitle("BoeBot GUI");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        //primaryStage.setScene(new Scene(borderPane, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.show();
    }
}



