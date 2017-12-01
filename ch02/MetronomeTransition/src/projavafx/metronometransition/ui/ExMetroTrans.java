package projavafx.metronometransition.ui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExMetroTrans extends Application
{
    Button startBtn, stopBtn, resumBtn, pauseBtn;
    Circle ball;

    public static void main(String... args){ Application.launch(args); }

    public void start(Stage stage)
    {
        ball = new Circle(100, 50, 6, Color.RED);

        TranslateTransition anime = new TranslateTransition(new Duration(2000), ball);
        anime.setFromX(0);
        anime.setToX(200);
        anime.setAutoReverse(true);
        anime.setCycleCount(Animation.INDEFINITE);
        anime.setInterpolator(Interpolator.LINEAR);

        startBtn = new Button("Start");
        startBtn.setOnAction(e-> anime.playFromStart() );

        stopBtn = new Button("Stop");
        stopBtn.setOnAction(e-> anime.stop() );

        pauseBtn = new Button("Pause");
        pauseBtn.setOnAction(e-> anime.pause() );

        resumBtn = new Button("Resume");
        resumBtn.setOnAction(e-> anime.play() );

        HBox btnBar = new HBox(10, startBtn,stopBtn,pauseBtn,resumBtn);
        btnBar.setLayoutX(50);
        btnBar.setLayoutY(500);

        Group group = new Group(ball, btnBar);
        Scene scene = new Scene(group, 600, 600);
        stage.setScene(scene);

        startBtn.disableProperty().bind(
                anime.statusProperty().isNotEqualTo(Animation.Status.STOPPED)
        );

        stopBtn.disableProperty().bind(
                anime.statusProperty().isEqualTo(Animation.Status.STOPPED)
        );

        pauseBtn.disableProperty().bind(
                anime.statusProperty().isNotEqualTo(Animation.Status.RUNNING)
        );

        resumBtn.disableProperty().bind(
                anime.statusProperty().isNotEqualTo(Animation.Status.PAUSED)
        );

        stage.setTitle("ExMetroTrans");
        stage.show();

    }


}
