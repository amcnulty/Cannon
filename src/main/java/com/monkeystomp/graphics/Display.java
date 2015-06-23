/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

import com.monkeystomp.entity.cannon.Cannon;
import com.monkeystomp.controls.Keyboard;
import com.monkeystomp.controls.Mouse;
import com.monkeystomp.controls.ToolBar;
import com.monkeystomp.entity.platform.Platform;
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
    public static final int SCREEN_WIDTH = 416; 
    public static final int SCREEN_HEIGHT = SCREEN_WIDTH * 9 / 16;
    // The frame is scaled up by this factor.
    public static final int SCALE = 3;
    //The bottom edge of the toolbar
    private static final int TOOLBAR_BOTTOM_EDGE = 50;
    // Object used to describe the size of the JFrame.
    Dimension size;
    // The window frame.
    public JFrame frame;
    // The Title of the frame.
    public static String title = "Aaron's Cannon Game Pre-Alpha v0.08";
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
    // Used to update and render a specific cannon
    public Cannon cannon;
    // Used to update and render a specific platform
    public Platform platform;
    // Allows access to mouse and keyboard input.
    private Keyboard key;
    private Mouse mouse;
    
    public Display () {
        size = Toolkit.getDefaultToolkit().getScreenSize();
        size.width = SCREEN_WIDTH * SCALE;
        size.height = SCREEN_HEIGHT * SCALE;
        setPreferredSize(size);
        frame = new JFrame();
        image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, TOOLBAR_BOTTOM_EDGE);
        level = Level.grassLevel;
        key = new Keyboard();
        mouse = new Mouse();
        toolbar = new ToolBar(SCREEN_WIDTH, SCREEN_HEIGHT, SCALE, TOOLBAR_BOTTOM_EDGE, this, key);
        changeLevel(Level.grassLevel);
        initGame(Cannon.basicCannon, Platform.basicPlatform);
        gameState = GAME_RUNNING;
        
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    
    public void initGame(Cannon cannon, Platform platform) {
        this.cannon = cannon;
        this.platform = platform;
        level.init(cannon, platform);
        toolbar.init(cannon);
        cannon.init(level);
        platform.init(level);
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
                platform.update();
                level.update();
                toolbar.update();
                cannon.update();
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
        platform.render(screen);
        cannon.render(screen);
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
