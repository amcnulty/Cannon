/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.mob.projectiles;

import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
public class MasterCannonball extends Projectile {
    
    private int force = 120 - random.nextInt(51);
    private double angle = Math.toRadians(65.0 - random.nextInt(36));
    public static final int FIRE_RATE = 1;
    
    public MasterCannonball(int x, int y) {
        super(x, y);
        sprite = Sprite.master_cannonball;
        damage = 400;
        areaOfEffect = 25;
    }
    
    double anim = 0;
    @Override
    public void update() {
        //System.out.println("UPDATING BASIC CANNONBALL anim: " + anim + " X: " + xd + " Y: " + yd);
        if (anim > 90) {
            anim = 0;
            remove();
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
