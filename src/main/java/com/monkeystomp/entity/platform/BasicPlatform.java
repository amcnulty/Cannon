/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.platform;

import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
class BasicPlatform extends Platform {

    public BasicPlatform() {
        x = 50;
        y = 180;
        hitPointsMax = 3000;
        hitPoints = hitPointsMax;
        sprite = Sprite.master_cannonball;
    }
    
    public void damagePlatform(int damage) {
        hitPoints -= damage;
    }
    
    @Override
    public void update() {
    }
    
}
