package snake;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SnakeGame extends Application {

    private Pane root = new Pane();
    private final Rectangle player = new Rectangle(0, 0, 28, 28);
    private final Food food = new Food(30, 30, 28, 28, Color.RED);
    private final ArrayList<Tail> list = new ArrayList<>();
    private final ArrayList<Rectangle> snake = new ArrayList<>();
    private int dir;
    private int check = 0;
    private int run = 0;
    private boolean c = true;
    private Timeline timeline;
    private double rate = 1;   

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 600, 600,Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.setResizable(false);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.075), (ActionEvent event) -> {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
                if (c == true) {

                    switch (e.getCode()) {
                        case LEFT:
                            if (dir != 3 || list.isEmpty()) {
                                dir = 1;
                            }
                            break;
                        case UP:
                            if (dir != 4 || list.isEmpty()) {
                                dir = 2;
                            }
                            break;
                        case RIGHT:
                            if (dir != 1 || list.isEmpty()) {
                                dir = 3;
                            }
                            break;
                        case DOWN:
                            if (dir != 2 || list.isEmpty()) {
                                dir = 4;
                            }
                            break;
                        case Z:
                            rate = rate * 0.9;
                            timeline.setRate(rate);
                            System.out.println(rate);
                            break;
                        case X:
                            rate = rate / 0.9;
                            timeline.setRate(rate);
                            System.out.println(rate);
                            break;
                    }
                    c = false;
                }
            });

            switch (dir) {
                case 1:
                    player.setTranslateX(player.getTranslateX() - 30);
                    c = true;
                    break;
                case 2:
                    player.setTranslateY(player.getTranslateY() - 30);
                    c = true;
                    break;
                case 3:
                    player.setTranslateX(player.getTranslateX() + 30);
                    c = true;
                    break;
                case 4:
                    player.setTranslateY(player.getTranslateY() + 30);
                    c = true;
                    break;
            }

            if (checkLoss(player, list) == true) {
                timeline.stop();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setFill(Color.RED);
                }
            }

            if (player.getTranslateX() >= 600 || player.getTranslateX() < 0 || player.getTranslateY() >= 600 || player.getTranslateY() < 0) {
                kill();
            }

            snake.add(player);
            for (int i = 0; i < list.size(); i++) {
                snake.add(list.get(i));
            }

            for (int i = list.size(); i > 0; i--) {
                list.get(i - 1).setTranslateX(snake.get(i - 1).getTranslateX());
                list.get(i - 1).setTranslateY(snake.get(i - 1).getTranslateY());
            }

            snake.clear();
            scene.setFill(Color.BLACK);

            if (player.getBoundsInParent().intersects(food.getBoundsInParent())) {
                //score.setText(Integer.toString(list.size()));

                check++;
                if (check == 1) {
                    run = 2;
                } else {
                    run = 1;
                }

                for (int i = 0; i < run; i++) {

                    list.add(new Tail(player.getTranslateX(), player.getTranslateY(), 28, 28, Color.GREEN));
                    root.getChildren().add(list.get(list.size() - 1));
                    food.move(list);

                }
                /*for (int i = 0; i < list.size(); i++) {
                if (i % 2 == 1) {
                list.get(i).setFill(Color.RED);
                }
                }*/
                System.out.println(list.size());
            }

        });
        timeline.getKeyFrames().add(kf);
        timeline.play();

        primaryStage.show();
    }

    @Override
    public void init() {
        root.getChildren().addAll(player, food);
        food.move(list);
        player.setFill(Color.GREEN);
        player.setTranslateX(301);
        player.setTranslateY(31);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
    }

    public boolean checkLoss(Rectangle head, ArrayList<Tail> tail) {

        for (int i = 1; i < tail.size() - 1; i++) {
            if (player.getBoundsInParent().intersects(tail.get(i).getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public void kill() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setFill(Color.RED);
        }
        timeline.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
