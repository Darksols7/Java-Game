package com.zoeyaraposa;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 640, HEIGHT = 480;
    public static int SCALE = 3;
    public Player player;
    public World world;

    public Main(){
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        new Spritesheet();
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
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
        
        world.render(g);

        player.render(g);

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
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS:" + frames);
                frames = 0;
                timer+=1000;
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
        
    }

}
