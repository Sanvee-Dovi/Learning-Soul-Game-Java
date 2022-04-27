package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import lsg.graphics.widgets.characters.statbars.StatBar;
import lsg.graphics.widgets.skills.SkillBar;

public class HUDPane extends BorderPane {

    private MessagePane messagePane;

    private StatBar heroStatBar;
    private StatBar monsterStatBar;
    private SkillBar skillBar;

    public StatBar getHeroStatBar() {
        return heroStatBar;
    }

    public StatBar getMonsterStatBar() {
        return monsterStatBar;
    }

    public MessagePane getMessagePane() {
        return messagePane;
    }

    public SkillBar getSkillBar() {
        return skillBar;
    }

    public void buildCenter(){
        messagePane = new MessagePane();
      this.setCenter(messagePane);
    }
    public void buildTop(){
       BorderPane borderPane = new BorderPane();
       this.heroStatBar = new StatBar();
        borderPane.setLeft(getHeroStatBar());
        this.monsterStatBar = new StatBar();
        borderPane.setRight(getMonsterStatBar());
        this.setTop(borderPane);

    }

    private	void buildBottom(){
        skillBar = new SkillBar();
       this.setBottom(skillBar);
    }

    public HUDPane(){
           super();
         this.buildCenter();
         this.buildTop();
         this.buildBottom();
    }



}
