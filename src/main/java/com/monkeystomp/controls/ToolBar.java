/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.controls;

import com.monkeystomp.graphics.Display;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import com.monkeystomp.level.Level;

/**
 *
 * @author Aaron
 */
public class ToolBar {
    
    // Dimensions of the screen.
    private int width, height;
    // Bottom edge of the toolbar on the screen.
    public static int TOOLBAR_BOTTOM_EDGE;
    // Handle for the Display class.
    Display display;
    // True if toolbar is showing false if it is hidden.
    private boolean showing = true;
    // The background sprite
    private Sprite background;
    // Number of shade lines on the border.
    private static final int BORDER_SHADES = 5;
    // Color of the background.
    private static final int BACKGROUNDCOLOR = 0x9393FF;
    
    public ToolBar(int width, int height, int toolbarBottomEdge, Display display) {
        this.width = width;
        this.height = height;
        TOOLBAR_BOTTOM_EDGE = toolbarBottomEdge;
        this.display = display;
        background = new Sprite(0, 0, buildBackground(), width, TOOLBAR_BOTTOM_EDGE);
    }
    
    private int[] buildBackground() {
        int[] result = new int[width * TOOLBAR_BOTTOM_EDGE];
        for (int i = 0; i < BORDER_SHADES; i++) {
            for (int y = 0; y < (TOOLBAR_BOTTOM_EDGE - (4 * i)); y++) {
                for (int x = 0; x < (width - (4 * i)); x++) {
                    result[(x + (2 * i)) + (y + (2 * i)) * width] = BACKGROUNDCOLOR - ((4 - i) * 328960);
                }
            }
        }
        return result;
    }
    
    int anim = 0;
    public void update() {
        if (anim == 6000) anim = 0;
        else anim++;
        if (anim % 120 == 0) {
            if (display.level.equals(Level.grassLevel)) display.level = Level.purpleLevel;
            else if (display.level.equals(Level.purpleLevel)) display.level = Level.randomLevel;
            else if (display.level.equals(Level.randomLevel)) display.level = Level.grassLevel;
        }
    }
    
    public void render(Screen screen) {
        screen.renderSprite(0, 0, background, false);
    }
    
}
