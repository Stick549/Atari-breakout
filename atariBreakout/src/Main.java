import java.awt.*;

public class Main {
    GameFrame frame;
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size.getWidth());
        System.out.println(size.getHeight());
    }
}