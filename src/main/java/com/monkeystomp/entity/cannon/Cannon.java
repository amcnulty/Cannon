/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.cannon;

import com.monkeystomp.entity.Entity;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import javax.sound.sampled.Clip;

/**
 *
 * @author Aaron
 */
public abstract class Cannon extends Entity {
    
    // This is the end of the barrel where projectiles will originate from.
    public int barrelX, barrelY;
    public double angle = 45.0;
    // reload timer variables
    protected long reloadTime;
    protected long lastTime;
    protected Sprite sprite;
    // The current projectile loaded in the cannon
    public int loadedProjectile;
    protected boolean readyToFire = true;
    
    protected Clip firingSound;
    
    public static Cannon basicCannon = new BasicCannon();
    
    public void requestFireCannon() {
    }
    
    public void update() {
    }
    
    public void render(Screen screen) {
        screen.renderSprite(x, y, sprite);
    }
    
}
