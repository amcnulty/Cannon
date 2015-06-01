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
public class Sprite {
    
    private int width, height;
    private int rawX, rawY;
    public int[] pixels;
    
    public static Sprite basic_cannon = new Sprite("/textures/cannons/basic_cannon.png");
    public static Sprite basic_cannonball = new Sprite("/textures/projectiles/basic_cannonball.png");
    public static Sprite windup_cannonball = new Sprite("/textures/projectiles/windup_cannonball.png");
    public static Sprite master_cannonball = new Sprite("/textures/projectiles/masterball_cannonball.png");
    public static Sprite basic_ground_click = new Sprite(16, 16, 0xff0000);
    
    public Sprite(String path) {
        try {
            BufferedImage image = ImageIO.read(Sprite.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Sprite(int rawX, int rawY, int[] pixels, int width, int height) {
        this.rawX = rawX;
        this.rawY = rawY;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            this.pixels[i] = pixels[i];
        }
    }
    
    public Sprite(int width, int height, int color) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color;
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
    
    public int getRawX() {
        return rawX;
    }
    
    public int getRawY() {
        return rawY;
    }
}
