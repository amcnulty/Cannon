/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron
 */
class PurpleLevel extends Level {

    public PurpleLevel(String path) {
        super(path);
    }
    
    @Override
    protected void loadLevel(String path) {
        try {
            levelBackgroundImage = ImageIO.read(Level.class.getResource(path));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Level could not load background image!");
        }
    }
    
    @Override
    protected void generateLevel() {
        int[] pixels = new int[levelBackgroundImage.getWidth() * levelBackgroundImage.getHeight()];
        levelBackgroundImage.getRGB(0, 0, levelBackgroundImage.getWidth(), levelBackgroundImage.getHeight(), pixels, 0, levelBackgroundImage.getWidth());
        levelBackgroundSprite = new Sprite(0, 50, pixels, levelBackgroundImage.getWidth(), levelBackgroundImage.getHeight());
    }
    
    public void update() {
        
    }
    
    public void render(Screen screen) {
        screen.renderSprite(levelBackgroundSprite.getRawX(), levelBackgroundSprite.getRawY(), levelBackgroundSprite, false);
        screen.renderSprite(30, 120, Sprite.basic_cannon, false);
    }
    
}
