/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.cannon;

import com.monkeystomp.entity.mob.projectiles.Projectile;
import com.monkeystomp.graphics.Sprite;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author Aaron
 */
public class BasicCannon extends Cannon {
    
    public BasicCannon() {
        x = 30;
        y = 120;
        barrelX = x + 32;
        barrelY = y;
        reloadTime = 1000000000;
        sprite = Sprite.basic_cannon;
        loadedProjectile = Projectile.BASICCANNONBALL;
        loadSounds();
    }
    
    private void loadSounds() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Cannon.class.getResource("/audio/sfx/tank_firing.wav"));
            firingSound = AudioSystem.getClip();
            firingSound.open(ais);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could Not Load Sound Effects In Basic Cannon Class!");
        }
    }
    
    @Override
    public void requestFireCannon() {
        if (readyToFire) {
            level.addProjectile(barrelX, barrelY);
            lastTime = System.nanoTime();
            readyToFire = false;
            firingSound.stop();
            firingSound.setFramePosition(0);
            firingSound.start();
        }
    }
    
    @Override
    public void update() {
        if (!readyToFire) {
            if (System.nanoTime() - lastTime >= reloadTime) readyToFire = true;
        }
    }
    
}
