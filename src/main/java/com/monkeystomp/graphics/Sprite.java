/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

/**
 *
 * @author Aaron
 */
public class Sprite {
    
    private int width, height;
    private int rawX, rawY;
    public int[] pixels;
    
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
