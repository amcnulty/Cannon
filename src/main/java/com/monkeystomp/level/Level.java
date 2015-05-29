/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.sound.sampled.Clip;


/**
 *
 * @author aaron
 */
public class Level {
    
    public static final int EASY_DIFFICULTY = 1;
    
    public static Level randomLevel = new RandomLevel();
    public static Level grassLevel = new GrassLevel("/levels/grass_level.png");
    public static Level purpleLevel = new PurpleLevel("/levels/purple_ground_level.png");
    
    protected int width, height;
    protected int difficulty;
    protected Random random;
    protected BufferedImage levelBackgroundImage;
    protected Sprite levelBackgroundSprite;
    
    protected Clip backgroundMusic;
    
    public Level() {
        random = new Random();
        generateLevel();
    }
    
    public Level(String path) {
        random = new Random();
        loadLevel(path);
        generateLevel();
    }
    
    protected void loadLevel(String path) {
    }
    
    protected void generateLevel() {
    }
    
    public void stopMusic() {
        backgroundMusic.stop();
    }
    
    public void playMusic() {
        backgroundMusic.setFramePosition(0);
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void update() {
    }
    
    public void render(Screen screen) {
    }
    
}
