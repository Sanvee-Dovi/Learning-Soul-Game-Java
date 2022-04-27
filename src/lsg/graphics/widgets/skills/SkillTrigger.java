package lsg.graphics.widgets.skills;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;


public class SkillTrigger extends AnchorPane {

    private ImageView view ;
    private Label text;
    private KeyCode keyCode;
    private SkillAction action;

    public KeyCode getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public Label getText() {
        return text;
    }

    public void setText(Label text) {
        this.text = text;
    }

    public SkillAction getAction() {
        return action;
    }

    public void setAction(SkillAction action) {
        this.action = action;
    }

    public Image getImage(){
        return view.getImage();
    }

    public void setImage(Image image){
       view.setImage(image);
    }

   public  SkillTrigger(KeyCode keyCode, String	text, Image	image,SkillAction action){
        this.keyCode=keyCode;
        this.text = new Label(text);
        this.view = new ImageView();
        this.view.setImage(image);
        this.action = action;
        this.BuildUI();
        this.addListeners();

   }

   private void BuildUI(){
        this.getStylesheets().add(CSSFactory.getStyleSheet("SkillTrigger.css"));
       this.getStyleClass().add("skill");
        this.view.setFitHeight(50);
        this.view.setFitWidth(50);
        this.getChildren().add(this.view);
        this.view.preserveRatioProperty();
        this.getChildren().add(this.text);
        AnchorPane.setTopAnchor(this.text,0.0);

   }

   public void trigger(){
        if(!this.isDisabled() && this.action!=null){
            action.execute();
        }
   }

   private void addListeners(){
        this.setOnMouseClicked(event -> {
            trigger();
        });
   }


}
