package pac;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ShortDungeon extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("ShortDungeon");

        Group root = new Group();

		Canvas cvs = new Canvas(400, 300);
		root.getChildren().add(cvs);
        
		Scene scene = new Scene(root, 400, 300, Color.WHITE);
        
		stage.setScene(scene);
		stage.show();
 
    }
    public static void main(String[] args) {
        launch(args);
    }
}
