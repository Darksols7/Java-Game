package game.ZoeyARaposa;

import java.awt.Color;
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

        if (right && World.isFree(x+spd, y)) {
            x+=currentSpd;
            lastDirectionX = 1;
            lastDirectionY = 0;
        }else if(left && World.isFree(x-spd, y)){
            x-=currentSpd;
            lastDirectionX = -1;
            lastDirectionY = 0;
        }
        if (up && World.isFree(x, y-spd)) {
            y-=currentSpd;
            lastDirectionX = 0;
            lastDirectionY = -1;
        }else if(down && World.isFree(x, y+spd)){
            y+=currentSpd;
            lastDirectionX = 0;
            lastDirectionY = 1;
        }

        if (isDashing && !right && !left && !up && !down) {
            moveWithCollision(lastDirectionX * currentSpd, lastDirectionY * currentSpd);
        }

        if(isDashing){
            dashTime--;
            if(dashTime <=0 ){
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
        if(World.isFree(nextX, y)){
            x = nextX;
        }else{
            break;
          }
        }
        for(int i = 0; i < absDy; i++){
            int nextY = y + stepY;
            if(World.isFree(x, nextY)){
                x = nextY;
            }else{
                break;
              }
            }
    }

    public void startDash(){
        if(!isDashing && (lastDirectionX != 0 || lastDirectionY != 0|| right || left || up || down)){
            isDashing = true;
            dashTime = dashDuration;
        }
    }

    public void render(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
        
    }

}

