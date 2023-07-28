import java.awt.*;

public class Player {
    int width;
    int height;
    int x;
    int y;

    public Player(int width, int height, int screenWidth, int yPos) {
        this.width = width;
        this.height = height;
        x = screenWidth/2;
        y = yPos;
    }
    public void move(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x = (int)(p.x + (GamePanel.screenWidth - size.getWidth())/2);
    }
}
