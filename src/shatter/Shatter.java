package shatter;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author George
 */
public class Shatter extends Parent {

  private Duration fadeOutDuration = Duration.seconds(3);
  private Duration rotateDuration = Duration.seconds(3);
  private Duration translateDuration = Duration.seconds(3);
  private Interpolator fadeOutInterpolator = Interpolator.LINEAR;
  private Interpolator rotateInterpolator = Interpolator.LINEAR;
  private Interpolator translateInterpolator = Interpolator.LINEAR;
  private double strength = 8;
  private final double arcLength = 360 / getStrength();
  private final ParallelTransition par = new ParallelTransition();
  private final Group pieces = new Group();
  private boolean rotateRandom = true;
  private Circle circle;
  private Ellipse ellipse;
  private Rectangle rectangle;
  private Text text;
  private ImageView imageView;
  private String CIRCLE = "javafx.scene.shape.Circle";
  private String ELLIPSE = "javafx.scene.shape.Ellipse";
  private String IMAGE = "javafx.scene.image.ImageView";
  private String RECTANGLE = "javafx.scene.shape.Rectangle";
  private String TEXT = "javafx.scene.text.Text";

  /**
   * Creates Shatter node with default behavior
   *
   * @param node the node representing this shatter - instance of Circle,
   * Ellipse, ImageView, Rectangle or Text.
   */
  public Shatter(Node node) {

    if (node != null) {
      if (node.getClass().getName().equalsIgnoreCase(CIRCLE)) {
        shatterCircle(node);
      } else if (node.getClass().getName().equalsIgnoreCase(ELLIPSE)) {
        shatterEllipse(node); 
      } else if (node.getClass().getName().equalsIgnoreCase(IMAGE)) {
        shatterImageView(node);
      } else if (node.getClass().getName().equalsIgnoreCase(RECTANGLE)) {
        shatterRectangle(node);
      } else if (node.getClass().getName().equalsIgnoreCase(TEXT)) {
        shatterText(node);
      } else {
        System.err.println(node.getClass().getName() + " is not supported.");
      }
    }
  }

  /**
   * @return the fadeOutDuration
   */
  public Duration getFadeOutDuration() {
    return fadeOutDuration;
  }

  /**
   * @param fadeOutDuration the duration to set Default fade out duration is 3
   * seconds
   */
  public void setFadeOutDuration(Duration fadeOutDuration) {
    this.fadeOutDuration = fadeOutDuration;
  }

  /**
   * @return the fadeOutInterpolator
   */
  public Interpolator getFadeOutInterpolator() {
    return fadeOutInterpolator;
  }

  /**
   * @param fadeOutInterpolator the fadeOutInterpolator to set
   */
  public void setFadeOutInterpolator(Interpolator fadeOutInterpolator) {
    this.fadeOutInterpolator = fadeOutInterpolator;
  }

  /**
   * @return the rotateDuration
   */
  public Duration getRotateDuration() {
    return rotateDuration;
  }

  /**
   * @param rotateDuration the rotateDuration to set Default rotate duration is
   * 3 seconds
   */
  public void setRotateDuration(Duration rotateDuration) {
    this.rotateDuration = rotateDuration;
  }

  /**
   * @return the rotateInterpolator
   */
  public Interpolator getRotateInterpolator() {
    return rotateInterpolator;
  }

  /**
   * @param rotateInterpolator the rotateInterpolator to set
   */
  public void setRotateInterpolator(Interpolator rotateInterpolator) {
    this.rotateInterpolator = rotateInterpolator;
  }

  /**
   * @return the strength that determines how many pieces will the shatter
   * produce.
   */
  public double getStrength() {
    return strength;
  }

  /**
   * @param strength set the strength that determines how many pieces will the
   * shatter produce. Default strength is 8
   */
  public void setStrength(double strength) {
    this.strength = strength;
  }

  /**
   * @return the translateDuration
   */
  public Duration getTranslateDuration() {
    return translateDuration;
  }

  /**
   *
   * @param translateDuration set the how fast will the shatter fall apart
   * Default translate duration is 3 seconds
   */
  public void setTranslateDuration(Duration translateDuration) {
    this.translateDuration = translateDuration;
  }

  /**
   * @return the translateInterpolator
   */
  public Interpolator getTranslateInterpolator() {
    return translateInterpolator;
  }

  /**
   * @param translateInterpolator the translateInterpolator to set
   */
  public void setTranslateInterpolator(Interpolator translateInterpolator) {
    this.translateInterpolator = translateInterpolator;
  }

  /**
   * @return the rotateRandom
   */
  public boolean isRotateRandom() {
    return rotateRandom;
  }

  /**
   * @param rotateRandom determines if the pieces will rotate by random angle or
   * by 360 degrees.
   */
  public void setRotateRandom(boolean rotateRandom) {
    this.rotateRandom = rotateRandom;
  }

  private FadeTransition setupFadeTransition(Node piece) {
    FadeTransition ft = new FadeTransition();
    ft.setNode(piece);
    ft.setDuration(fadeOutDuration);
    ft.setInterpolator(fadeOutInterpolator);
    ft.setFromValue(1);
    ft.setToValue(0);
    return ft;
  }

  private RotateTransition setupRotateTransition(Node piece) {
    RotateTransition rt = new RotateTransition();
    rt.setNode(piece);
    rt.setDuration(rotateDuration);
    rt.setInterpolator(rotateInterpolator);

    if (!isRotateRandom()) {
      rt.setByAngle(360);
      rt.setCycleCount(Timeline.INDEFINITE);
    } else {
      rt.setToAngle(Math.random() * 360);
      rt.setCycleCount(1);
    }
    return rt;
  }

  private TranslateTransition setupTranslateTransition(Node piece) {
    TranslateTransition tt = new TranslateTransition();
    tt.setNode(piece);
    tt.setDuration(translateDuration);
    tt.setInterpolator(translateInterpolator);
    return tt;
  }

  private void shatterCircle(Node node) {
    circle = (Circle) node;
    getChildren().add(circle);

    circle.setOnMouseClicked(e -> {

      for (int i = 0; i < strength; i++) {
        Arc piece = new Arc();
        piece.setCenterX(circle.getCenterX());
        piece.setCenterY(circle.getCenterY());
        piece.setRadiusX(circle.getRadius());
        piece.setRadiusY(circle.getRadius());
        piece.setFill(circle.getFill());
        piece.setStartAngle((360 / strength) * i);
        piece.setLength(arcLength);
        piece.setType(ArcType.ROUND);
        pieces.getChildren().add(piece);
        TranslateTransition pieceTranslation = setupTranslateTransition(pieces.getChildren().get(i));

        if (i + 1 <= strength * .25) {
          System.out.println("i <= strength * .25: " + i + ", " + strength * .25);
          pieceTranslation.setToX(circle.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .5) {
          System.out.println("i <= strength * .5: " + i + ", " + strength * .5);
          pieceTranslation.setToX(-circle.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .75) {
          System.out.println("i <= strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(circle.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 > strength * .75) {
          System.out.println("i > strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(-circle.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        }

        RotateTransition pieceRotation = setupRotateTransition(pieces.getChildren().get(i));
        FadeTransition pieceFadeOut = setupFadeTransition(pieces.getChildren().get(i));
        par.getChildren().addAll(pieceTranslation, pieceRotation, pieceFadeOut);
      }

      getChildren().removeAll(circle);
      par.play();
    });

    getChildren().add(pieces);
  }

  private void shatterEllipse(Node node) {
    ellipse = (Ellipse) node;
    getChildren().add(ellipse);
    ellipse.setOnMouseClicked(e -> {

      for (int i = 0; i < strength; i++) {
        Arc piece = new Arc();
        piece.setCenterX(ellipse.getCenterX());
        piece.setCenterY(ellipse.getCenterY());
        piece.setRadiusX(ellipse.getRadiusX());
        piece.setRadiusY(ellipse.getRadiusY());
        piece.setFill(ellipse.getFill());
        piece.setStartAngle((360 / strength) * i);
        piece.setLength(arcLength);
        piece.setType(ArcType.ROUND);
        pieces.getChildren().add(piece);
        TranslateTransition pieceTranslation = setupTranslateTransition(pieces.getChildren().get(i));

        if (i + 1 <= strength * .25) {
          System.out.println("i <= strength * .25: " + i + ", " + strength * .25);
          pieceTranslation.setToX(ellipse.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .5) {
          System.out.println("i <= strength * .5: " + i + ", " + strength * .5);
          pieceTranslation.setToX(-ellipse.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .75) {
          System.out.println("i <= strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(ellipse.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 > strength * .75) {
          System.out.println("i > strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(-ellipse.getCenterX() + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        }

        RotateTransition pieceRotation = setupRotateTransition(pieces.getChildren().get(i));
        FadeTransition pieceFadeOut = setupFadeTransition(pieces.getChildren().get(i));
        par.getChildren().addAll(pieceTranslation, pieceRotation, pieceFadeOut);
      }

      getChildren().removeAll(ellipse);
      par.play();
    });

    getChildren().add(pieces);
  }

  private void shatterImageView(Node node) {
    imageView = (ImageView) node;
    getChildren().add(imageView);
    SnapshotParameters sp = new SnapshotParameters();
    sp.setFill(Color.TRANSPARENT);

    /*System.out.println("x: "+ imageView.getX());
     System.out.println("y: "+ imageView.getY());
     System.out.println("width: "+ imageView.getImage().getWidth());
     System.out.println("height: "+ imageView.getImage().getHeight());*/
    imageView.setOnMouseClicked(e -> {

      for (int i = 0; i < strength; i++) {;
        Arc piece = new Arc();
        piece.setCenterX(imageView.getX() + imageView.getImage().getWidth() / 2);
        piece.setCenterY(imageView.getY() + imageView.getImage().getHeight() / 2);
        piece.setRadiusX(Math.max(imageView.getImage().getWidth(), imageView.getImage().getHeight()));
        piece.setRadiusY(Math.max(imageView.getImage().getWidth(), imageView.getImage().getHeight()));
        piece.setStartAngle((360 / strength) * i);
        piece.setLength(arcLength);
        piece.setType(ArcType.ROUND);
        imageView.setClip(piece);
        WritableImage wimg = imageView.snapshot(sp, null);
        ImageView pieceImageView = new ImageView(wimg);
        pieceImageView.setX(imageView.getX());
        pieceImageView.setY(imageView.getY());
        pieces.getChildren().add(pieceImageView);
        TranslateTransition pieceTranslation = setupTranslateTransition(pieces.getChildren().get(i));

        if (i + 1 <= strength * .25) {
          System.out.println("i <= strength * .25: " + i + ", " + strength * .25);
          pieceTranslation.setToX(imageView.getImage().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());//pieces.getChildren().get(i)
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .5) {
          System.out.println("i <= strength * .5: " + i + ", " + strength * .5);
          pieceTranslation.setToX(-imageView.getImage().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .75) {
          System.out.println("i <= strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(imageView.getImage().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 > strength * .75) {
          System.out.println("i > strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(-imageView.getImage().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        }

        RotateTransition pieceRotation = setupRotateTransition(pieces.getChildren().get(i));
        FadeTransition pieceFadeOut = setupFadeTransition(pieces.getChildren().get(i));
        par.getChildren().addAll(pieceTranslation, pieceRotation, pieceFadeOut);
      }

      getChildren().removeAll(imageView);
      par.play();
    });

    getChildren().add(pieces);
  }

  private void shatterRectangle(Node node) {
    rectangle = (Rectangle) node;
    getChildren().add(rectangle);
    rectangle.setOnMouseClicked(e -> {

      for (int i = 0; i < strength; i++) {;
        Arc piece = new Arc();
        piece.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
        piece.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
        piece.setRadiusX(Math.max(rectangle.getWidth(), rectangle.getHeight()));
        piece.setRadiusY(Math.max(rectangle.getWidth(), rectangle.getHeight()));
        piece.setFill(rectangle.getFill());
        piece.setStartAngle((360 / strength) * i);
        piece.setLength(arcLength);
        piece.setType(ArcType.ROUND);
        Shape result = Shape.intersect(piece, rectangle);
        pieces.getChildren().add(result);
        TranslateTransition pieceTranslation = setupTranslateTransition(pieces.getChildren().get(i));

        if (i + 1 <= strength * .25) {
          System.out.println("i <= strength * .25: " + i + ", " + strength * .25);
          pieceTranslation.setToX(rectangle.getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .5) {
          System.out.println("i <= strength * .5: " + i + ", " + strength * .5);
          pieceTranslation.setToX(-rectangle.getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .75) {
          System.out.println("i <= strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(rectangle.getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 > strength * .75) {
          System.out.println("i > strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(-rectangle.getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());

        }

        FadeTransition pieceFadeOut = setupFadeTransition(pieces.getChildren().get(i));
        RotateTransition pieceRotation = setupRotateTransition(pieces.getChildren().get(i));
        par.getChildren().addAll(pieceTranslation, pieceRotation, pieceFadeOut);
      }

      getChildren().removeAll(rectangle);
      par.play();
    });

    getChildren().add(pieces);
  }

  private void shatterText(Node node) {
    text = (Text) node;
    getChildren().add(text);
    text.setOnMouseClicked(e -> {

      for (int i = 0; i < strength; i++) {;
        Arc piece = new Arc();
        piece.setCenterX(text.getX() + text.getLayoutBounds().getWidth() / 2);
        piece.setCenterY(text.getY() + text.getLayoutBounds().getHeight() / 2);
        piece.setRadiusX(Math.max(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight()));
        piece.setRadiusY(Math.max(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight()));
        piece.setFill(text.getFill());
        piece.setStartAngle((360 / strength) * i);
        piece.setLength(arcLength);
        piece.setType(ArcType.ROUND);
        Shape result = Shape.intersect(piece, text);
        pieces.getChildren().add(result);
        TranslateTransition pieceTranslation = setupTranslateTransition(pieces.getChildren().get(i));

        if (i + 1 <= strength * .25) {
          System.out.println("i <= strength * .25: " + i + ", " + strength * .25);
          pieceTranslation.setToX(text.getLayoutBounds().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .5) {
          System.out.println("i <= strength * .5: " + i + ", " + strength * .5);
          pieceTranslation.setToX(-text.getLayoutBounds().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(-pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 <= strength * .75) {
          System.out.println("i <= strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(text.getLayoutBounds().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        } else if (i + 1 > strength * .75) {
          System.out.println("i > strength * .75: " + i + ", " + strength * .75);
          pieceTranslation.setToX(-text.getLayoutBounds().getWidth() / 2 + Math.sin((360 / strength) * i + (arcLength / 2)) * pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
          pieceTranslation.setToY(pieces.getChildren().get(i).getBoundsInLocal().getMaxY());
        }

        FadeTransition pieceFadeOut = setupFadeTransition(pieces.getChildren().get(i));
        RotateTransition pieceRotation = setupRotateTransition(pieces.getChildren().get(i));
        par.getChildren().addAll(pieceTranslation, pieceRotation, pieceFadeOut);
      }

      getChildren().removeAll(text);
      par.play();
    });

    getChildren().add(pieces);
  }
}
