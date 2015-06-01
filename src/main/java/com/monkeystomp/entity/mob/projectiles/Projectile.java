/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.mob.projectiles;

import com.monkeystomp.entity.mob.Mob;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
public class Projectile extends Mob {
    
    protected int damage;
    protected int areaOfEffect;
    protected int startingX, startingY;
    protected double xd, yd;
    
    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        xd = x;
        yd = y;
        this.startingX = x;
        this.startingY = y;
    }
    
    public void update() {
    }
    
    public void render(Screen screen) {
    }
    
}
