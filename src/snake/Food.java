package snake;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {

    public Food(int x, int y, int w, int h, Color color) {
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
    }

    void move(ArrayList<Tail> list) {
        Random rand = new Random();
        int x = (rand.nextInt(19)) * 30 + 1;
        int y = (rand.nextInt(19)) * 30 + 1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBoundsInParent().intersects(x, y, 18, 18)) {
                x = (rand.nextInt(19)) * 30 + 1;
                y = (rand.nextInt(19)) * 30 + 1;
                i = -1;
                System.out.println("!!!");
            }
        }

        setTranslateX(x);
        setTranslateY(y);
    }
}
