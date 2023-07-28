import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    static final float screenWidth = 900.0f;
    static final float screenHeight = 765.0f;
    final int delay = 5;
    static int score;
    static boolean running;
    Timer timer;
    Player player = new Player((int)(200 * (screenWidth/1000)), (int)(20 * (screenHeight/850)), (int)screenWidth, (int)(830 * (screenHeight/850)));
    Ball ball;
    ArrayList<ArrayList<Block>> blocks;

    final int blockWidth = (int)(100 * (screenWidth/1000));
    final int blockHeight = (int)(50 * (screenHeight/850));
    int rowLength;
    int rows;
    Random random;
    ArrayList<ArrayList<Color>> colors;
    boolean win;

    public GamePanel() {
        running = false;
        this.setPreferredSize(new Dimension((int)screenWidth, (int)screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        win = false;
        running = true;
        rowLength = 17;
        //rowLength = 5;
        rows = 8;
        //rows = 3;
        score = 0;
        timer = new Timer(delay, this);
        random = new Random();
        timer.start();
        timer.setDelay((int)delay);
        ball = new Ball((int)screenHeight, (int)screenWidth, 20);
        blocks = new ArrayList<>();
        colors = new ArrayList<>();
        for(int i = 0; i < rows; i++){
            blocks.add(new ArrayList<>());
            colors.add(new ArrayList<>());
            for(int j = 0; j < rowLength; j++) {
            (blocks.get(i)).add(new Block(blockWidth, blockHeight, j*blockWidth+blockWidth/2, i*blockHeight+blockHeight/2));
            (colors.get(i)).add(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            g.setColor(Color.WHITE);
            g.fillRect(player.x-player.width/2, player.y-player.height/2, player.width, player.height);
            g.fillOval(ball.x - ball.size/2, ball.y - ball.size/2, ball.size, ball.size);
            //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            for(ArrayList<Block> row : blocks){
                for(Block block : row){
                    if(block.exists) {
                        g.setColor(colors.get(blocks.indexOf(row)).get(row.indexOf(block)));
                        g.fillRect(block.x - block.width/2, block.y - block.height/2, block.width, block.height);
                    }
                }
            }
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            g.setColor(Color.RED);
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score, ((int)screenWidth - metrics.stringWidth("Score: " + score))/2, (int)(screenHeight - g.getFont().getSize()));
        }
        else if (win) {
            win(g);
        }
        else{
            gameOver(g);

        }

    }
    public void gameOver(Graphics g){
        running = false;
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.setColor(Color.RED);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, ((int)screenWidth - metrics.stringWidth("Score: " + score))/2, g.getFont().getSize());
        g.drawString("Press Space To Restart", ((int)screenWidth - metrics.stringWidth("Press Space To Restart"))/2, (int)screenHeight -  g.getFont().getSize());
    }
    public void win(Graphics g){
        running = false;
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.setColor(Color.GREEN);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("You Win!", ((int)screenWidth - metrics.stringWidth("You Win!"))/2, g.getFont().getSize());
        g.drawString("Press Space To Restart", ((int)screenWidth - metrics.stringWidth("Press Space To Restart"))/2, (int)screenHeight -  g.getFont().getSize());
    }
    public void restart(){
        running = true;
        ball.x = (int)(screenWidth*0.5);
        ball.y = (int)(screenHeight*0.75);
        player.x = (int)screenWidth/2;
        blocks.clear();
        for(int i = 0; i < rows; i++){
            blocks.add(new ArrayList<Block>());
            for(int j = 0; j < rowLength; j++) {
                (blocks.get(i)).add(new Block(blockWidth, blockHeight, j*blockWidth+blockWidth/2, i*blockHeight+blockHeight/2));
            }
        }
        score = 0;
        ball.xVel = (int)(5 * (screenWidth/1000));
        ball.yVel = (int)(5 * (screenHeight/850));

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            ball.checkPlayerCollision(player);
            ball.checkWallCollision();
            ball.move();
            player.move();
            checkWin();
            for(ArrayList<Block> row : blocks){
                for(Block block : row){
                    if(block.exists) {
                        block.checkBallCollisions(ball);
                    }
                }
            }

        }
        repaint();
    }
    public void checkWin(){
        if(score == rows * rowLength){
            win = true;
            running = false;
        }
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            if ((e.getKeyCode() == KeyEvent.VK_SPACE) && (!running)) {
                restart();
            }
        }
    }
}
