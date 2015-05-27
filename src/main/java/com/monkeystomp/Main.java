/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp;

import com.monkeystomp.graphics.Display;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author Aaron
 */
public class Main{
    public static void main(String[] args) {
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                cursor.setRGB(x, y, 0xff00ff);
            }
        }
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
        Display display = new Display();
        display.frame.setResizable(false);
        display.frame.setTitle(Display.title);
        display.frame.add(display);
        display.frame.getContentPane().setCursor(blank);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setVisible(true);
        display.requestFocus();
        
        display.start();
    }
    
}
