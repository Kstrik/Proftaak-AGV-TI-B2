package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import sample.GridSelection.*;

//import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable, GridEventHandler
{
//    @FXML
//    Button button_W;
//    @FXML
//    Button button_A;
//    @FXML
//    Button button_S;
//    @FXML
//    Button button_D;
//    @FXML
//    Button button_Stop;
//    @FXML
//    Button button_Break;


    public int testInt = 190;

    public boolean btConnected = false;
    public int _ultrasonicDistance = 0;
    public int _locationX = 0;
    public int _locationY = 0;
    Image selected = new Image("recources/crossroadsEnd.png");
    Image notSelected = new Image("recources/crossroads.jpg");

    class Coordinate
    {
        public Coordinate(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public int x = 0;
        public int y = 0;
    }

    ArrayList<Coordinate> coordinates = new ArrayList();



    @FXML
    VBox vboxLeft;

    //buttons
    @FXML
    RadioButton mode_manual;
    @FXML
    RadioButton mode_followline;
    @FXML
    RadioButton mode_grid;
    @FXML
    ToggleGroup mode;
    @FXML
    Button initializeBTbutton;

//    @FXML
//    Button updateXY;
//    @FXML
//    TextField setX;
//    @FXML
//    TextField setY;

    @FXML
    Button setGrid;
    @FXML
    TextField setWidth;
    @FXML
    TextField setHeight;
    @FXML
    Label message;

    //status and data
    @FXML
    Circle connection_Dot;
    @FXML
    Label locationX;
    @FXML
    Label locationY;
    @FXML
    Label ultrasonicDistance;

    @FXML
    BorderPane borderPane;


    Bluetooth btConnection = new Bluetooth("COM5");

    GridSelectionComponent gridSelectionComponent;


    public void sendCommand_W()
    {
        if (mode_manual.isSelected())
        {
            btConnection.writeString("w");
            System.out.println("Button pressed: [W]");
        }

    }

    public void sendCommand_A()
    {
        if (mode_manual.isSelected())
        {
            btConnection.writeString("a");
            System.out.println("Button pressed: [A]");
        }
    }

    public void sendCommand_S()
    {
        if (mode_manual.isSelected())
        {
            btConnection.writeString("s");
            System.out.println("Button pressed: [S]");
        }
    }

    public void sendCommand_D() {
        if (mode_manual.isSelected()) {
            btConnection.writeString("d");
            System.out.println("Button pressed: [D]");
        }
    }

    public void sendCommand_Break()
    {
        if (mode_manual.isSelected())
        {
            btConnection.writeString("c");
            System.out.println("Button pressed: [Break]");
        }
    }

    public void sendCommand_Stop()
    {
        if (mode_manual.isSelected())
        {
            btConnection.writeString("x");
            System.out.println("Button pressed: [Stop]");
        }
    }

    public void sendCommand_UpdateMode()
    {
        if (mode_manual.isSelected())
        {
            //btConnection.writeString("m");
        }
        else if (mode_grid.isSelected())
        {
            //btConnection.writeString("l");
        }
        else if (mode_followline.isSelected())
        {
            btConnection.writeString("l");
        }
        System.out.println("Button pressed: [Update Mode]");
    }


    void update()
    {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
//                    if (btConnected) {
//                        fetchData();
//                        updateGUIData();
//                    }

                    //fetchTestData();
                    //updateGUIData();
                });

            }
        }, 0, 1000);
    }


    public void tryBluetooth()
    {
        if (btConnection.initialize())
        {
            System.out.println("Bluetooth Initialized");
            btConnected = true;
            connection_Dot.setFill(Paint.valueOf("#00ff00"));
        }
        else
        {
            System.out.println("Bluetooth Connection Failed!!!");
            connection_Dot.setFill(Paint.valueOf("#ff0000"));
        }
    }

    public void fetchData() {
        btConnection.writeString("p");

        //read Ultrasonic distance
        _ultrasonicDistance = bytesToInt(btConnection.readBytes(8));

        //read LocationX;
        _locationX = bytesToInt(btConnection.readBytes(8));

        //read LocationY;
        _locationX = bytesToInt(btConnection.readBytes(8));
    }

    public void gridPressed(int x, int y, ImageView image) {
        System.out.println("Coordinates X:" + x + " Y:" + y);

        if(!coordinates.isEmpty()) {
            int foundIndex = -1;
            for (Coordinate c : coordinates) {
                if (c.x == x && c.y == y) {
                    foundIndex = coordinates.indexOf(c);
                    image.setImage(notSelected);
                }
            }
            if (foundIndex == -1) {
                image.setImage(selected);
                coordinates.add(new Coordinate(x, y));
            }else{
                coordinates.remove(foundIndex);
            }


        }else{
            image.setImage(selected);
            coordinates.add(new Coordinate(x, y));
        }

        System.out.println(coordinates.size());


    }

    public void createGrid()
    {
        System.out.println("called");
        try
        {
            if(!setWidth.getText().isEmpty() || !setHeight.getText().isEmpty())
            {
                ColorTheme colorTheme = new ColorTheme(Color.BLACK, Color.RED, Color.BLUE, Color.LIME, Color.BLACK, Color.RED);
                this.gridSelectionComponent.changeGrid(Integer.parseInt(setWidth.getText()), Integer.parseInt(setHeight.getText()), colorTheme);
                message.setText("");
            }
            else
            {
                message.setTextFill(Color.RED);
                message.setText("Missing axis!");
            }
        }
        catch (Exception e)
        {
            message.setTextFill(Color.RED);
            message.setText("Illegal input!");
        }
    }

    public void clearPath()
    {
        this.gridSelectionComponent.clearPath();
    }

//    public void updateCoordinates(){
//
//        String dataX = setX.getText();
//        String dataY = setY.getText();
//
//        try{
//            int intX = Integer.parseInt(dataX);
//            int intY = Integer.parseInt(dataY);
//
//            System.out.println("Coordinates X:" + intX + " Y:" + intY);
//
//            if(!coordinates.isEmpty()) {
//                int foundIndex = -1;
//                for (Coordinate c : coordinates) {
//                    if (c.x == intX && c.y == intY) {
//                        foundIndex = coordinates.indexOf(c);
//                    }
//                }
//                if (foundIndex == -1) {
//                    coordinates.add(new Coordinate(intX, intY));
//                }else{
//                    coordinates.remove(foundIndex);
//                }
//            }else{
//                coordinates.add(new Coordinate(intX, intY));
//            }
//
//            System.out.println(coordinates.size());
//
//        }catch(Exception e){
//            System.out.println("Invalid data");
//            setX.setText("Invalid");
//            setY.setText("data!");
//        }
//
//
//
//    }

    public void sendCoordinates(){

        ArrayList<Node> path = this.gridSelectionComponent.getPath();

        if(!path.isEmpty())
        {
            btConnection.writeByte((byte)-2);
            for(Node node : path)
            {
                btConnection.writeByte((byte)node.getGridPosition().x);
                btConnection.writeByte((byte)(node.getGridPosition().y));
                System.out.println("X: " + node.getGridPosition().x + " Y: " + node.getGridPosition().y);
            }
            btConnection.writeByte((byte)-3);
            message.setTextFill(Color.LIME);
            message.setText("Coordinates send!");
        }
        else
        {
            //System.out.println("No coordinates selected");
            message.setTextFill(Color.RED);
            message.setText("No coordinates selected!");
        }
    }


    public void fetchTestData() {
        testInt++;

        _ultrasonicDistance = testInt;
        _locationX = testInt;
        _locationY = testInt;
    }


    public void updateGUIData() {
        if (_ultrasonicDistance <= 200) {
            ultrasonicDistance.setBackground((new Background(new BackgroundFill(Color.web("#ff0000"), CornerRadii.EMPTY, Insets.EMPTY))));
            ultrasonicDistance.setTextFill(Paint.valueOf("#ffffff"));
        }
        if (_ultrasonicDistance > 200) {
            //something
        }
        ultrasonicDistance.setText(Integer.toString(_ultrasonicDistance));

        locationX.setText(Integer.toString(_locationX));
        locationY.setText(Integer.toString(_locationY));
    }


    public int bytesToInt(byte[] data) {
        // TODO: 12/20/2018
        return 0;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tryBluetooth();
        update();

        vboxLeft.setBackground(new Background(new BackgroundFill(Color.web("#444444"), CornerRadii.EMPTY, Insets.EMPTY)));

        System.out.println(borderPane.getWidth());
        System.out.println(borderPane.getHeight());
        gridSelectionComponent = new GridSelectionComponent(540, 440, this);
        borderPane.setCenter(gridSelectionComponent);
    }

    @Override
    public void onNodeHover(Node node)
    {
        locationX.setText(Float.toString(node.getGridPosition().x));
        locationY.setText(Float.toString(node.getGridPosition().y));
    }

//    public void onGridPressed00() {gridPressed(0,0, cross00);}
//    public void onGridPressed01() {gridPressed(0,1, cross01);}
//    public void onGridPressed02() {gridPressed(0,2, cross02);}
//    public void onGridPressed03() {gridPressed(0,3, cross03);}
//    public void onGridPressed04() {gridPressed(0,4, cross04);}
//    public void onGridPressed10() {gridPressed(1,0, cross10);}
//    public void onGridPressed11() {gridPressed(1,1, cross11);}
//    public void onGridPressed12() {gridPressed(1,2, cross12);}
//    public void onGridPressed13() {gridPressed(1,3, cross13);}
//    public void onGridPressed14() {gridPressed(1,4, cross14);}
//    public void onGridPressed20() {gridPressed(2,0, cross20);}
//    public void onGridPressed21() {gridPressed(2,1, cross21);}
//    public void onGridPressed22() {gridPressed(2,2, cross22);}
//    public void onGridPressed23() {gridPressed(2,3, cross23);}
//    public void onGridPressed24() {gridPressed(2,4, cross24);}
//    public void onGridPressed30() {gridPressed(3,0, cross30);}
//    public void onGridPressed31() {gridPressed(3,1, cross31);}
//    public void onGridPressed32() {gridPressed(3,2, cross32);}
//    public void onGridPressed33() {gridPressed(3,3, cross33);}
//    public void onGridPressed34() {gridPressed(3,4, cross34);}
//    public void onGridPressed40() {gridPressed(4,0, cross40);}
//    public void onGridPressed41() {gridPressed(4,1, cross41);}
//    public void onGridPressed42() {gridPressed(4,2, cross42);}
//    public void onGridPressed43() {gridPressed(4,3, cross43);}
//    public void onGridPressed44() {gridPressed(4,4, cross44);}
//    public void onGridPressed50() {gridPressed(5,0, cross50);}
//    public void onGridPressed51() {gridPressed(5,1, cross51);}
//    public void onGridPressed52() {gridPressed(5,2, cross52);}
//    public void onGridPressed53() {gridPressed(5,3, cross53);}
//    public void onGridPressed54() {gridPressed(5,4, cross54);}

    public void onGridPressed00() {gridPressed(0,4, cross00);}
    public void onGridPressed01() {gridPressed(0,3, cross01);}
    public void onGridPressed02() {gridPressed(0,2, cross02);}
    public void onGridPressed03() {gridPressed(0,1, cross03);}
    public void onGridPressed04() {gridPressed(0,0, cross04);}
    public void onGridPressed10() {gridPressed(1,4, cross10);}
    public void onGridPressed11() {gridPressed(1,3, cross11);}
    public void onGridPressed12() {gridPressed(1,2, cross12);}
    public void onGridPressed13() {gridPressed(1,1, cross13);}
    public void onGridPressed14() {gridPressed(1,0, cross14);}
    public void onGridPressed20() {gridPressed(2,4, cross20);}
    public void onGridPressed21() {gridPressed(2,3, cross21);}
    public void onGridPressed22() {gridPressed(2,2, cross22);}
    public void onGridPressed23() {gridPressed(2,1, cross23);}
    public void onGridPressed24() {gridPressed(2,0, cross24);}
    public void onGridPressed30() {gridPressed(3,4, cross30);}
    public void onGridPressed31() {gridPressed(3,3, cross31);}
    public void onGridPressed32() {gridPressed(3,2, cross32);}
    public void onGridPressed33() {gridPressed(3,1, cross33);}
    public void onGridPressed34() {gridPressed(3,0, cross34);}
    public void onGridPressed40() {gridPressed(4,4, cross40);}
    public void onGridPressed41() {gridPressed(4,3, cross41);}
    public void onGridPressed42() {gridPressed(4,2, cross42);}
    public void onGridPressed43() {gridPressed(4,1, cross43);}
    public void onGridPressed44() {gridPressed(4,0, cross44);}
    public void onGridPressed50() {gridPressed(5,4, cross50);}
    public void onGridPressed51() {gridPressed(5,3, cross51);}
    public void onGridPressed52() {gridPressed(5,2, cross52);}
    public void onGridPressed53() {gridPressed(5,1, cross53);}
    public void onGridPressed54() {gridPressed(5,0, cross54);}


    @FXML
    ImageView cross00;
    @FXML
    ImageView cross10;
    @FXML
    ImageView cross20;
    @FXML
    ImageView cross30;
    @FXML
    ImageView cross40;
    @FXML
    ImageView cross50;
    @FXML
    ImageView cross01;
    @FXML
    ImageView cross11;
    @FXML
    ImageView cross21;
    @FXML
    ImageView cross31;
    @FXML
    ImageView cross41;
    @FXML
    ImageView cross51;
    @FXML
    ImageView cross02;
    @FXML
    ImageView cross12;
    @FXML
    ImageView cross22;
    @FXML
    ImageView cross32;
    @FXML
    ImageView cross42;
    @FXML
    ImageView cross52;
    @FXML
    ImageView cross03;
    @FXML
    ImageView cross13;
    @FXML
    ImageView cross23;
    @FXML
    ImageView cross33;
    @FXML
    ImageView cross43;
    @FXML
    ImageView cross53;
    @FXML
    ImageView cross04;
    @FXML
    ImageView cross14;
    @FXML
    ImageView cross24;
    @FXML
    ImageView cross34;
    @FXML
    ImageView cross44;
    @FXML
    ImageView cross54;
}