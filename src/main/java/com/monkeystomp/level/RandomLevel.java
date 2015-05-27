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
        difficulty = EASY_DIFFICULTY;
    }
    
    protected void generateLevel() {
        backgroundColor = random.nextInt();
        groundColor = random.nextInt();
        System.out.println("Background Color: ");
        System.out.print(Integer.toHexString(backgroundColor));
        System.out.print(" Ground Color: " + groundColor);
        
    }
    
    public void render(Screen screen) {
        screen.renderLevel(backgroundColor, groundColor);
    }
}
