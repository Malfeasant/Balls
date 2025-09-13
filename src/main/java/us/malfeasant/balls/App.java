package us.malfeasant.balls;

import java.util.Arrays;
import java.util.function.BiConsumer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private Node[] balls;
    private Pane pane;
    private Spinner<Integer> countSpinner;
    private Spinner<Integer> sizeSpinner;

    private void makeBalls() {
        int count = countSpinner.getValue();
        int size = sizeSpinner.getValue();
        pane.getChildren().clear();
        balls = new Node[count];
        for (int i = 0; i < count; ++i) {
            var hue = (360.0 * i / count);
            var color = Color.hsb(hue, 1.0, 1.0);
            balls[i] = new Circle(240, 240, size, color);
        }
        pane.getChildren().addAll(balls);
    }

    @Override
    public void start(Stage stage) {
        pane = new StackPane();
        var countLabel = new Label("Ball count:");
        countSpinner = new Spinner<>(1, 360, BALL_COUNT);
        var sizeLabel = new Label("Ball size:");
        sizeSpinner = new Spinner<>(1, Integer.MAX_VALUE, BALL_SIZE);
        var controls = new HBox(5.0, countLabel, countSpinner, sizeLabel, sizeSpinner);
        var borderPane = new BorderPane(pane, null, null, controls, null);

        makeBalls();

        countSpinner.valueProperty().addListener(e -> makeBalls());
        sizeSpinner.valueProperty().addListener(e -> makeBalls());

        stage.setTitle("Have a ball");
        stage.setScene(new Scene(borderPane, 480, 480));
        

        var timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int count = balls.length;
                long time = now & 0xffffffffl;
                forEachWithCounter(Arrays.asList(balls), (i, ball) -> {
//                Arrays.stream(balls).forEach(withCounter((i, ball) -> {
                    var phase = 2.0 * Math.PI * time / 0x100000000l;
                    var shift = Math.PI * i / count;

                    var xComp = Math.cos(shift);
                    var yComp = Math.sin(shift);
                    var offset = Math.sin(phase + shift);

                    ball.setTranslateX(offset * xComp * 180);
                    ball.setTranslateY(offset * yComp * 180);
                });
            }
        };

        stage.show();
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }

    private static <T> void forEachWithCounter(Iterable<T> source, BiConsumer<Integer, T> consumer) {
        int i = 0;

        for (T item : source) {
            consumer.accept(i, item);
            ++i;
        }
    }
}