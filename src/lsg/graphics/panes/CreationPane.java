package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;

import javafx.event.EventHandler;


public class CreationPane  extends VBox {

    private TextField nameField;
    private static final Duration ANIMATION_DURATION = Duration.millis(1500);
    private static final double ZOOM_SCALE = 1.5;
    private static final double ZOOM_Y = 0.25;
    public TextField getNameField() {
        return nameField;
    }

    public CreationPane(){
        this.nameField = new TextField();
        GameLabel label = new GameLabel("Player Name");
        this.getChildren().addAll(label,nameField);
    }

   public void fadeIn(EventHandler<ActionEvent>finishedHandler){
       FadeTransition ft = new FadeTransition(ANIMATION_DURATION);
           ft.setToValue(1);
           ft.setNode(this);
           ft.setCycleCount(1);
           ft.setOnFinished(finishedHandler);
           ft.play();
    }

    public void restart(EventHandler<ActionEvent>finishedHandler){
        FadeTransition ft = new FadeTransition(ANIMATION_DURATION);
        ft.setToValue(0);
        ft.setFromValue(200);
        ft.setNode(this);
        ft.setCycleCount(1);
        ft.play();
    }

}
