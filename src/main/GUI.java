package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GUI extends Application {
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller();
        Group window = new Group();
        for (int i = 0; i < controller.getFieldSize(); i++) {
            for (int j = 0; j < controller.getFieldSize(); j++) {
                Rectangle point = new Rectangle(2 * i, 2 * j, 2, 2);
                window.getChildren().add(point);
            }
        }
        primaryStage.setTitle("Flocking Boids");
        Scene scene = new Scene(window, controller.getFieldSize() *2, controller.getFieldSize()*2);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("mouse click detected! "+event.getSource());
                final java.awt.Point p = MouseInfo.getPointerInfo().getLocation();
                System.out.println(p.getX());
                System.out.println(p.getY());
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

}
