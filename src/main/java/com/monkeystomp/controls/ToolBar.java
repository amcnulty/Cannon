/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.controls;

import com.monkeystomp.entity.cannon.Cannon;
import com.monkeystomp.entity.mob.projectiles.Projectile;
import com.monkeystomp.graphics.Display;
import com.monkeystomp.graphics.Font;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;

/**
 *
 * @author Aaron
 */
public class ToolBar {
    
    // Dimensions of the screen.
    private int width, height;
    // The scale factor of the window.
    private int scale;
    // Bottom edge of the toolbar on the screen.
    public static int TOOLBAR_BOTTOM_EDGE;
    // The currently selected shell;
    public static int selected_shell = Projectile.BASICCANNONBALL;
    // Handle for the Display class.
    private Display display;
    // Used to see keyboard events.
    private Keyboard key;
    // Mouse possition adjusted for SCALE.
    private int mouseX, mouseY;
    // True if toolbar is showing false if it is hidden.
    private boolean showing = true;
    
    // The sprites of this class
    // The background sprite
    private Sprite background;
    // The info boxes that pop up next to the mouse
    private Sprite infoBoxSlotOne;
    private Sprite infoBoxSlotTwo;
    private Sprite infoBoxSlotThree;
    private Sprite infoBoxSlotFour;
    
    private boolean showInfoBox1 = false;
    private boolean showInfoBox2 = false;
    private boolean showInfoBox3 = false;
    private boolean showInfoBox4 = false;
    
    // Number of shade lines on the border.
    private static final int BORDER_SHADES = 5;
    // Color of the background.
    private static final int BACKGROUNDCOLOR = 0x9393FF;
    // Used to print text to the screen.
    private Font font;
    // Handle for the cannon
    private Cannon cannon;
    
    public ToolBar(int width, int height, int scale, int toolbarBottomEdge, Display display, Keyboard key) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        TOOLBAR_BOTTOM_EDGE = toolbarBottomEdge;
        this.display = display;
        this.key = key;
        font = new Font();
        background = new Sprite(0, 0, buildBackground(), width, TOOLBAR_BOTTOM_EDGE);
        infoBoxSlotOne = buildNameBoxSprite("AREA OF EFFECT = ", 3);
        infoBoxSlotTwo = buildNameBoxSprite(Projectile.TURTLECANNONBALLNAME, 3);
        infoBoxSlotThree = buildNameBoxSprite(Projectile.MASTERCANNONBALLNAME, 3);
        infoBoxSlotFour = buildNameBoxSprite(Projectile.WINDUPCANNONBALLNAME, 3);
    }
    
    private int[] buildBackground() {
        int[] result = new int[width * TOOLBAR_BOTTOM_EDGE];
        for (int i = 0; i < BORDER_SHADES; i++) {
            for (int y = 0; y < (TOOLBAR_BOTTOM_EDGE - (4 * i)); y++) {
                for (int x = 0; x < (width - (4 * i)); x++) {
                    result[(x + (2 * i)) + (y + (2 * i)) * width] = BACKGROUNDCOLOR - ((4 - i) * 328960);
                }
            }
        }
        return result;
    }
    
    private Sprite buildNameBoxSprite(String itemName, int rows) {
        rows--;
        int width = ((itemName.length() * 6) + 6);
        int[] pixels = new int[width * (13 + (8 * rows))];
        for (int y = 0; y < (13 + (8 * rows)); y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = 0xffFF384B;
            }
        }
        for (int i = 0; i < width; i++) {
            pixels[i] = 0;
            pixels[i + (12 + (8 * rows)) * width] = 0;
        }
        for (int i = 0; i < (13 + (8 * rows)); i++) {
            pixels[0 + i * width] = 0;
            pixels[(width * (i + 1)) - 1] = 0;
        }
        Sprite newSprite = new Sprite(pixels, width, (13 + (8 * rows)));
        return newSprite;
    }
    
    private void setMousePossition() {
        mouseX = Mouse.getMouseX() / Display.SCALE;
        mouseY = Mouse.getMouseY() / Display.SCALE;
    }
    
    private void checkMouseHover() {
        if (mouseX >= 10 && mouseX <= 26 && mouseY >= 22 && mouseY <= 38) {
            showInfoBox1 = true;
            showInfoBox2 = false;
            showInfoBox3 = false;
            showInfoBox4 = false;
            if (Mouse.getMouseB() == 1) selected_shell = Projectile.BASICCANNONBALL;
            
        }
        else if (mouseX >= 35 && mouseX <= 51 && mouseY >= 22 && mouseY <= 38) {
            showInfoBox1 = false;
            showInfoBox2 = true;
            showInfoBox3 = false;
            showInfoBox4 = false;
            if (Mouse.getMouseB() == 1) selected_shell = Projectile.TURTLESHELLCANNONBALL;
        }
        else if (mouseX >= 60 && mouseX <= 76 && mouseY >= 22 && mouseY <= 38) {
            showInfoBox1 = false;
            showInfoBox2 = false;
            showInfoBox3 = true;
            showInfoBox4 = false;
            if (Mouse.getMouseB() == 1) selected_shell = Projectile.MASTERCANNONBALL;
        }
        else if (mouseX >= 85 && mouseX <= 101 && mouseY >= 22 && mouseY <= 38) {
            showInfoBox1 = false;
            showInfoBox2 = false;
            showInfoBox3 = false;
            showInfoBox4 = true;
            if (Mouse.getMouseB() == 1) selected_shell = Projectile.WINDUPCANNONBALL;
        }
        else {
            showInfoBox1 = false;
            showInfoBox2 = false;
            showInfoBox3 = false;
            showInfoBox4 = false;
        }
    }
    
    public void init(Cannon cannon) {
        this.cannon = cannon;
    }
    
    //int anim = 0;
    public void update() {
        key.update();
        if (key.key1) selected_shell = Projectile.BASICCANNONBALL;
        else if (key.key2) selected_shell = Projectile.TURTLESHELLCANNONBALL;
        else if (key.key3) selected_shell = Projectile.MASTERCANNONBALL;
        else if (key.key4) selected_shell = Projectile.WINDUPCANNONBALL;
        else if (key.r) cannon.changeFiringAngle(1);
        else if (key.f) cannon.changeFiringAngle(2);
        else if (key.v) cannon.changeFiringAngle(3);
        setMousePossition();
        checkMouseHover();
    }
    
    public void render(Screen screen) {
        // Background of the toolbar
        screen.renderSprite(0, 0, background);
        // Shells section
        font.renderSuperSmallCharacters2(40, 10, "SHELLS", screen);
        screen.renderSprite(10, 22, Sprite.basic_cannonball);
        screen.renderSprite(35, 22, Sprite.turtle_cannonball);
        screen.renderSprite(60, 22, Sprite.master_cannonball);
        screen.renderSprite(85, 22, Sprite.windup_cannonball);
        switch (selected_shell) {
            case Projectile.BASICCANNONBALL:
                screen.renderSprite(6, 18, Sprite.projectile_selection);
                break;
            case Projectile.TURTLESHELLCANNONBALL:
                screen.renderSprite(31, 18, Sprite.projectile_selection);
                break;
            case Projectile.MASTERCANNONBALL:
                screen.renderSprite(56, 18, Sprite.projectile_selection);
                break;
            case Projectile.WINDUPCANNONBALL:
                screen.renderSprite(81, 18, Sprite.projectile_selection);
                break;
        }
        if (showInfoBox1) {
            screen.renderSprite(mouseX, mouseY + 20, infoBoxSlotOne);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 23, Projectile.BASICCANNONBALLNAME, screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 32, "DAMAGE = " + Integer.toString(Projectile.BASICCANNONBALL_DAMAGE), screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 41, "AREA OF EFFECT = " + Integer.toString(Projectile.BASICCANNONBALL_AREA), screen);
        }
        else if (showInfoBox2) {
            screen.renderSprite(mouseX, mouseY + 20, infoBoxSlotTwo);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 23, Projectile.TURTLECANNONBALLNAME, screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 32, "DAMAGE = " + Integer.toString(Projectile.TURTLESHELLCANNONBALL_DAMAGE), screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 41, "AREA OF EFFECT = " + Integer.toString(Projectile.TURTLESHELLCANNONBALL_AREA), screen);
        }
        else if (showInfoBox3) {
            screen.renderSprite(mouseX, mouseY + 20, infoBoxSlotThree);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 23, Projectile.MASTERCANNONBALLNAME, screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 32, "DAMAGE = " + Integer.toString(Projectile.MASTERCANNONBALL_DAMAGE), screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 41, "AREA OF EFFECT = " + Integer.toString(Projectile.MASTERCANNONBALL_AREA), screen);
        }
        else if (showInfoBox4) {
            screen.renderSprite(mouseX, mouseY + 20, infoBoxSlotFour);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 23, Projectile.WINDUPCANNONBALLNAME, screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 32, "DAMAGE = " + Integer.toString(Projectile.WINDUPCANNONBALL_DAMAGE), screen);
            font.renderSuperSmallCharacters2(mouseX + 3, mouseY + 41, "AREA OF EFFECT = " + Integer.toString(Projectile.WINDUPCANNONBALL_AREA), screen);
        }
    }
    
}
