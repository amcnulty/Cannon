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
    
    // Mob sprites
    public static final Sprite policeman_down_standing = new Sprite(16, 0, 0, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_right_standing = new Sprite(16, 0, 1, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_up_standing = new Sprite(16, 0, 2, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_left_standing = new Sprite(16, 2, 3, SpriteSheet.policeman_sprite_sheet);
    
    public static final Sprite policeman_down_walking_1 = new Sprite(16, 1, 0, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_right_walking_1 = new Sprite(16, 1, 1, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_up_walking_1 = new Sprite(16, 1, 2, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_left_walking_1 = new Sprite(16, 1, 3, SpriteSheet.policeman_sprite_sheet);
    
    public static final Sprite policeman_down_walking_2 = new Sprite(16, 2, 0, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_right_walking_2 = new Sprite(16, 2, 1, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_up_walking_2 = new Sprite(16, 2, 2, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_left_walking_2 = new Sprite(16, 0, 3, SpriteSheet.policeman_sprite_sheet);
    
    public static final Sprite policeman_left_attacking_1 = new Sprite(16, 2, 4, SpriteSheet.policeman_sprite_sheet);
    public static final Sprite policeman_left_attacking_2 = new Sprite(16, 1, 4, SpriteSheet.policeman_sprite_sheet);
    
    // Cannonball sprites
    public static Sprite basic_cannonball = new Sprite("/textures/projectiles/basic_cannonball.png");
    public static Sprite windup_cannonball = new Sprite("/textures/projectiles/windup_cannonball.png");
    public static Sprite master_cannonball = new Sprite("/textures/projectiles/masterball_cannonball.png");
    public static Sprite turtle_cannonball = new Sprite("/textures/projectiles/turtle_shell_cannonball.png");
    
    // Cannon sprites
    public static Sprite basic_cannon = new Sprite("/textures/cannons/basic_cannon.png");
    public static Sprite basic_cannon_high = new Sprite("/textures/cannons/basic_cannon_high.png");
    public static Sprite basic_cannon_low = new Sprite("/textures/cannons/basic_cannon_low.png");
    
    // Platform sprites
    public static final Sprite woodPlatform = new Sprite("/textures/platforms/wood_platform.png");
    
    // Mouse click sprites
    public static Sprite basic_ground_click = new Sprite(16, 16, 0xff0000);
    public static final Sprite ground_click1 = new Sprite(32, 0, 0, SpriteSheet.mouse_click_sheet);
    public static final Sprite ground_click2 = new Sprite(32, 1, 0, SpriteSheet.mouse_click_sheet);
    public static final Sprite ground_click3 = new Sprite(32, 2, 0, SpriteSheet.mouse_click_sheet);
    public static final Sprite ground_click4 = new Sprite(32, 0, 1, SpriteSheet.mouse_click_sheet);
    public static final Sprite ground_click5 = new Sprite(32, 1, 1, SpriteSheet.mouse_click_sheet);
    public static final Sprite ground_click6 = new Sprite(32, 2, 1, SpriteSheet.mouse_click_sheet);
    
    public static Sprite reload_bar = new Sprite(100, 10, 0xababab);
    public static Sprite projectile_selection = new Sprite("/textures/mis/projectile_selection.png");
    public static Sprite muzzle_flash = new Sprite("/textures/mis/muzzle_flash.png");
    
    // Brick five story building sprites
    public static Sprite brick_5story_building = new Sprite("/textures/buildings/brick_5story_building.png");
    public static Sprite brick_5Stroy_building_low_damage = new Sprite("/textures/buildings/brick_5story_building_low_damage.png");
    public static Sprite brick_5Stroy_building_medium_damage = new Sprite("/textures/buildings/brick_5story_building_medium_damage.png");
    public static Sprite brick_5Stroy_building_high_damage = new Sprite("/textures/buildings/brick_5story_building_high_damage.png");
    public static Sprite brick_5story_building_destroyed = new Sprite("/textures/buildings/brick_5story_building_destroyed.png");
    
    // Temporary pause window sprite
    public static final Sprite pause_window = new Sprite(100, 100, 0xff0000);
    
    // Start Screen Background sprite
    public static final Sprite start_screen_background = new Sprite("/backgrounds/cannon.jpg");
    
    /**
     * Use this constructor to get a square sprite from a sprite sheet.
     * @param size - Edge length of the square sprite.
     * @param xloc - X location of the sprite on the sprite sheet.
     * @param yloc - Y location of the sprite on the sprite sheet.
     * @param sheet - The sprite sheet that is being referenced.
     */
    public Sprite(int size, int xloc, int yloc, SpriteSheet sheet) {
        this.width = size;
        this.height = size;
        pixels = new int[size * size];
        for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
            for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
                pixels[x + y * this.width] = sheet.pixels[(x + (size * xloc)) + (y +(size * yloc)) * sheet.getWidth()];
            }
        }
    }
    
    /**
     * Use this constructor to load a single sprite directly from a file.
     * @param path - The local path location of the resource.
     */
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
    
    /**
     * 
     * @return The array of pixel data for this sprite.
     */
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
