/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.mob;

import com.monkeystomp.entity.Entity;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public class Mob extends Entity {
    
    private Sprite sprite;
    private boolean walking;
    private boolean showingRed;
    private boolean attacking;
    private final int mobType;
    private int dir;
    private int anim;
    private int hitPoints;
    private int attackDamage;
    private int attackSpeed;
    private int showingRedTimer;
    private Sprite redSprite;
    private int points;
    
    // Corrdinates when the mob should start attacking.
    private int attackX, attackY;
    
    private ArrayList<int[]> destinations = new ArrayList<>();
    private int routePosition = 0;
    
    // Mob type constants
    public static final int POLICEMAN = 0;
    
    public Mob(int x, int y, int mobType) {
        this.x = x;
        this.y = y;
        this.mobType = mobType;
        sprite = Sprite.policeman_down_standing;
        switch(mobType) {
            case POLICEMAN:
                hitPoints = 25;
                attackDamage = 5;
                attackSpeed = 30;
                points = 15;
                break;
        }
    }
    
    public void setDestination(int x, int y) {
        int[] corrdinates = {x, y};
        destinations.add(corrdinates);
    }
    
    public void attackAtPoint(int x, int y) {
        attackX = x;
        attackY = y;
    }
    
    private void move(int xa, int ya) {
        if (xa > 0) dir = 1;
        if (xa < 0) dir = 3;
        if (ya > 0) dir = 2;
        if (ya < 0) dir = 0;
        this.x += xa;
        this.y += ya;
        walking = true;
    }
    
    private boolean collision(int xa, int ya) {
        return level.mobHere(xa, ya, x, y);
    }
    
    public boolean mobHere(int x, int y) {
        return x < this.x + sprite.getWidth() / 2 && x > this.x - sprite.getWidth() / 2 && y < this.y + sprite.getHeight() / 2 && y > this.y - sprite.getHeight() / 2;
    }
    
    public void damageMob(int damage) {
        hitPoints -= damage;
        showingRed = true;
        showingRedTimer = 0;
    }
    
    private void setSprite() {
        switch (mobType) {
            case (POLICEMAN):
            switch (dir) {
                case 0:
                    sprite = Sprite.policeman_up_standing;
                    if (walking && anim % 20 < 10) {
                        sprite = Sprite.policeman_up_walking_1;
                    }
                    else if (walking && anim % 20 >= 10) {
                        sprite = Sprite.policeman_up_walking_2;
                    }
                    break;
                case 1:
                    sprite = Sprite.policeman_right_standing;
                    if (walking && anim % 20 < 10) {
                        sprite = Sprite.policeman_right_walking_1;
                    }
                    else if (walking && anim % 20 >= 10) {
                        sprite = Sprite.policeman_right_walking_2;
                    }
                    break;
                case 2:
                    sprite = Sprite.policeman_down_standing;
                    if (walking && anim % 20 < 10) {
                        sprite = Sprite.policeman_down_walking_1;
                    }
                    else if (walking && anim % 20 >= 10) {
                        sprite = Sprite.policeman_down_walking_2;
                    }
                    break;
                case 3:
                    sprite = Sprite.policeman_left_standing;
                    if (walking && anim % 20 < 10) {
                        sprite = Sprite.policeman_left_walking_1;
                    }
                    else if (walking && anim % 20 >= 10) {
                        sprite = Sprite.policeman_left_walking_2;
                    }
                    else if (attacking && anim % 20 < 10) sprite = Sprite.policeman_left_attacking_1;
                    else if (attacking && anim % 20 >= 10) sprite = Sprite.policeman_left_attacking_2;
                    break;
            }
            break;
        }
    }
    
    public void update() {
        if (hitPoints <= 0) {
            level.increaseScore(points);
            System.out.println("Increasing Score");
            remove();
        }
        if (anim >= 60) anim = 0;
        else anim++;
        
        // Controls the movement of this mob.
        if (routePosition < destinations.size()) {
            if (destinations.get(routePosition)[0] > x) move(1, 0);
            else if (destinations.get(routePosition)[0] < x) move(-1, 0);
            if (destinations.get(routePosition)[1] > y) move(0, 1);
            else if (destinations.get(routePosition)[1] < y) move(0, -1);
            if (destinations.get(routePosition)[0] == x && destinations.get(routePosition)[1] == y) routePosition++;
        }
        else walking = false;
        
        // Sets sprite to whatever the current animation is.
        setSprite();
        
        // Controls when this mob will turn red.
        if (showingRed) {
            if (showingRedTimer >= 60) {
                showingRed = false;
                showingRedTimer = 0;
            }
            else {
                redSprite = new Sprite(0, 0, sprite.getPixels(), sprite.getWidth(), sprite.getHeight());
                for (int i = 0; i < sprite.getPixels().length; i++) {
                    if (redSprite.getPixels()[i] != 0xffff00ff) redSprite.getPixels()[i] = 0xff0000;
                }
                showingRedTimer++;
            }
        }
        
        if (x == attackX && y == attackY && anim % attackSpeed == 0) {
            attacking = true;
            level.damagePlatform(attackDamage);
            level.decreaseScore(2);
        }
    }
    
    public void render(Screen screen) {
        if (showingRed && redSprite != null) screen.renderSprite(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, redSprite);
        else screen.renderSprite(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite);
    }
    
}
