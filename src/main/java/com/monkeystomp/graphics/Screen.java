/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

import java.util.Random;

/**
 *
 * @author Aaron
 */
public class Screen {
    
    private Random random;
    private int width, height;
    private final int TOP_OF_VIEW;
    public int[] pixels;
    
    public Screen(int width, int height, int topOfViewArea) {
        this.width = width;
        this.height = height;
        this.TOP_OF_VIEW = topOfViewArea;
        pixels = new int[width * height];
        random = new Random();
    }
    
    int randomColor;
    String randomHexCode = "";
    String hexValues = "0123456789abcdef";
    public void RandomBackgroundColor() {
        for (int i = 0; i < 6; i++) {
            randomHexCode += hexValues.charAt(random.nextInt(16));
        }
        randomColor = Integer.parseInt(randomHexCode, 16);
        System.out.println(randomHexCode);
        randomHexCode = "";
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = randomColor;
        }
    }
    
    public void renderSprite(int xp, int yp, Sprite sprite) {
//        if (fixed) {
//            xp -= xOffset;
//            yp -= yOffset;
//        }
        for (int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int xa = x + xp;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                int col = sprite.pixels[x + y * sprite.getWidth()];
                if (col != 0xffff00ff) pixels[xa + ya * width] = col; 
            }
        }
        
    }
    
    public void renderReloadBar(int width) {
        int col;
        if (width == 100) {
            col = 0x00ff00;
        }
        else col = 0xff0000;
        for (int y = 27; y < 10 + 27; y++) {
            for (int x = 160; x < width + 160; x++) {
                pixels[x + y * this.width] = col;
            }
        }
    }
    
    public void renderLevel(int backgroundColor, int groundColor) {
        for (int y = TOP_OF_VIEW; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = backgroundColor;
            }
        }
//        for (int y = (int)(height * 2 / 3); y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                pixels[x + y * width] = groundColor;
//            }
//        }
    }
}
