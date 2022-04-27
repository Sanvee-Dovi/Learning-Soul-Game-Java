package lsg.graphics.widgets.characters.statbars;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lsg.graphics.ImageFactory;
import lsg.graphics.widgets.texts.GameLabel;

public class StatBar extends BorderPane {

 private ImageView avatar ;
 private GameLabel name ;
 private ProgressBar lifeBar;
 private ProgressBar	stamBar;

    public GameLabel getName() {
        return name;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public ProgressBar getLifeBar() {
        return lifeBar;
    }

    public ProgressBar getStamBar() {
        return stamBar;
    }


    public StatBar(){
        this.setPrefSize(350,100);
        avatar = new ImageView() ;
        this.setLeft(avatar);
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(100);
        avatar.setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.HERO_HEAD)[0]);
        name = new GameLabel("default");
        name.setStyle("-fx-font-size: 33px");
        lifeBar = new ProgressBar();
        stamBar = new ProgressBar();
        lifeBar.setMaxWidth(Double.MAX_VALUE);
        stamBar.setMaxWidth(180);
        stamBar.setMaxHeight(10);
        lifeBar.setStyle("-fx-accent: red");
        stamBar.setStyle("-fx-accent: greenyellow");
        VBox vb = new VBox();
        vb.getChildren().addAll(this.name,this.lifeBar,this.stamBar);
        this.setLeft(this.avatar);
        this.setCenter(vb);
    }

    public void flip(){
        this.setScaleX(-this.getScaleX());
        this.name.setScaleX(-this.name.getScaleX());
    }

}
