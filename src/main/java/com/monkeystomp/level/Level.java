/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.graphics.Screen;
import java.util.Random;


/**
 *
 * @author aaron
 */
public class Level {
    
    public static final int EASY_DIFFICULTY = 1;
    
    public static Level randomLevel = new RandomLevel();
    
    protected int width, height;
    protected int difficulty;
    protected Random random;
    
    public Level() {
        random = new Random();
        generateLevel();
    }
    
    protected void generateLevel() {
    }
    
    public void render(Screen screen) {
    }
    
}
