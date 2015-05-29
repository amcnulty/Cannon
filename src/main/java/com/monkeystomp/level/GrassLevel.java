/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron
 */
public class GrassLevel extends Level {
    
    public GrassLevel(String path) {
        super(path);
    }
    
    protected void loadLevel(String path) {
        try {
            levelBackgroundImage = ImageIO.read(Level.class.getResource(path));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Level could not load background image!");
        }
    }
    
    protected void generateLevel() {
        int[] pixels = new int[levelBackgroundImage.getWidth() * levelBackgroundImage.getHeight()];
        levelBackgroundImage.getRGB(0, 0, levelBackgroundImage.getWidth(), levelBackgroundImage.getHeight(), pixels, 0, levelBackgroundImage.getWidth());
        // randomise the grass pixels
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == 0xff446E0F) pixels[i] += (0x000100 * Math.abs(random.nextInt(40)));
        }
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == 0xff3F372A) pixels[i] += (0x000100 * random.nextInt(10)) + (0x010000 * random.nextInt(20));
        }
        levelBackgroundSprite = new Sprite(0, 50, pixels, levelBackgroundImage.getWidth(), levelBackgroundImage.getHeight());
    }
    
    public void update() {
        
    }
    
    public void render(Screen screen) {
        screen.renderSprite(0, 50, levelBackgroundSprite, false);
        screen.renderSprite(30, 120, Sprite.basic_cannon, false);
    }
    
}
