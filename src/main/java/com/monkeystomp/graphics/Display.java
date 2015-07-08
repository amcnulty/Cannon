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
import com.monkeystomp.menus.PauseWindow;
import com.monkeystomp.entity.platform.Platform;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import com.monkeystomp.level.Level;
import com.monkeystomp.menus.StartScreen;
import java.awt.Cursor;
import java.awt.Point;
import java.io.IOException;
import javax.imageio.ImageIO;
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
    private static final int START_SCREEN = 0;
    private static final int GAME_RUNNING = 1;
    private static final int GAME_PAUSED = 2;
    // The variable that is checked by the render and update methods.
    private int gameState;
    // Game element variables.
    // The ToolBar class is incharge of the toolbar at the top of the screen.
    private ToolBar toolbar;
    // The Level class is used to control the level this class is updating and rendering.
    public Level level;
    // The first screen you see when you start the game.
    public StartScreen startScreen;
    // Used to update and render a specific cannon
    public Cannon cannon;
    // Used to update and render a specific platform
    public Platform platform;
    // Allows access to mouse and keyboard input.
    private Keyboard key;
    private Mouse mouse;
    // The window that comes up when game is paused.
    private PauseWindow pauseWindow;
    // Cursor variables
    private Cursor beerBottle;
    private static final String BEER_BOTTLE_POINTER_NAME = "Beer Bottle Pointer";
    private static final int BEER_BOTTLE_POINTER = 0;
    private Cursor torch;
    private static final String TORCH_POINTER_NAME = "Torch Pointer";
    private static final int TORCH_POINTER = 1;
    private Cursor flameSword;
    private static final String FLAME_SWORD_POINTER_NAME = "Flame Sword Pointer";
    private static final int FLAME_SWORD_POINTER = 2;
    
    public Display () {
        size = Toolkit.getDefaultToolkit().getScreenSize();
        size.width = SCREEN_WIDTH * SCALE;
        size.height = SCREEN_HEIGHT * SCALE;
        setPreferredSize(size);
        frame = new JFrame();
        loadCursors();
        image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT, TOOLBAR_BOTTOM_EDGE);
        key = new Keyboard();
        mouse = new Mouse();
        startScreen = new StartScreen();
        level = Level.grassLevel;
        pauseWindow = new PauseWindow();
        toolbar = new ToolBar(SCREEN_WIDTH, SCREEN_HEIGHT, SCALE, TOOLBAR_BOTTOM_EDGE, this, key);
        //changeLevel(Level.grassLevel);
        //initGame(Cannon.basicCannon, Platform.basicPlatform);
        gameState = START_SCREEN;
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    
    /**
     * This method is called by the constructor to load the custom cursors on startup.
     * Currently this sets the default cursor to the torch.
     */
    private final void loadCursors() {
        // The raw image.
        BufferedImage cursorImage = null;
        // The image after alpha checking.
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        // Load beer bottle cursor.
        try {
            cursorImage = ImageIO.read(Display.class.getResource("/textures/pointers/beer_bottle_pointer.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load beer bottle mouse pointer image");
        }
        if (cursorImage != null) {
            for (int y = 0; y < cursor.getWidth(); y++) {
                for (int x = 0; x < cursor.getHeight(); x++) {
                    if (cursorImage.getRGB(x, y) != 0xffff00ff) {
                        cursor.setRGB(x, y, cursorImage.getRGB(x, y));
                    }
                }
            }
        }
        beerBottle = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), BEER_BOTTLE_POINTER_NAME);
        // Load torch cursor
        try {
            cursorImage = ImageIO.read(Display.class.getResource("/textures/pointers/torch_pointer.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load torch mouse pointer image");
        }
        if (cursorImage != null) {
            for (int y = 0; y < cursor.getWidth(); y++) {
                for (int x = 0; x < cursor.getHeight(); x++) {
                    if (cursorImage.getRGB(x, y) != 0xffff00ff) {
                        cursor.setRGB(x, y, cursorImage.getRGB(x, y));
                    }
                }
            }
        }
        torch = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), TORCH_POINTER_NAME);
        // Load flame sword cursor
        try {
            cursorImage = ImageIO.read(Display.class.getResource("/textures/pointers/flame_sword_pointer.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load flame sword mouse pointer image");
        }
        if (cursorImage != null) {
            for (int y = 0; y < cursor.getWidth(); y++) {
                for (int x = 0; x < cursor.getHeight(); x++) {
                    if (cursorImage.getRGB(x, y) != 0xffff00ff) {
                        cursor.setRGB(x, y, cursorImage.getRGB(x, y));
                    }
                }
            }
        }
        flameSword = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), FLAME_SWORD_POINTER_NAME);
        frame.getContentPane().setCursor(torch);
    }
    
    public void setCursor(int type) {
        switch (type) {
            case BEER_BOTTLE_POINTER:
                frame.getContentPane().setCursor(beerBottle);
                break;
            case TORCH_POINTER:
                frame.getContentPane().setCursor(torch);
                break;
            case FLAME_SWORD_POINTER:
                frame.getContentPane().setCursor(flameSword);
                break;
        }
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
        key.update();
        switch (gameState) {
            case START_SCREEN:
                startScreen.update();
                if (startScreen.startGame) {
                    changeLevel(Level.grassLevel);
                    initGame(Cannon.basicCannon, Platform.basicPlatform);
                    gameState = GAME_RUNNING;
                }
                break;
            case GAME_RUNNING:
                if (key.escape && !key.checked) {
                    key.checked = true;
                    gameState = GAME_PAUSED;
                }
                platform.update();
                level.update();
                toolbar.update();
                cannon.update();
                break;
            case GAME_PAUSED:
                pauseWindow.update();
                if (key.escape && !key.checked) {
                    key.checked = true;
                    gameState = GAME_RUNNING;
                }
                break;
        }
    }
    
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        switch (gameState) {
            case START_SCREEN:
                startScreen.render(screen);
                break;
            case GAME_RUNNING:
                level.render(screen);
                toolbar.render(screen);
                platform.render(screen);
                cannon.render(screen);
                break;
            case GAME_PAUSED:
                pauseWindow.render(screen);
                break;
        }
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
}
