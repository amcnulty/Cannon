/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.menus;

import com.monkeystomp.controls.ToolBar;
import com.monkeystomp.graphics.Display;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import com.monkeystomp.menus.buttons.*;
import com.monkeystomp.menus.commands.*;

/**
 *
 * @author aaron
 */
public class PauseWindow extends Menu{
    
    private final Sprite backgroundSprite;
    private final int x, y;
    public boolean goToMainMenu = false;
    public boolean resumeGame = false;
    
    public PauseWindow() {
        this.x = Display.SCREEN_WIDTH / 2;
        this.y = Display.SCREEN_HEIGHT / 2;
        backgroundSprite = new Sprite(0, 0, createBackgroundSprite(), 100, 100);
        addButtons();
    }
    
    private final void addButtons() {
        buttons.add(new ClickableButton(Display.SCREEN_WIDTH / 2, 95, "Resume", ClickableButton.SMALL_TEXT, Command.RESUME, this));
        buttons.add(new ClickableButton(Display.SCREEN_WIDTH / 2, 115, "Main Menu", ClickableButton.SMALL_TEXT, Command.MAIN_MENU, this));
        buttons.add(new ClickableButton(Display.SCREEN_WIDTH / 2, 135, "Quit", ClickableButton.SMALL_TEXT, Command.QUIT, this));
    }
    
    private int[] createBackgroundSprite() {
        int[] result = new int[10000];
        for (int i = 0; i < ToolBar.BORDER_SHADES; i++) {
            for (int y = 0; y < (100 - (4 * i)); y++) {
                for (int x = 0; x < (100 - (4 * i)); x++) {
                    result[(x + (2 * i)) + (y + (2 * i)) * 100] = ToolBar.BACKGROUNDCOLOR - ((4 - i) * 328960);
                }
            }
        }
        return result;
    }
    
    @Override
    public void doCommand(Command command) {
        switch (command) {
            case QUIT:
                System.exit(0);
                break;
            case MAIN_MENU:
                goToMainMenu = true;
                break;
            case RESUME:
                resumeGame = true;
                break;
            default:
                // Nothing. This should not be accessed.
                break;
        }
    }
    
    public void update() {
        if (goToMainMenu || resumeGame) {
            goToMainMenu = false;
            resumeGame = false;
        }
        for (ClickableButton but: buttons) {
            but.update();
        }
    }
    
    public void render(Screen screen) {
        screen.renderSprite(x - backgroundSprite.getWidth() / 2, y - backgroundSprite.getHeight() / 2, backgroundSprite);
        for (ClickableButton but: buttons) {
            but.render(screen);
        }
    }
    
}
