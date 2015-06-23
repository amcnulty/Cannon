/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.projectiles;

import com.monkeystomp.entity.Entity;
import com.monkeystomp.graphics.Sprite;
import javax.sound.sampled.Clip;

/**
 *
 * @author Aaron
 */
public abstract class Projectile extends Entity {
    
    // Variables used to calculate phisics
    // The force the projectile is shot at
    protected double force;
    // The angle in radians the projectile is fired.
    protected double angle;
    
    // Variables used on impact
    // Number of particles the projectile causes
    protected int particleAmount;
    // Damage of the projectile
    protected int damage;
    // Area around the impact site that is effected
    protected int areaOfEffect;
    
    // Coordinate variables
    // These are the coordinates that the projectile originated at.
    protected int startingX, startingY;
    // These are the coordinates that the projectile is targeted at.
    protected int endingX, endingY;
    // The coordinates of the cannonball
    protected double xd, yd;
    // The coordinates of the collision
    protected int xCollision, yCollision;
    
    // Used to animate the projectile
    protected double anim;
    
    protected Clip explosion;
    
    // The sprite of the projectile.
    protected Sprite sprite;
    
    // The name constants of the projectile.
    public static final String BASICCANNONBALLNAME = "BASIC CANNONBALL";
    public static final String TURTLECANNONBALLNAME = "TURTLE SHELL CANNONBALL";
    public static final String MASTERCANNONBALLNAME = "MASTER CANNONBALL";
    public static final String WINDUPCANNONBALLNAME = "WIND-UP CANNONBALL";
    
    // Projectile reload time constants.
    public static final int BASICCANNONBALL_RELOAD_TIME = 500000000;
    public static final int TURTLESHELLCANNONBALL_RELOAD_TIME = 750000000;
    public static final int MASTERCANNONBALL_RELOAD_TIME = 850000000;
    public static final int WINDUPCANNONBALL_RELOAD_TIME = 1000000000;
    
    // Projectile damage constants.
    public static final int BASICCANNONBALL_DAMAGE = 100;
    public static final int TURTLESHELLCANNONBALL_DAMAGE = 180;
    public static final int MASTERCANNONBALL_DAMAGE = 275;
    public static final int WINDUPCANNONBALL_DAMAGE = 400;
    
    // Projectile area of effect constants.
    public static final int BASICCANNONBALL_AREA = 10;
    public static final int TURTLESHELLCANNONBALL_AREA = 18;
    public static final int MASTERCANNONBALL_AREA = 25;
    public static final int WINDUPCANNONBALL_AREA = 38;
    
    // Projectile type constants
    public static final int BASICCANNONBALL = 1;
    public static final int TURTLESHELLCANNONBALL = 2;
    public static final int MASTERCANNONBALL = 3;
    public static final int WINDUPCANNONBALL = 4;
    
    public void setPosition(int x, int y) {
        xd = x;
        yd = x;
        this.x = x;
        this.y = y;
        startingX = x;
        startingY = y;
    }
    
    public void setTrajectory(int endingX, int endingY, double force, double angle) {
        this.endingX = endingX;
        this.endingY = endingY;
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
            case TURTLESHELLCANNONBALL:
                return new TurtleShellCannonball();
            default:
                return new BasicCannonball();
        }
    }
    
    @Override
    public void update() {
    }
}