/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.cannon;

import com.monkeystomp.controls.ToolBar;
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
        reloadTime = 1000000000;
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
    
    @Override
    public void update() {
        loadedProjectile = ToolBar.selected_shell;
        fireStatusMessage = "READY TO FIRE";
        if (!readyToFire) {
            now = System.nanoTime();
            if (now - muzzleFlashTimer >+ 200000000) showMuzzleFlash = false;
            if (now - lastTime >= reloadTime) {
                readyToFire = true;
                reloadBarPercent = 1.0;
            }
            else {
                reloadBarPercent = (double)((now - lastTime) % reloadTime) / (double)reloadTime;
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
