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
public class WindupCannonball extends Projectile {
    
    public WindupCannonball() {
        sprite = Sprite.windup_cannonball;
        damage = Projectile.WINDUPCANNONBALL_DAMAGE;
        areaOfEffect = Projectile.WINDUPCANNONBALL_AREA;
        particleAmount = 130;
    }
    
    private double getRandomForce() {
        return (20 + (random.nextDouble() * 45));
    }
    
    private double getRandomAngle() {
        return (70 + (random.nextDouble() * 30));
    }
    
    private int getColor() {
        return 0x111111;
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
