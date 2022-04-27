package lsg;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.exceptions.StaminaEmptyException;
import lsg.exceptions.WeaponBrokenException;
import lsg.exceptions.WeaponNullException;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.*;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.graphics.widgets.texts.GameLabel;
import lsg.weapons.Sword;

public class LearningSoulsGameApplication extends Application {
    private Scene scene;
    private AnchorPane root;
    private  TitlePane gameTitle;
    private  TitlePane Title;
    private CreationPane creationPane;
    private  String heroname;
    private AnimationPane animationPane;
    private Hero hero;
    private HeroRenderer heroRenderer;
    private Zombie zombie;
    private ZombieRenderer zombieRenderer;
    private HUDPane hudPane;



    @Override
    public void start(Stage primaryStage) throws Exception {
       primaryStage.setTitle("Learning Soul Games");
        root = new AnchorPane();
        scene = new Scene(root,1200,800);
       primaryStage.setScene(scene);
       primaryStage.setResizable(false);
       buildUI();
       addListerners();
       primaryStage.show();
       startGame();

    }

    public static void main(String[] args) {
        launch(args);
    }



    private void buildUI(){

        gameTitle = new TitlePane(this.scene, "Learning Soul Games");
       Title = new TitlePane(this.scene,"Learning Soul Games");
        creationPane = new CreationPane();
        hudPane = new HUDPane();
        creationPane.setOpacity(0);
        AnchorPane.setTopAnchor(gameTitle,10.0);
        AnchorPane.setLeftAnchor(gameTitle, 0.0);
        AnchorPane.setRightAnchor(gameTitle, 0.0);
         gameTitle.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(creationPane,430.0);
        AnchorPane.setLeftAnchor(creationPane,430.0);
        AnchorPane.setRightAnchor(creationPane,430.0);
        AnchorPane.setBottomAnchor(creationPane,430.0);
        AnchorPane.setTopAnchor(hudPane,0.0);
        AnchorPane.setLeftAnchor(hudPane,0.0);
        AnchorPane.setRightAnchor(hudPane,0.0);
        AnchorPane.setBottomAnchor(hudPane,0.0);
        this.scene.getStylesheets().add(CSSFactory.getStyleSheet("LSG.css"));
        this.scene.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        animationPane = new AnimationPane(root);
        this.root.getChildren().addAll(gameTitle,creationPane);
    }


      private void startGame(){
        gameTitle.zoomIn((event -> {
            System.out.println("ZOOM terminé !");
            creationPane.fadeIn((event1 -> {
                ImageFactory.preloadAll((()->{
                    System.out.println("Pré-chargement des images terminé");
                }));
            }));
        }));
      }

      private void addListerners(){
        creationPane.getNameField().setOnAction(event -> {
            heroname = creationPane.getNameField().getText();
            System.out.println("Nom du héro :" + heroname);
            if (!heroname.isEmpty()) {
                creationPane.restart(event1 -> {
                    this.root.getChildren().remove(creationPane);
                });
            }
            gameTitle.zoomOut(event3 -> {
                play();
            });


        });
      }

      private void play(){
         this.root.getChildren().add(animationPane);
        this.root.getChildren().add(hudPane);
          createHero();
          createMonster(event -> {
          hudPane.getMessagePane().showMessage("Fight");
             heroRenderer.attack(event1 -> {
                 try{
                     zombie.getHitWith(hero.attack());
                 } catch (Exception exception) {
                     hudPane.getMessagePane().showMessage(exception.getMessage());
                     exception.printStackTrace();
                 }
             });
          });


      }


      public void createHero(){
            hero = new Hero(heroname);
            hero.setWeapon(new Sword());
            heroRenderer =  animationPane.createHeroRenderer();
            heroRenderer.goTo(animationPane.getPrefWidth()*0.5 - heroRenderer.getFitWidth()*0.65, null);
            hudPane.getHeroStatBar().getName().setText(this.heroname);
            hudPane.getHeroStatBar().getAvatar().setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.HERO_HEAD)[0]);
            hudPane.getHeroStatBar().getLifeBar().progressProperty().bind(hero.lifeRateProperty());
            hudPane.getHeroStatBar().getStamBar().progressProperty().bind(hero.staminaRateProperty());
      }

      public void createMonster(EventHandler<ActionEvent>finishedHandler){
           zombie = new Zombie();
           zombie.setWeapon(new Sword());
           zombieRenderer = animationPane.createZombieRenderer();
           zombieRenderer.goTo(animationPane.getPrefWidth()*0.5 - zombieRenderer.getBoundsInLocal().getWidth() * 0.15, finishedHandler);
           hudPane.getMonsterStatBar().getName().setText(zombie.getName());
            hudPane.getMonsterStatBar().flip();
            hudPane.getMonsterStatBar().getAvatar().setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ZOMBIE_HEAD)[0]);
            hudPane.getMonsterStatBar().getLifeBar().progressProperty().bind(zombie.lifeRateProperty());
            hudPane.getMonsterStatBar().getStamBar().progressProperty().bind(zombie.staminaRateProperty());
            hudPane.getMonsterStatBar().getAvatar().setRotate(30);


      }


}
