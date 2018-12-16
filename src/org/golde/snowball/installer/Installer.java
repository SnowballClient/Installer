package org.golde.snowball.installer;

import org.golde.snowball.installer.helpers.ConstantGetters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jaco.mp3.player.MP3Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;

public class Installer extends Application
{
    private final WebView curView;
    private MP3Player curPlayer;
    private final feedbackHandler feedbackHandler;
    public int curIndex;
    
    public Installer() {
        this.curView = new WebView();
        this.curPlayer = new MP3Player();
        this.feedbackHandler = new feedbackHandler(this);
        this.curIndex = 0;
    }
    
    public static void main(final String[] args) {
        Application.launch(Installer.class, new String[0]);
    }
    
    public void start(final Stage curStage) {
        this.curPlayer.addToPlayList(ConstantGetters.getAudio());
        this.curPlayer.play();
        final VBox curLayout = new VBox();
        curLayout.getChildren().add(this.curView);
        curStage.setScene(new Scene((Parent)curLayout));
        curStage.setTitle("Snowball Installer");
        curStage.getIcons().add(new Image(ConstantGetters.getIcon()));
        curStage.setResizable(false);
        curStage.setWidth(1063.0);
        curStage.setHeight(620.0);
        this.curView.setContextMenuEnabled(false);
        Platform.runLater(() -> this.curView.getEngine().load(ClassLoader.getSystemResource("index.html").toExternalForm()));
        this.curView.getEngine().getLoadWorker().stateProperty().addListener((o, t, tt) -> {
            if (tt == Worker.State.SUCCEEDED) {
                ((JSObject)this.curView.getEngine().executeScript("window")).setMember("feedback", this.feedbackHandler);
                if (this.curView.getEngine().getLocation().toLowerCase().contains("index.html")) {
                    this.registerWorkers();
                }
//                Document doc = curView.getEngine().getDocument();
//                Element e = doc.getElementById("intro");
//                e.setTextContent("I have changed you!");
            }
        });
        
        
        
        curStage.show();
    }
    
    private void registerWorkers() {
        new InstallThread(this).start();
        this.moveFoward();
        new Timeline(new KeyFrame[] { new KeyFrame(Duration.minutes(2.0), e -> {
                if (100 > this.curIndex) {
                    this.die();
                }
            }, new KeyValue[0]) }).play();
    }
    
    public void stop() {
        this.curPlayer.stop();
        this.curPlayer = null;
        System.exit(0);
    }
    
    public void moveFoward() {
        this.curIndex += 34;
        if (this.curIndex > 100) {
            new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(4.0), e -> Platform.runLater(() -> this.curView.getEngine().executeScript("javascript:Finish()")), new KeyValue[0]) }).play();
        }
    }
    
    public void die() {
        Platform.runLater(() -> this.curView.getEngine().load(ClassLoader.getSystemResource("error.html").toExternalForm()));
    }
}
