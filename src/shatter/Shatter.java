/*
 * TODO
 * - make it an actual node
 * - make it react on touch and collision
 * - improve the math
 * - create meaningful interface for good usability
 * 
 */
package shatter;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ParallelTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author jiri
 */
public class Shatter extends Application {

    // STRENGTH determines how many pieces'll shatter produce
    public final int STRENGTH = 3;
    private final int arcLength = 360 / STRENGTH;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("@javafxcz - Shatter - www.dredwerkz.cz");
        final Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        
        final Circle circle = new Circle(50, Color.BLACK);
        circle.setCenterX(scene.getWidth() / 2);
        circle.setCenterY(scene.getHeight() / 2);
        //final Color colors[] = {Color.BLACK, Color.RED, Color.GOLD};
        final Group pieces =  new Group();
        
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                for (int i = 0; i < STRENGTH; i++) {
                    pieces.getChildren().add(ArcBuilder.create()
                        .centerX(circle.getCenterX())
                        .centerY(circle.getCenterY())
                        .radiusX(circle.getRadius())
                        .radiusY(circle.getRadius())
                        .startAngle((360 / STRENGTH) * i).length(arcLength)
                        //.fill(colors[i])
                        .type(ArcType.ROUND)
                        .build());
                    circle.setOpacity(0);
                }
                
                root.getChildren().add(pieces);
                
                TranslateTransition tArc0 = TranslateTransitionBuilder.create()
                    .toX(circle.getCenterX() + Math.sin(arcLength / 2) * pieces.getChildren().get(0).getBoundsInLocal().getMaxY())
                    .toY(-pieces.getChildren().get(0).getBoundsInLocal().getMaxY())
                    .node(pieces.getChildren().get(0))
                    .duration(Duration.seconds(3)).interpolator(Interpolator.LINEAR)
                    .build();
                RotateTransition rotArc0 = RotateTransitionBuilder.create()
                    .byAngle(360)//.toAngle(Math.random() * 360)
                    .node(pieces.getChildren().get(0))
                    .duration(Duration.seconds(2)).cycleCount(Timeline.INDEFINITE).interpolator(Interpolator.LINEAR)
                    .build();
                
                TranslateTransition tArc1 = TranslateTransitionBuilder.create()
                    .toX(-circle.getCenterX() + Math.sin(arcLength / 2) * pieces.getChildren().get(1).getBoundsInLocal().getMaxY())
                    .toY(-pieces.getChildren().get(1).getBoundsInLocal().getMaxY())
                    .node(pieces.getChildren().get(1))
                    .duration(Duration.seconds(3)).interpolator(Interpolator.LINEAR)
                    .build();
                 RotateTransition rotArc1 = RotateTransitionBuilder.create()
                    .byAngle(360)//.toAngle(Math.random() * 360)
                    .node(pieces.getChildren().get(1))
                    .duration(Duration.seconds(2)).cycleCount(Timeline.INDEFINITE).interpolator(Interpolator.LINEAR)
                    .build();
                                
                TranslateTransition tArc2 =  TranslateTransitionBuilder.create()
                    .toX(circle.getCenterX() + Math.sin(arcLength / 2) * pieces.getChildren().get(2).getBoundsInLocal().getMaxY())
                    .toY(pieces.getChildren().get(2).getBoundsInLocal().getMaxY())
                    .node(pieces.getChildren().get(2))
                    .duration(Duration.seconds(3)).interpolator(Interpolator.LINEAR)
                    .build();
                RotateTransition rotArc2 = RotateTransitionBuilder.create()
                    .byAngle(360)//.toAngle(Math.random() * 360)
                    .node(pieces.getChildren().get(2))
                    .duration(Duration.seconds(2)).cycleCount(Timeline.INDEFINITE).interpolator(Interpolator.LINEAR)
                    .build();
                
                ParallelTransition pArcs = ParallelTransitionBuilder.create()
                    .children(tArc0, rotArc0, tArc1, rotArc1, tArc2, rotArc2)
                    .build();
                pArcs.play();
            }
        });
        
        root.getChildren().add(circle);
        primaryStage.show();
    }
}
