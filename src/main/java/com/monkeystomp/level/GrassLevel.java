/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.level;

import com.monkeystomp.controls.Mouse;
import com.monkeystomp.entity.building.Brick5Story;
import com.monkeystomp.entity.building.Building;
import com.monkeystomp.entity.mob.Mob;
import com.monkeystomp.entity.projectiles.Projectile;
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
    private ArrayList<Building> backgroundBuildings = new ArrayList<>();
    private ArrayList<Building> foregroundBuildings = new ArrayList<>();
    private ArrayList<Mob> enemies = new ArrayList<>();
    
    public GrassLevel(String path) {
        super(path);
        addBuildings();
        setMobWaveTimer();
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
        // Add background buildings here.
        backgroundBuildings.add(new Brick5Story(220, 155));
        backgroundBuildings.add(new Brick5Story(280, 158));
        backgroundBuildings.add(new Brick5Story(320, 152));
        backgroundBuildings.add(new Brick5Story(360, 162));
        for (Building build: backgroundBuildings) {
            build.init(this);
        }
        // Add foreground buildings here.
        foregroundBuildings.add(new Brick5Story(282, 205));
        foregroundBuildings.add(new Brick5Story(310, 207));
        for (Building build: foregroundBuildings) {
            build.init(this);
        }
    }
    
    private void setMobWaveTimer() {
        nextWaveTimer = random.nextInt(120) + 240;
    }
    
    private void createWave() {
        if (random.nextBoolean()) {
            for (int i = 0; i < random.nextInt(2) + 3; i++) {
                enemies.add(new Mob(200 + random.nextInt(30), (220 + (10 * i)) + random.nextInt(10), Mob.POLICEMAN));
                for (int ii = 0; ii < 20; ii++) {
                    enemies.get(enemies.size() - 1).setDestination(190 + random.nextInt(50), 182 + random.nextInt(23));
                    enemies.get(enemies.size() - 1).setDestination(380 + random.nextInt(30), 182 + random.nextInt(23));
                }
                //enemies.get(enemies.size() - 1).setDestination(100, 192);
                enemies.get(enemies.size() - 1).init(this);
            }
        }
        else {
            for (int i = 0; i < random.nextInt(2) + 3; i++) {
                enemies.add(new Mob(400 + (18 * i) + random.nextInt(10), 180 + random.nextInt(25), Mob.POLICEMAN));
                for (int ii = 0; ii < 20; ii++) {
                    enemies.get(enemies.size() - 1).setDestination(190 + random.nextInt(50), 182 + random.nextInt(23));
                    enemies.get(enemies.size() - 1).setDestination(380 + random.nextInt(30), 182 + random.nextInt(23));
                }
                //enemies.get(enemies.size() - 1).setDestination(100, 192);
                enemies.get(enemies.size() - 1).init(this);
            }
        }
    }
    
    @Override
    public void addProjectile(int x, int y) {
        projectiles.add(Projectile.getProjectile(cannon.loadedProjectile));
        projectiles.get(projectiles.size() - 1).setPosition(x, y);
        projectiles.get(projectiles.size() - 1).setTrajectory(randomizeX(), randomizeY(), getForce(), cannon.angle);
        projectiles.get(projectiles.size() - 1).init(this);
    }
    
    private int randomizeX() {
        if (random.nextBoolean()) targetX = mouseX + (int)(cannon.getAccuracy() * random.nextDouble());
        else targetX = mouseX - (int)(cannon.getAccuracy() * random.nextDouble());
        return targetX;
    }
    
    private int randomizeY() {
        if (random.nextBoolean()) targetY = mouseY + (int)(cannon.getAccuracy() * random.nextDouble());
        else targetY = mouseY - (int)(cannon.getAccuracy() * random.nextDouble());
        return targetY;
    }
    
    @Override
    public void addParticle(Particle particle) {
        particles.add(particle);
        particles.get(particles.size() - 1).init(this);
    }
    
    private double getForce() {
        return Math.sqrt((16 * Math.pow((targetX - cannon.barrelX), 2)) / (Math.pow(Math.cos(Math.toRadians(cannon.angle)), 2) * ((targetY - cannon.barrelY) + (targetX - cannon.barrelX) * Math.tan(Math.toRadians(cannon.angle)))));
    }
    
    private boolean feildIsRightClicked() {
        return Mouse.getMouseB() == 3 && mouseX > 100 && mouseY > 164 && mouseX <= Display.SCREEN_WIDTH && mouseY <= Display.SCREEN_HEIGHT;
    }
    
    public boolean mobHere(int x, int y) {
        for (Mob mob: enemies) {
            if (mob.mobHere(x, y)) return true;
        }
        return false;
    }
    
    public boolean mobHere(int xa, int ya, int x, int y) {
        for (Mob mob: enemies) {
            if (mob.mobHere(xa, ya) && mob.x != x && mob.y != y) return true;
        }
        return false;
    }
    
    public boolean buildingHere(int x, int y) {
        for (Building build: backgroundBuildings) {
            if (build.buildingHere(x, y)) return true;
        }
        for (Building build: foregroundBuildings) {
            if (build.buildingHere(x, y)) return true;
        }
        return false;
    }
    
    @Override
    public void damageBuilding(int x, int y, int damage) {
        for (Building build: backgroundBuildings) {
            if (build.buildingHere(x, y)) {
                build.damageBuilding(damage);
            }
        }
        for (Building build: foregroundBuildings) {
            if (build.buildingHere(x, y)) {
                build.damageBuilding(damage);
            }
        }
    }
    
    @Override
    public boolean damageMob(int x, int y, int damage) {
        for (Mob mob: enemies) {
            if (mob.mobHere(x, y)) {
                mob.damageMob(damage);
                return true;
            }
        }
        return false;
    }
    
    private int anim = 0;
    @Override
    public void update() {
        if (anim > 10000) anim = 0;
        else anim++;
        setMousePossition();
        if (anim >= nextWaveTimer) {
            anim = 0;
            setMobWaveTimer();
            createWave();
        }
        if (feildIsRightClicked()) {
            renderClicks = true;
            cannon.requestFireCannon();
        }
        else renderClicks = false;
        for (int i = 0; i < backgroundBuildings.size(); i++) {
            if (backgroundBuildings.get(i).isRemoved()) backgroundBuildings.remove(i);
            else backgroundBuildings.get(i).update();
        }
        for (int i = 0; i < foregroundBuildings.size(); i++) {
            if (foregroundBuildings.get(i).isRemoved()) foregroundBuildings.remove(i);
            else foregroundBuildings.get(i).update();
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved()) projectiles.remove(i);
            else projectiles.get(i).update();
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isRemoved()) enemies.remove(i);
            else enemies.get(i).update();
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
        for (Building build: backgroundBuildings) {
            build.render(screen);
        }
        for (Projectile pro: projectiles) {
            pro.render(screen);
        }
        for (Mob mob: enemies) {
            mob.render(screen);
        }
        for (Building build: foregroundBuildings) {
            build.render(screen);
        }
        for (Particle par: particles) {
            par.render(screen);
        }
    }
    
}
