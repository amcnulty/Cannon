/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.controls;

import com.monkeystomp.graphics.Display;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author aaron
 */
public class PauseWindow {
    
    Sprite sprite;
    private final int x, y;
    private int anim;
    
    public PauseWindow() {
        this.x = Display.SCREEN_WIDTH / 2;
        this.y = Display.SCREEN_HEIGHT / 2;
        sprite = Sprite.pause_window;
    }
    
    public void update() {
        if (anim == 59) {
            anim = 0;
            System.out.println("We Are Paused!!");
        }
        else anim++;
    }
    
    public void render(Screen screen) {
        screen.renderSprite(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite);
    }
    
}
