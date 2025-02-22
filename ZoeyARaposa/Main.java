package game.ZoeyARaposa;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 800, HEIGHT = 800;
    public Player player;
    public World world;

    public Main(){
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        player = new Player(500, 500);
        world = new World();

    }

    public void tick(){
        player.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        player.render(g);

        world.render(g);

        bs.show();

    }

    public static void main(String[] args){
        Main main = new Main();
        JFrame frame = new JFrame();

        frame.add(main);
        frame.setTitle("Zoey a Raposa");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);


        new Thread(main).start();
    }
    @Override
    public void run() {
        
        while (true) {
            System.out.println("Loop!");
            tick();
            render();
            try{
                Thread.sleep(1000/60);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

      }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D){
            player.right = true;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            player.left = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = true;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.startDash();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D){
            player.right = false;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            player.left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            player.isDashing = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

}
