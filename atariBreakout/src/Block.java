public class Block {
    int width;
    int height;
    int x;
    int y;
    boolean exists = true;

    public Block(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    public void checkBallCollisions(Ball b){
        if(((b.x +(b.size/2)) <= (x+width/2)) && ((b.x - (b.size/2)) >= (x-width/2))) {

            if (((b.y + (b.size/2)) >= (y - height/2)) && ((b.y - (b.size/2)) <= (y + height/2))) {
                exists = false;
                b.horizontalBounce();
                GamePanel.score += 1;

            }
        }
        if(((b.y +(b.size/2)) <= (y+height/2)) && ((b.y - (b.size/2)) >= (y-height/2))) {
            if (((b.x + (b.size/2)) >= (x - width/2) && ((b.x - (b.size/2)) <= (x + width/2)))) {
                exists = false;
                b.verticalBounce();
                GamePanel.score += 1;

            }
        }
    }

}
