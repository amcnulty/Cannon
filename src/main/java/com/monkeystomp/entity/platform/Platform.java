/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.platform;

import com.monkeystomp.entity.Entity;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
public abstract class Platform extends Entity {
    
    // The image of the platform.
    protected Sprite sprite;
    
    // How much health the platform has.
    protected int hitPoints;
    protected int hitPointsMax;
    
    public static Platform basicPlatform = new BasicPlatform();
    
    public void damagePlatform(int damage) {
    }
    
    @Override
    public void render(Screen screen) {
        screen.renderSprite(x, y, sprite);
        screen.renderSprite(300, 27, Sprite.reload_bar);
        screen.renderCannonHealth((int)(100 * ((double)hitPoints/(double)hitPointsMax)));
    }
}
