/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.graphics.Screen;

/**
 *
 * @author aaron
 */
class RandomLevel extends Level {
    
    private int backgroundColor;
    private int groundColor;
    

    public RandomLevel() {
        super();
    }
    
    protected void generateLevel() {
        backgroundColor = random.nextInt();
        groundColor = random.nextInt();
    }
    
    public void render(Screen screen) {
        screen.renderLevel(backgroundColor, groundColor);
    }
}
