/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.mob.projectiles;

import com.monkeystomp.entity.particle.Particle;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
public class BasicCannonball extends Projectile {
    
    public BasicCannonball() {
        sprite = Sprite.basic_cannonball;
        damage = Projectile.BASICCANNONBALL_DAMAGE;
        areaOfEffect = Projectile.BASICCANNONBALL_AREA;
        particleAmount = 50;
    }
    
    private double getRandomForce() {
        return (8 + (random.nextDouble() * 45));
    }
    
    private double getRandomAngle() {
        return (70 + (random.nextDouble() * 30));
    }
    
    private int getColor() {
        return 0xffffff;
    }
    
    @Override
    public void update() {
        if (xd >= endingX) {
            remove();
            // generate an array of particles new Particle(double startingX, double startingY, double force, double angle, int angle);
            for (int i = 0; i < particleAmount; i++) {
                level.addParticle(new Particle(endingX, endingY, getRandomForce(), getRandomAngle(), getColor()));
            }
            // play explosion sound!
            Thread audioClipThread = new Thread("Audio Clip") {
                public void run() {
                    try {
                        AudioInputStream ais = AudioSystem.getAudioInputStream(Projectile.class.getResource("/audio/sfx/explosions/basic_explosion.wav"));
                        explosion = AudioSystem.getClip();
                        explosion.open(ais);
                        ais.close();
                        explosion.start();
                        explosion.addLineListener((LineEvent e) -> {
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
            // send damage information to the level
        }
        else {
            xd = ((anim / 15) * (force * Math.cos(angle)) + startingX);
            yd = (16 * Math.pow((anim / 15), 2.0)) - ((anim / 15) * (force * Math.sin(angle))) + startingY;
            anim++;
        }
    }
    
    @Override
    public void render(Screen screen) {
        screen.renderSprite((int)xd - (sprite.getWidth() / 2), (int)yd - (sprite.getHeight() / 2), sprite);
    }
    
}
