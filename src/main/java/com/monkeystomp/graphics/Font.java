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
    
    private char currentChar;
    private int index;
    
    private static SpriteSheet bigFont = new SpriteSheet("/fonts/big_font_sheet.png", 16);
    private static SpriteSheet smallFont = new SpriteSheet("/fonts/8x8_font_sheet.png", 8);
    private static SpriteSheet superSmallFont = new SpriteSheet("/fonts/5x5_font_sheet.png", 5);
    private static Sprite[] smallCharacters = Sprite.split(smallFont);
    private static Sprite[] superSmallCharacters = Sprite.split(superSmallFont);
    
    public static String bigCharactersIndex = "";
    public static String smallCharactersIndex = "!\"##%%\'()**,-./0123456789:;;==??ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    public static String superSmallCharactersIndex = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+=-";
    
    public Sprite[] returnSuperSmallCharacterSprites(String text) {
        Sprite[] sprites = new Sprite[text.length()];
        for (int i = 0; i < text.length(); i ++) {
            currentChar = text.charAt(i);
            index = superSmallCharactersIndex.indexOf(currentChar);
            sprites[i] = superSmallCharacters[index];
        }
        return sprites;
    }
    
    public void renderSuperSmallCharacters2(int x, int y, String text, Screen screen) {
        for (int i = 0; i < text.length(); i++) {
            currentChar = text.charAt(i);
            if (currentChar == ' ') {
                x -= 3;
            }
            else {
                index = superSmallCharactersIndex.indexOf(currentChar);
                screen.renderSprite(x + i * 6, y, superSmallCharacters[index]);
            }
        }
    }
    
    private Sprite[] returnSmallCharacterSprites(String text) {
        Sprite[] sprites = new Sprite[text.length()];
        for (int i = 0; i < text.length(); i++) {
            currentChar = text.charAt(i);
            index = smallCharactersIndex.indexOf(currentChar);
            sprites[i] = smallCharacters[index + 33];
        }
        return sprites;
    }
    
    public Sprite returnSmallCharacterSprite(String text, int color) {
        Sprite result;
        Sprite[] letters = returnSmallCharacterSprites(text);
        int spaces = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') spaces++;
        }
        int width = (text.length() * 9) - (spaces * 3);
        spaces = 0;
        int[] pixels = new int[width * 8];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xffff00ff;
        }
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                spaces++;
                i++;
            }
            for (int y = 0; y < 8; y++) {
                for (int x = i * 9 - (spaces * 3); x < (i * 9 - (spaces * 3)) + 8; x++) {
                    int xx = (x - (i * 9 - (spaces * 3)));
                    if (letters[i].getPixels()[xx + y * letters[i].getWidth()] != 0xffff00ff) {
                        //pixels[x + y * width] = letters[i].getPixels()[xx + y * letters[i].getWidth()];
                        pixels[x + y * width] = color;
                    }
                }
            }
        }
        result = new Sprite(0, 0, pixels, width, 8);
        return result;
    }
    
    public void renderSmallCharacters(int x, int y, String text, Screen screen) {
        for (int i = 0; i < text.length(); i++) {
            currentChar = text.charAt(i);
            if (currentChar == ' ') x -= 3;
            else {
                index = smallCharactersIndex.indexOf(currentChar);
                screen.renderSprite(x + i * 9, y, smallCharacters[index + 33]);
            }
        }
    }
}
