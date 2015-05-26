/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp;

import com.monkeystomp.graphics.Display;
import javax.swing.JFrame;

/**
 *
 * @author Aaron
 */
public class Main{
    public static void main(String[] args) {
        Display display = new Display();
        display.frame.setResizable(false);
        display.frame.setTitle(Display.title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setVisible(true);
        display.requestFocus();
        
        display.start();
    }
    
}
