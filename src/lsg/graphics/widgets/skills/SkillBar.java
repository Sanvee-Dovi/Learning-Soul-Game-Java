package lsg.graphics.widgets.skills;
 import javafx.scene.input.KeyCode;
 import  javafx.scene.layout.HBox;

 import java.util.LinkedHashMap;
 import java.util.Map;

public class SkillBar extends HBox {

    private static LinkedHashMap<KeyCode,String>DEFAULT_BINDING=new LinkedHashMap<>();
    static {
        DEFAULT_BINDING.put(KeyCode.DIGIT1,"&");
        DEFAULT_BINDING.put(KeyCode.DIGIT2,"é");
        DEFAULT_BINDING.put(KeyCode.DIGIT3,"\"");
        DEFAULT_BINDING.put(KeyCode.DIGIT4,"'");
        DEFAULT_BINDING.put(KeyCode.DIGIT5,"(");
    }
    private SkillTrigger triggers[];

    public SkillBar(){
        super();
        this.setSpacing(10);
        this.setPrefHeight(110);
    }
    private void init(){
        this.triggers = new SkillTrigger[DEFAULT_BINDING.size()];
        int i=0;
        for(Map.Entry<KeyCode,String>M : DEFAULT_BINDING.entrySet()){
                 triggers[i] = new SkillTrigger(M.getKey(),M.getValue(),null,null);
                 this.getChildren().add(triggers[i]);
            i+=1;
        }
    }
    public	SkillTrigger getTrigger(int	slot){
        return triggers[slot];
    }

    public	void process(KeyCode code){
        if(!this.isDisabled()){
          for(int i = 0; i<this.triggers.length; i++){
              if(this.triggers[i].getKeyCode()==code){
                  this.triggers[i].trigger();
              }
          }
        }
    }


}
