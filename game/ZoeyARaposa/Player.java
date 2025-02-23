package game.ZoeyARaposa;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle {
    public int spd = 6; 
    private int dashSpd = 14; 
    private int dashDuration = 10; 
    private int dashTime = 0; 
    public boolean right, up, down, left;
    public boolean isDashing = false;
    private int lastDirectionX = 0;
    private int lastDirectionY = 0;

    public Player(int x, int y){
        super(x,y,32,32);
    }

    public void tick(){
        int currentSpd = isDashing ? dashSpd : spd;

        if (right || left || up || down || (isDashing && (lastDirectionX != 0 || lastDirectionY != 0))) {
            int dx = 0, dy = 0;
            if (right) {
                dx = currentSpd;
                lastDirectionX = 1;
                lastDirectionY = 0;
            } else if (left) {
                dx = -currentSpd;
                lastDirectionX = -1;
                lastDirectionY = 0;
            }
            if (up) {
                dy = -currentSpd;
                lastDirectionX = 0;
                lastDirectionY = -1;
            } else if (down) {
                dy = currentSpd;
                lastDirectionX = 0;
                lastDirectionY = 1;
            }
            // Para o dash, usa a última direção
            if (isDashing && !right && !left && !up && !down) {
                dx = lastDirectionX * currentSpd;
                dy = lastDirectionY * currentSpd;
            }

            moveWithCollision(dx, dy);
    }

    if (isDashing) {
        dashTime--;
        if (dashTime <= 0) {
            isDashing = false;
        }
        
    }
}

    private void moveWithCollision(int dx, int dy){
        int stepX = dx < 0 ? -1 : 1; 
        int stepY = dy < 0 ? -1 : 1; 
        int absDx = Math.abs(dx); 
        int absDy = Math.abs(dy); 
    

    for(int i = 0; i < absDx; i++){
        int nextX = x + stepX;
        if(isPositionValid(nextX, y)){
            x = nextX;
        }else{
            break;
          }
        }
        for(int i = 0; i < absDy; i++){
            int nextY = y + stepY;
            if(isPositionValid(x, nextY)){
                y = nextY;
            }else{
                break;
              }
            }
    }

    private boolean isPositionValid(int newX,int newY){
        if (newX < 0 || newX + width > 800 || newY < 0 || newY + height > 800) {
            return false;
        }
        Rectangle nextPos = new Rectangle(newX, newY, width, height);
        for(Blocks block : World.blocos){
            if(nextPos.intersects(block)){
                int overlapX = Math.min(nextPos.x + nextPos.width, block.x + block.width) -
                               Math.max(nextPos.x, block.x);
                int overlapY = Math.min(nextPos.y + nextPos.height, block.y + block.height) -
                               Math.max(nextPos.y, block.y);

                if(overlapX < 2 && overlapY < 2){
                    continue;
                }  
                return false;
            }
        }
        return true;
    }

    public void startDash(){
        if(!isDashing && (lastDirectionX != 0 || lastDirectionY != 0|| right || left || up || down)){
            isDashing = true;
            dashTime = dashDuration;
        }

        
    }

    public void render(Graphics g){
        //g.setColor(Color.blue);
        //g.fillRect(x, y, width, height);
        g.drawImage(Spritesheet.player_front, x, y,32,32,null);
    }

}

