/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Aaron
 */
public class Keyboard implements KeyListener{
    
    private boolean[] keys = new boolean[200];
    public boolean escape, key1, key2, key3, key4, r, f, v;
    public boolean checked;
    
    public void update() {
        escape = keys[KeyEvent.VK_ESCAPE];
        key1 = keys[KeyEvent.VK_1];
        key2 = keys[KeyEvent.VK_2];
        key3 = keys[KeyEvent.VK_3];
        key4 = keys[KeyEvent.VK_4];
        r = keys[KeyEvent.VK_R];
        f = keys[KeyEvent.VK_F];
        v = keys[KeyEvent.VK_V];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        checked = false;
    }
    
}
