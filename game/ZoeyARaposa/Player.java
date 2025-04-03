package game.ZoeyARaposa;

//import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle {
    public int spd = 6; 
    private int dashSpd = 14; 
    private int dashDuration = 10; 
    private int dashTime = 0; 
    public boolean right, up, down, left;
    public boolean isDashing = false;
    public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
    public int dir = right_dir;
    private int lastDirectionX = 0;
    private int lastDirectionY = 0;

    private int frames = 0, nextFrames = 5, index = 0, maxIndex = 1;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    private BufferedImage[] upPlayer;
    private BufferedImage[] downPlayer;

    public Player(int x, int y){
        super(x,y,32,32);

        rightPlayer = new BufferedImage[2];
        leftPlayer = new BufferedImage[2];
        upPlayer = new BufferedImage[2];
        downPlayer = new BufferedImage[2];

        rightPlayer[0] = Spritesheet.getSprite(96, 0, 32, 32); // Direita Caminhada 1
        rightPlayer[1] = Spritesheet.getSprite(128, 0, 32, 32); // Direita Caminhada 2
        leftPlayer[0] = Spritesheet.getSprite(160, 0, 32, 32); // Esquerda Caminhada 1
        leftPlayer[1] = Spritesheet.getSprite(192, 0, 32, 32); // Esquerda Caminhada 2
        upPlayer[0] = Spritesheet.getSprite(288, 0, 32, 32); // Cima Caminhada 1
        upPlayer[1] = Spritesheet.getSprite(256, 0, 32, 32); // Cima Caminhada 2
        downPlayer[0] = Spritesheet.getSprite(192, 32, 32, 32); // Baixo Caminhada 1
        downPlayer[1] = Spritesheet.getSprite(224, 32, 32, 32); // Baixo Caminhada 2
    }

    public void tick(){
        int currentSpd = isDashing ? dashSpd : spd;

        if (right || left || up || down || (isDashing && (lastDirectionX != 0 || lastDirectionY != 0))) {
            moved = false;
            int dx = 0, dy = 0;
            if (right) {
                moved = true;
                dir = right_dir;
                dx = currentSpd;
                lastDirectionX = 1;
                lastDirectionY = 0;
            } else if (left) {
                moved = true;
                dir = left_dir;
                dx = -currentSpd;
                lastDirectionX = -1;
                lastDirectionY = 0;
            }
            if (up) {
                moved = true;
                dir = up_dir;
                dy = -currentSpd;
                lastDirectionX = 0;
                lastDirectionY = -1;
            } else if (down) {
                moved = true;
                dir = down_dir;
                dy = currentSpd;
                lastDirectionX = 0;
                lastDirectionY = 1;
            }

            if(moved){
                frames++;
                if(frames == nextFrames){
                    frames = 0;
                    index++;
                    if(index > maxIndex){
                        index = 0;
                    }
                }
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
        if(dir == right_dir){
            g.drawImage(rightPlayer[index], x, y, 32, 32, null);
        } else if(dir == left_dir){
            g.drawImage(leftPlayer[index], x, y, 32, 32, null);
        } else if(dir == up_dir){
            g.drawImage(upPlayer[index], x, y, 32, 32, null);
        } else if(dir == down_dir){
            g.drawImage(downPlayer[index], x, y, 32, 32, null);
        }
    }

}