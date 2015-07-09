/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.menus;

import com.monkeystomp.menus.buttons.ClickableButton;
import com.monkeystomp.menus.commands.Command;
import java.util.ArrayList;

/**
 *
 * @author Aaron
 */
public abstract class Menu {
    
    protected ArrayList<ClickableButton> buttons = new ArrayList<>();
    
    public void doCommand(Command command) {
    }
    
}
