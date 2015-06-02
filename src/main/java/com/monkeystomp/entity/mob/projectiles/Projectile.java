/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.mob.projectiles;

import com.monkeystomp.entity.mob.Mob;

/**
 *
 * @author Aaron
 */
public abstract class Projectile extends Mob {
    
    protected double force;
    protected double angle;
    protected int damage;
    protected int areaOfEffect;
    protected int startingX, startingY;
    protected double xd, yd;
    
    public static final int BASICCANNONBALL = 0;
    public static final int WINDUPCANNONBALL = 1;
    public static final int MASTERCANNONBALL = 2;
    
    public Projectile() {
    }
    
    public void setPosition(int x, int y) {
        xd = x;
        yd = x;
        this.x = x;
        this.y = y;
        startingX = x;
        startingY = y;
    }
    
    public void setTrajectory(double force, double angle) {
        this.force = force;
        this.angle = Math.toRadians(angle);
    }
    
    public static Projectile getProjectile(int type) {
        switch (type) {
            case BASICCANNONBALL:
                return new BasicCannonball();
            case WINDUPCANNONBALL:
                return new WindupCannonball();
            case MASTERCANNONBALL:
                return new MasterCannonball();
            default:
                return new BasicCannonball();
        }
    }
    
}