/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

import com.monkeystomp.controls.Keyboard;
import com.monkeystomp.controls.Mouse;
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
    // Width and Height of the screen before being scaled up by SCALE.
    public int width = 416; 
    public int height = width * 9 / 16;
    // The frame is scaled up by this factor.
    public static final int SCALE = 4;
    //The bottom edge of the toolbar
    private static final int TOOLBAR_BOTTOM_EDGE = 50;
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
    // The ToolBar class is incharge of the toolbar at the top of the screen.
    private ToolBar toolbar;
    // The Level class is used to control the level this class is updating and rendering.
    public Level level;
    // Allows access to mouse and keyboard input.
    private Keyboard key;
    private Mouse mouse;
    
    public Display () {
        size = Toolkit.getDefaultToolkit().getScreenSize();
        size.width = width * SCALE;
        size.height = height * SCALE;
        setPreferredSize(size);
        frame = new JFrame();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(width, height, TOOLBAR_BOTTOM_EDGE);
        level = Level.grassLevel;
        key = new Keyboard();
        mouse = new Mouse();
        toolbar = new ToolBar(width, height, SCALE, TOOLBAR_BOTTOM_EDGE, this);
        gameState = GAME_RUNNING;
        
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        changeLevel(Level.grassLevel);
    }
    
    public void changeLevel(Level level) {
        this.level.stopMusic();
        this.level = level;
        this.level.playMusic();
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
                level.update();
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
        level.render(screen);
        toolbar.render(screen);
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
