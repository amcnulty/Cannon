/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.controls.Mouse;
import com.monkeystomp.entity.building.Brick5Story;
import com.monkeystomp.entity.building.Building;
import com.monkeystomp.entity.mob.projectiles.Projectile;
import com.monkeystomp.entity.particle.Particle;
import com.monkeystomp.graphics.Display;
import com.monkeystomp.graphics.Screen;
import com.monkeystomp.graphics.Sprite;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author Aaron
 */
public class GrassLevel extends Level {
    
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Building> buildings = new ArrayList<>();
    
    public GrassLevel(String path) {
        super(path);
        addBuildings();
    }
    
    protected void loadLevel(String path) {
        try {
            levelBackgroundImage = ImageIO.read(Level.class.getResource(path));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Level could not load background image!");
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Level.class.getResource("/audio/songs/Schubert_Military_March.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(ais);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Level could not load background music!");
        }
    }
    
    protected void generateLevel() {
        int[] pixels = new int[levelBackgroundImage.getWidth() * levelBackgroundImage.getHeight()];
        levelBackgroundImage.getRGB(0, 0, levelBackgroundImage.getWidth(), levelBackgroundImage.getHeight(), pixels, 0, levelBackgroundImage.getWidth());
        // randomize the grass pixels
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == 0xff446E0F) pixels[i] += (0x000100 * Math.abs(random.nextInt(15)));
        }
        // randomize the stump pixels
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == 0xff3F372A) pixels[i] += (0x000100 * random.nextInt(10)) + (0x010000 * random.nextInt(20));
        }
        levelBackgroundSprite = new Sprite(0, 50, pixels, levelBackgroundImage.getWidth(), levelBackgroundImage.getHeight());
        //backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Adjusts the mouse position to the current SCALE.
     */
    private void setMousePossition() {
        mouseX = Mouse.getMouseX() / Display.SCALE;
        mouseY = Mouse.getMouseY() / Display.SCALE;
    }
    
    private void addBuildings() {
        buildings.add(new Brick5Story(320, 180));
        
        for (Building build: buildings) {
            build.init(this);
        }
    }
    
    @Override
    public void addProjectile(int x, int y) {
        projectiles.add(Projectile.getProjectile(cannon.loadedProjectile));
        projectiles.get(projectiles.size() - 1).setPosition(x, y);
        projectiles.get(projectiles.size() - 1).setTrajectory(mouseX, mouseY, getForce(), cannon.angle);
        projectiles.get(projectiles.size() - 1).init(this);
    }
    
    @Override
    public void addParticle(Particle particle) {
        particles.add(particle);
    }
    
    private double getForce() {
        double force;
        double x = mouseX - cannon.barrelX;
        double y = mouseY - cannon.barrelY;
        double top = 16 * Math.pow(x, 2);
        double bottom = Math.pow(Math.cos(Math.toRadians(cannon.angle)), 2) * (y + (x * Math.tan(Math.toRadians(cannon.angle))));
        force = Math.sqrt(top / bottom);
        //force = Math.sqrt( (16 * Math.pow((mouseX - cannon.barrelX), 2.0)) / (Math.pow(Math.cos(Math.toRadians(cannon.angle)), 2.0) * ((mouseY + cannon.barrelY) + ((mouseX - cannon.barrelX) * Math.tan(Math.toDegrees(cannon.angle))))) );
        return force;
    }
    
    private boolean feildIsRightClicked() {
        return Mouse.getMouseB() == 3 && mouseX > 100 && mouseY > 164;
    }
    
    public boolean buildingHere(int x, int y) {
        for (Building build: buildings) {
            return build.buildingHere(x, y);
        }
        return false;
    }
    //private int anim = 0;
    @Override
    public void update() {
//        if (anim > 10000) anim = 0;
//        else anim++;
        setMousePossition();
        if (feildIsRightClicked()) {
            renderClicks = true;
            cannon.requestFireCannon();
        }
        else renderClicks = false;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).isRemoved()) buildings.remove(i);
            else buildings.get(i).update();
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved()) projectiles.remove(i);
            else projectiles.get(i).update();
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isRemoved()) particles.remove(i);
            else particles.get(i).update();
        }
    }
    
    @Override
    public void render(Screen screen) {
        screen.renderSprite(0, 50, levelBackgroundSprite);
        if (renderClicks) screen.renderSprite(mouseX - (Sprite.basic_ground_click.getWidth() / 2), mouseY - (Sprite.basic_ground_click.getHeight() / 2), Sprite.basic_ground_click);
        for (Building build: buildings) {
            build.render(screen);
        }
        for (Projectile pro: projectiles) {
            pro.render(screen);
        }
        for (Particle par: particles) {
            par.render(screen);
        }
    }
    
}
