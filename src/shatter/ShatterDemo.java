/*
 * TODO
 * - make it an actual node
 * - make it react on touch and collision
 * - improve the math
 * - create meaningful interface for good usability
 * 
 */
package shatter;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author jiri
 */
public class ShatterDemo extends Application {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("@dr3dwerkz - Shatter - www.dredwerkz.cz");
    final Group root = new Group();
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    Shatter circle = new Shatter(new Circle(100, 200, 50));
    circle.setStrength(12);
    circle.setDuration(Duration.millis(500));
    circle.setRotateRandom(false);
    
    Shatter rectangle = new Shatter(new Rectangle(300, 400, 100, 100));
    
    Image img = new Image("file:1001167_280184238793557_125115852_n.jpg", 300, 200, true, true);
    ImageView view = new ImageView(img);
    view.setX(250);
    view.setY(200);
    Shatter iv = new Shatter(view);
    
    Text text = new Text(600, 500, "Shatter me!");
    text.setFont(new Font(20));
    Shatter textShatter = new Shatter(text);
    
    root.getChildren().addAll(circle, iv, rectangle, textShatter);
    primaryStage.show();
  }
}