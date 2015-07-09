/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.menus.buttons;

import com.monkeystomp.controls.Mouse;
import com.monkeystomp.graphics.Display;
import com.monkeystomp.graphics.Font;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import com.monkeystomp.menus.Menu;
import com.monkeystomp.menus.commands.Command;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 *
 * @author Aaron
 */
public class ClickableButton {
    
    private final Font font = new Font();
    // Corrdinates of the button.
    private final int x, y;
    // Dimensions of the button.
    private int width, height;
    // Edges of the button.
    private int leftEdge, rightEdge, topEdge, bottomEdge;
    // The text displayed on the button.
    private final String text;
    // The clip object that plays the button click sound.
    private Clip clickSound;
    // Used to control the color of the text on the button.
    private boolean uptodate = false;
    private boolean lastTime = false;
    private boolean readyToClick = false;
    private boolean buttonHeldDown = false;
    // Style of the text.
    private int textStyle;
    // Use this to set the option to have a border on the button.
    public boolean showBorder = true;
    // The command this button is assigned.
    private final Command command;
    // The sprite for this button.
    private Sprite buttonSprite;
    // The sprite for the text.
    private Sprite buttonTextSprite;
    // Location of the mouse.
    private int mouseX, mouseY;
    
    // Color constants.
    public static final int DEFAULT_TEXT_COLOR = 0xffffff;
    public static final int MOUSE_OVER_TEXT_COLOR = 0xff6100;
    public static final int BUTTON_HELD_DOWN_COLOR = 0x0aff20;
    
    // Text style constants.
    public static final int SUPER_SMALL_TEXT = 0;
    public static final int SMALL_TEXT = 1;
    
    // Button Command Constants.
    public static final int START_GAME = 10;
    public static final int OPTIONS = 11;
    public static final int Quit = 12;
    
//    public enum Command {
//        START_GAME, OPTIONS, QUIT
//    }
    
    private Menu parent;
    
    public ClickableButton(int x, int y, String text, int textStyle, Command command, Menu parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.text = text;
        this.command = command;
        this.textStyle = textStyle;
        buttonTextSprite = font.returnSmallCharacterSprite(text, 0xffffff);
        createButtonSprite();
        leftEdge = x - (width / 2);
        rightEdge = leftEdge + width;
        topEdge = y - (height / 2);
        bottomEdge = topEdge + height;
    }
    
    private final void createButtonSprite() {
        width = buttonTextSprite.getWidth() + 10;
        switch (textStyle) {
            case SMALL_TEXT:
                height = 14;
                break;
            case SUPER_SMALL_TEXT:
                height = 11;
                break;
        }
        int[] pixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++) pixels[i] = 0x032aba;
        for (int x = 0; x < width; x++) {
            pixels[x] = 0;
            pixels[x + (height - 1) * width] = 0;
        }
        for (int y = 0; y < height; y++) {
            pixels[0 + y * width] = 0;
            pixels[(width - 1) + y * width] = 0;
        }
        buttonSprite = new Sprite(0, 0, pixels, width, height);
    }
    
    private void onClick() {
        Thread audioClipThread = new Thread ("Button Click") {
            public void run() {
                try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(ClickableButton.class.getResource("/audio/sfx/button_click.wav"));
                    clickSound = AudioSystem.getClip();
                    clickSound.open(ais);
                    ais.close();
                    clickSound.start();
                    clickSound.addLineListener((LineEvent e) -> {
                        if (e.getType() == LineEvent.Type.STOP) {
                            e.getLine().close();
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        audioClipThread.start();
        parent.doCommand(command);
    }
    
    private void setMousePosition() {
        mouseX = Mouse.getMouseX() / Display.SCALE;
        mouseY = Mouse.getMouseY() / Display.SCALE;
    }
    
    private boolean mouseOverButton() {
        return (mouseX > leftEdge && mouseX < rightEdge && mouseY > topEdge && mouseY < bottomEdge);
    }
    
    public void update() {
        setMousePosition();
        if (lastTime != mouseOverButton()) uptodate = false;
        lastTime = mouseOverButton();
        if (mouseOverButton() && !uptodate && Mouse.getMouseB() != 1) {
            for (int i = 0; i < buttonTextSprite.pixels.length; i++) {
                if (buttonTextSprite.pixels[i] != 0xffff00ff) buttonTextSprite.pixels[i] = MOUSE_OVER_TEXT_COLOR;
            }
            readyToClick = true;
            uptodate = true;
        }
        else if (!mouseOverButton() && !uptodate) {
            for (int i = 0; i < buttonTextSprite.pixels.length; i++) {
                if (buttonTextSprite.pixels[i] != 0xffff00ff) buttonTextSprite.pixels[i] = DEFAULT_TEXT_COLOR;
            }
            readyToClick = false;
            buttonHeldDown = false;
            uptodate = true;
        }
        else if (mouseOverButton() && readyToClick && Mouse.getMouseB() == 1) {
            buttonHeldDown = true;
            for (int i = 0; i < buttonTextSprite.pixels.length; i++) {
                if (buttonTextSprite.pixels[i] != 0xffff00ff) buttonTextSprite.pixels[i] = BUTTON_HELD_DOWN_COLOR;
            }
        }
        else if (mouseOverButton() && readyToClick && Mouse.getMouseB() != 1 && buttonHeldDown) {
            onClick();
            buttonHeldDown = false;
            uptodate = false;
        }
                    
    }
    
    public void render(Screen screen) {
        if (showBorder) screen.renderSprite(x - buttonSprite.getWidth() / 2, y - buttonSprite.getHeight() / 2, buttonSprite);
        screen.renderSprite(x - buttonTextSprite.getWidth() / 2, y - buttonTextSprite.getHeight() / 2, buttonTextSprite);
    }
}
