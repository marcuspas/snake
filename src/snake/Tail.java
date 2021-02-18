package snake;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tail extends Rectangle {

    public Tail(double x, double y,int w, int h, Color color) {
        super(w, h, color);
        setTranslateX(x);
        setTranslateY(y);
    }
  
}
