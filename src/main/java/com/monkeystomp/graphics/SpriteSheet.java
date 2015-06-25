/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron
 */
public class SpriteSheet {
    
    private String path;
    public final int SPRITE_WIDTH;
    public final int SPRITE_HEIGHT;
    private int width, height;
    public int[] pixels;
    
    public static SpriteSheet policeman_sprite_sheet = new SpriteSheet("/textures/characters/policeman_sprite_sheet.png", 3, 5);
    public static SpriteSheet mouse_click_sheet = new SpriteSheet("/textures/mis/mouse_click_sheet.png", 3, 2);
    
    public SpriteSheet(String path, int size) {
        this.path = path;
        SPRITE_WIDTH = size;
        SPRITE_HEIGHT = size;
        load();
    }
    
    /**
     * Use this constructor for sprite sheets with all the same size sprites.
     * @param path - The local path location of the resource.
     * @param width - Number of sprites horizontally.
     * @param height - Number of sprites vertically.
     */
    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        load();
        SPRITE_WIDTH = this.width / width;
        SPRITE_HEIGHT = this.height / height;
    }
    
    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load sprite sheet!!");
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int[] getPixels() {
        return pixels;
    }
    
}
