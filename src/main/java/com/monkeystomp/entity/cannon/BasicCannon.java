/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.cannon;

import com.monkeystomp.controls.ToolBar;
import com.monkeystomp.entity.projectiles.Projectile;
import com.monkeystomp.graphics.Font;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;

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
        angle = 45.0;
        reloadTime = 1000000000;
        accuracy = 30;
        font = new Font();
        sprite = Sprite.basic_cannon;
        muzzleFlash = Sprite.muzzle_flash;
    }
    
    @Override
    public void requestFireCannon() {
        if (readyToFire) {
            level.addProjectile(barrelX, barrelY);
            lastTime = System.nanoTime();
            readyToFire = false;
            showMuzzleFlash = true;
            muzzleFlashTimer = System.nanoTime();
            Thread audioClipThread = new Thread("Audio Clip") {
                public void run() {
                    try {
                        AudioInputStream ais = AudioSystem.getAudioInputStream(Cannon.class.getResource("/audio/sfx/tank_firing.wav"));
                        firingSound = AudioSystem.getClip();
                        firingSound.open(ais);
                        ais.close();
                        firingSound.start();
                        firingSound.addLineListener((LineEvent e) -> {
                            if (e.getType() == LineEvent.Type.STOP) {
                                e.getLine().close();
                            }
                        });
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            audioClipThread.start();
        }
    }
    
    public int getAccuracy() {
        return accuracy;
    }
    
    public void changeFiringAngle(int number) {
        switch (number) {
            case 1:
                sprite = Sprite.basic_cannon_high;
                angle = 66.0;
                barrelX = x + 23;
                barrelY = y - 3;
                break;
            case 2:
                sprite = Sprite.basic_cannon;
                angle = 45.0;
                barrelX = x + 32;
                barrelY = y;
                break;
            case 3:
                sprite = Sprite.basic_cannon_low;
                angle = 20.0;
                barrelX = x + 36;
                barrelY = y + 8;
                break;
        }
    }
    
    @Override
    public void update() {
        loadedProjectile = ToolBar.selected_shell;
        switch (loadedProjectile) {
            case Projectile.BASICCANNONBALL:
                reloadTime = Projectile.BASICCANNONBALL_RELOAD_TIME;
                break;
            case Projectile.TURTLESHELLCANNONBALL:
                reloadTime = Projectile.TURTLESHELLCANNONBALL_RELOAD_TIME;
                break;
            case Projectile.MASTERCANNONBALL:
                reloadTime = Projectile.MASTERCANNONBALL_RELOAD_TIME;
                break;
            case Projectile.WINDUPCANNONBALL:
                reloadTime = Projectile.WINDUPCANNONBALL_RELOAD_TIME;
                break;
        }
        fireStatusMessage = "READY TO FIRE";
        if (!readyToFire) {
            now = System.nanoTime();
            if (now - muzzleFlashTimer >+ 200000000) showMuzzleFlash = false;
            if (now - lastTime >= reloadTime) {
                readyToFire = true;
                reloadBarPercent = 1.0;
            }
            else {
                reloadBarPercent = (double)((now - lastTime)) / (double)reloadTime;
                fireStatusMessage = "-RELOADING-";
            }
        }
    }
    
    @Override
    public void render(Screen screen) {
        super.render(screen);
        if (showMuzzleFlash) {
            screen.renderSprite(barrelX - (muzzleFlash.getWidth() / 2), barrelY - (muzzleFlash.getHeight() / 2), muzzleFlash);
        }
    }
    
}
