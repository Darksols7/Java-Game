package game.ZoeyARaposa;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

    public static BufferedImage spritesheet;

    public static BufferedImage player_front;

    public Spritesheet(){
        try{
            spritesheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("graficos/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        player_front = Spritesheet.getSprite(64, 0, 32, 32);
    }

    public static BufferedImage getSprite(int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }

}
