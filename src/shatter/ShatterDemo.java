/*
 * TODO
 * - make it react on touch and collision
 * - improve the math
 * - create useful API
 * - add sound?
 * - fix bugs ;)
 */
package shatter;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
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
    circle.setFadeOutDuration(Duration.millis(500));
    circle.setRotateRandom(false);
    Shatter c = new Shatter(new Circle(600, 500, 50));
    
    Shatter rectangle = new Shatter(new Rectangle(300, 400, 100, 100));
    rectangle.setRotateDuration(Duration.millis(1000));
    
    Image img = new Image("file:1001167_280184238793557_125115852_n.jpg", 300, 200, true, true);
    ImageView view = new ImageView(img);
    view.setX(250);
    view.setY(200);
    Shatter iv = new Shatter(view);
    iv.setTranslateDuration(Duration.seconds(8));
    
    Text text = new Text(600, 500, "Shatter me!");
    text.setFont(new Font(20));
    Shatter textShatter = new Shatter(text);
    
    Ellipse ellipse = new Ellipse();
    ellipse.setCenterX(50);
    ellipse.setCenterY(50);
    ellipse.setRadiusX(50);
    ellipse.setRadiusY(25);
    Shatter e = new Shatter(ellipse);
    
    Shatter button = new Shatter(new Button("Meh"));
    
    root.getChildren().addAll(circle, c, iv, rectangle, textShatter, e, button);
    primaryStage.show();
  }
}
