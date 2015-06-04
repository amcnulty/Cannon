/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkeystomp.graphics;

/**
 *
 * @author Aaron
 */
public class Font {
    
    private static SpriteSheet superSmallFont = new SpriteSheet("/fonts/5x5_font_sheet.png", 5);
    private static Sprite[] superSmallCharacters = Sprite.split(superSmallFont);
    
    public static String superSmallCharactersIndex = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+=-";
    
    public Sprite[] returnCharacterSprites(String text) {
        Sprite[] sprites = new Sprite[text.length()];
        for (int i = 0; i < text.length(); i ++) {
            char currentChar = text.charAt(i);
            int index = superSmallCharactersIndex.indexOf(currentChar);
            sprites[i] = superSmallCharacters[index];
        }
        return sprites;
    }
    
    public void renderSuperSmallCharacters2(int x, int y, String text, Screen screen) {
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar == ' ') {
                x -= 3;
            }
            else {
                int index = superSmallCharactersIndex.indexOf(currentChar);
                screen.renderSprite(x + i * 6, y, superSmallCharacters[index]);
            }
        }
    }
}
