/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.entity.building;

import com.monkeystomp.entity.Entity;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
public abstract class Building extends Entity {
    
    protected Sprite sprite;
    
    protected int leftEdge, rightEdge, topEdge, bottomEdge;
    protected int hitPoints;
    
    public boolean buildingHere(int x, int y) {
        return false;
    }
    
    @Override
    public void render(Screen screen) {
        screen.renderSprite(leftEdge, topEdge, sprite);
    }
    
}
