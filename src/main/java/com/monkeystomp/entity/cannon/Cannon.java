/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.cannon;

import com.monkeystomp.entity.Entity;
import com.monkeystomp.entity.projectiles.Projectile;
import com.monkeystomp.graphics.Font;
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
    public double angle;
    
    // Accuracy variables
    protected int accuracy;
    
    // Timer variables
    protected long reloadTime;
    protected long lastTime;
    protected long now;
    protected long muzzleFlashTimer;
    
    // The 32 x 32 image of the cannon
    protected Sprite sprite;
    
    // The muzzle flash sprite
    protected Sprite muzzleFlash;
    
    // True if showing muzzle flash
    protected boolean showMuzzleFlash = false;
    
    // Used to print words to the screen.
    protected Font font;
    
    // Message to be printed to the screen right above the reload bar.
    protected String fireStatusMessage = "";
    
    // The current projectile loaded in the cannon
    public int loadedProjectile  = Projectile.BASICCANNONBALL;
    
    // True if the cannon has been reloaded and ready to fire
    protected boolean readyToFire = true;
    
    // Used to draw the reload bar.
    protected double reloadBarPercent = 1.0;
    
    // Sound clip for the cannon firing
    protected Clip firingSound;
    
    // All types of cannons
    public static Cannon basicCannon = new BasicCannon();
    
    public void FireCannon() {
    }
    
    public int getAccuracy() {
        return accuracy;
    }
    
    public boolean readyToFire() {
        return readyToFire;
    }
    
    /**
     * Changes the current sprite of the cannon, the firing angle, and the barrel x and y location.
     * @param number - 1 if firing high 2 if firing middle 3 if firing low
     */
    public void changeFiringAngle(int number) {
    }
    
    public void update() {
    }
    
    public void render(Screen screen) {
        // Cannon image
        screen.renderSprite(x, y, sprite);
        // Reload bar and message
        screen.renderSprite(115, 27, Sprite.reload_bar);
        screen.renderReloadBar((int)(100 * reloadBarPercent));
        font.renderSuperSmallCharacters2(125, 15, fireStatusMessage, screen);
        font.renderSuperSmallCharacters2(339, 30, Integer.toString(level.getScore()), screen);
        if (level.getMultiplier() > 1) font.renderSuperSmallCharacters2(388, 16, "X" + Integer.toString(level.getMultiplier()), screen);
    }
    
}
