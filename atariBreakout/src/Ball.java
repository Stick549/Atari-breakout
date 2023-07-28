public class Ball {
    int x;
    int y;
    int xVel;
    int yVel;
    int size;
    int screenHeight;
    int screenWidth;

    public Ball(float screenHeight, float screenWidth, int size) {
        this.size = size;
        this.screenWidth = (int)screenWidth;
        this.screenHeight = (int)screenHeight;
        x = (int)(screenWidth*0.5);
        y = (int)(screenHeight*0.75);
        this.xVel = (int)(5 * (screenWidth/1000));
        this.yVel = (int)(5 * (screenHeight/850));
    }
    public void move(){
        x += xVel;
        y += yVel;
    }
    public void horizontalBounce(){
        yVel = yVel *-1;
    }
    public void verticalBounce(){
        xVel = xVel *-1;
    }
    public void checkPlayerCollision(Player player){
        boolean touching = false;
        if (((y+size/2) >= (player.y-player.height/2)) && ((x+size/2) >= (player.x-player.width/2)) && ((x+size/2) <= (player.x+ player.width/2))&&(!touching)){
            horizontalBounce();
            touching = true;
        } else if ((y + size/2 <= player.y + player.height/2) && (y - size/2 >= player.y - player.height/2) && ((x+size/2 <= player.x + player.height/2) || (x - size/2 >= player.x - player.height/2)) && !touching) {
            verticalBounce();
            touching = true;
        }
        else{
            touching = false;
        }
    }
    public void checkWallCollision(){
        boolean touching = false;
        if (((y - size/2) <= 0) && !touching){
            horizontalBounce();
            touching = true;
        }
        else if (((x+size/2) >= screenWidth) && !touching) {
            verticalBounce();
            touching = true;
        }
        else if (((x-size/2) <= 0) & ! touching) {
            verticalBounce();
            touching = true;
        }
        else{
            touching = false;
        }
        if ((y + size/2) >= screenHeight) {
            GamePanel.running = false;
        }
    }
}
