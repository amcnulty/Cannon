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
    
    // Cannonball sprites
    public static Sprite basic_cannonball = new Sprite("/textures/projectiles/basic_cannonball.png");
    public static Sprite windup_cannonball = new Sprite("/textures/projectiles/windup_cannonball.png");
    public static Sprite master_cannonball = new Sprite("/textures/projectiles/masterball_cannonball.png");
    public static Sprite turtle_cannonball = new Sprite("/textures/projectiles/turtle_shell_cannonball.png");
    
    // Cannon sprites
    public static Sprite basic_cannon = new Sprite("/textures/cannons/basic_cannon.png");
    
    public static Sprite basic_ground_click = new Sprite(16, 16, 0xff0000);
    public static Sprite reload_bar = new Sprite(100, 10, 0xababab);
    public static Sprite projectile_selection = new Sprite("/textures/mis/projectile_selection.png");
    public static Sprite brick_5story_building = new Sprite("/textures/buildings/brick_5story_building.png");
    public static Sprite muzzle_flash = new Sprite("/textures/mis/muzzle_flash.png");
    
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
    
    public Sprite(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
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
    
    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
        for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
            for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
                for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
                    for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
                        int xo = x + xp * sheet.SPRITE_WIDTH;
                        int yo = y + yp * sheet.SPRITE_HEIGHT;
                        pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_WIDTH);
            }
        }
        return sprites;
    }
}
