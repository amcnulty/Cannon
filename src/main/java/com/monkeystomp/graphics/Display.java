/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

import com.monkeystomp.controls.ToolBar;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import com.monkeystomp.level.Level;
import javax.swing.JFrame;

/**
 *
 * @author Aaron
 */
public class Display extends Canvas implements Runnable {
    // Varialbes for the JFrame and screen size.
    // Width and Height of the screen before being scaled up by scale.
    public int width, height;
    // The frame is scaled up by this factor.
    private static int scale = 3;
    // Object used to describe the size of the JFrame.
    Dimension size;
    // The window frame.
    public JFrame frame;
    // The Title of the frame.
    public static String title = "Aaron's Cannon Game Pre-Alpha v0.01";
    // Display Thread variables
    // Set to true when thread is started. Set to false when game is stoped.
    private boolean running = false;
    // The thread running the game loop on the Canvas.
    private Thread displayThread;
    // Image and screen rendering variables.
    // A handle on the screen class to recive pixel information.
    private Screen screen;    
    // The image that is being rendered to the screen.
    private BufferedImage image;
    // Pixels to be loaded to the BufferedImage.
    private int[] pixels;
    // Gamestate Variables
    private static final int GAME_RUNNING = 1;
    private static final int GAME_PAUSED = 2;
    // The variable that is checked by the render and update methods.
    private int gameState;
    // Game element variables.
    ToolBar toolbar;
    Level level;
    
    public Display () {
        size = Toolkit.getDefaultToolkit().getScreenSize();
//        width = (int)(size.getWidth() * .3);
//        height = (int)(size.getHeight() * .3);
        width = 420;
        height = (width * 9 / 16); 
        size.width = width * scale;
        size.height = height * scale;
        setPreferredSize(size);
        frame = new JFrame();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(width, height, (int) (.18 * height));
        gameState = GAME_RUNNING;
        level = Level.randomLevel;
        toolbar = new ToolBar(width, height, (int) (.18 * height));
    }
    
    public void start() {
        running = true;
        displayThread = new Thread(this, "Display");
        displayThread.start();
    }
    
    public void stop() {
        if (running) {
            running = false;
        }
        try {
            displayThread.join();
        }
        catch (InterruptedException e) {
            System.err.println("The thread was interrupted while trying thread.join() in stop method");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frame.setTitle(title + "  |  " + updates + " ups " + frames + " fps ");
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }
    
    private void update() {
        switch (gameState) {
            case GAME_RUNNING:
                toolbar.update();
                
                break;
            case GAME_PAUSED:
                
                break;
        }
    }
    
    private void render() {
        BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }
        toolbar.render(screen);
        level.render(screen);
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
