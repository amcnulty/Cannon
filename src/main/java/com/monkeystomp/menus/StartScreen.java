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
import com.monkeystomp.level.Level;
import com.monkeystomp.menus.buttons.ClickableButton;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import com.monkeystomp.menus.commands.Command;

/**
 *
 * @author Aaron
 */
public class StartScreen extends Menu{
    
    private final Sprite background;
    public boolean startGame = false;
    public boolean options = false;
    private boolean musicPlaying = false;
    private Clip backgroundMusic;
    
    //private ArrayList<ClickableButton> buttons = new ArrayList<>();
    
    public StartScreen() {
        //background = new Sprite(0, 0, createBackgroundSprite(), Display.SCREEN_WIDTH, Display.SCREEN_HEIGHT);
        background = Sprite.start_screen_background;
        addButtons();
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Level.class.getResource("/audio/songs/civil_war_music.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(ais);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Level could not load background music!");
        }
        playMusic();
    }
    
    private int[] createBackgroundSprite() {
        int[] result = new int[(Display.SCREEN_WIDTH) * (Display.SCREEN_HEIGHT)];
        for (int i = 0; i < ToolBar.BORDER_SHADES; i++) {
            for (int y = 0; y < (Display.SCREEN_HEIGHT - (4 * i)); y++) {
                for (int x = 0; x < (Display.SCREEN_WIDTH - (4 * i)); x++) {
                    result[(x + (2 * i)) + (y + (2 * i)) * Display.SCREEN_WIDTH] = ToolBar.BACKGROUNDCOLOR - ((4 - i) * 328960);
                }
            }
        }
        return result;
    }
    
    private final void addButtons() {
        buttons.add(new ClickableButton(Display.SCREEN_WIDTH / 2, 100, "Start Game", ClickableButton.SMALL_TEXT, Command.START_GAME, this));
        buttons.add(new ClickableButton(Display.SCREEN_WIDTH / 2, 130, "Options", ClickableButton.SMALL_TEXT, Command.OPTIONS, this));
        buttons.add(new ClickableButton(Display.SCREEN_WIDTH / 2, 160, "Quit", ClickableButton.SMALL_TEXT, Command.QUIT, this));
        for (ClickableButton but: buttons) {
            but.showBorder = false;
        }
    }
    
    public void stopMusic() {
        musicPlaying = false;
        backgroundMusic.stop();
    }
    
    public void playMusic() {
        musicPlaying = true;
        backgroundMusic.setFramePosition(0);
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    @Override
    public void doCommand(Command command) {
        switch (command) {
            case START_GAME:
                stopMusic();
                startGame = true;
                break;
            case OPTIONS:
                options = true;
                break;
            case QUIT:
                System.exit(0);
        }
    }
    
    public void update() {
        if (startGame || options) {
            startGame = false;
            options = false;
        }
        if (!musicPlaying) playMusic();
        for (ClickableButton but: buttons) {
            but.update();
        }
    }
    
    public void render(Screen screen) {
        screen.renderSprite(0, 0, background);
        for (ClickableButton but: buttons) {
            but.render(screen);
        }
    }
    
}
