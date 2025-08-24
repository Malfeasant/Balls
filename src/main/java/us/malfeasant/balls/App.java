package us.malfeasant.balls;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static final int BALL_COUNT = 6;
    private static final int BALL_SIZE = 9;
    Node[] balls;

    @Override
    public void start(Stage stage) {
        var pane = new StackPane();
        stage.setTitle("Have a ball");
        stage.setScene(new Scene(pane, 480, 480));

        balls = new Node[BALL_COUNT];
        for (int i = 0; i < BALL_COUNT; ++i) {
            var hue = (360.0 * i / BALL_COUNT);
            var color = Color.hsb(hue, 1.0, 1.0);
            balls[i] = new Circle(240, 240, BALL_SIZE, color);
        }
        pane.getChildren().addAll(balls);

        var timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long time = now & 0xffffffffl;
                for (int i = 0; i < BALL_COUNT; ++i) {
                    // TODO I vaguely remember a way to use a foreach loop
                    // but still have access to an index... 
                    var phase = 2.0 * Math.PI * time / 0x100000000l;
                    var shift = Math.PI * i / BALL_COUNT;

                    var xComp = Math.cos(shift);
                    var yComp = Math.sin(shift);
                    var offset = Math.sin(phase + shift);

                    balls[i].setTranslateX(offset * xComp * 180);
                    balls[i].setTranslateY(offset * yComp * 180);
                }
            }
        };

        stage.show();
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }

}