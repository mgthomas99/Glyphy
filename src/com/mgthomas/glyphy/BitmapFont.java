package com.mgthomas.glyphy; 

import java.awt.Graphics;
import java.io.Serializable;

/**
 * Immutable {@link com.mgthomas.glyphy.Glyph} collection with
 * useful methods for internal {@code Glyph} manipulation. The
 * expected method for creating a new {@code BitmapFont} is to
 * invoke {@link com.mgthomas.glyphy.BitmapFontLoader#loadBitmapFont}.
 * <p>
 * 
 * @see java.awt.Graphics
 */
public final class BitmapFont
implements Serializable {
    
    private final String name;
    
    private final Glyph[] glyphs;
    
    private int height;
    
    private int width;
    
    /**
     * Creates a new {@code BitmapFont} and appends the specified
     * name and {@code Glyph}s. The appended name is used solely
     * for font identification among font collections and is not
     * used within the {@link com.mgthomas.glyphy.BitmapFont#equals}
     * comparison.
     * <p>
     * 
     * @param   name
     *          The name of this {@code BitmapFont}.
     * @param   glyphs
     *          The {@code Glyph}s to append to this {@code BitmapFont}.
     */
    public BitmapFont(String name, Glyph[] glyphs) {
        this.name = name;
        this.glyphs = glyphs;
        
        height = glyphs[0].getHeight();
        width = glyphs[0].getWidth();
    }
    
    /**
     * Draws the string onto the {@link java.awt.Graphics} component at the
     * specified <i>x</i> and <i>y</i> coordinates using this
     * {@code BitmapFont}. Newlines and tabs are accounted for.
     * <p>
     * 
     * @param   str
     *          The string to draw.
     * @param   x
     *          The <i>x</i> coordinate to begin drawing at.
     * @param   y
     *          The <i>y</i> coordinate to begin drawing at.
     * @param   g
     *          The {@code Graphics} component to draw to.
     */
    public void drawString(String str, int x, int y, Graphics g) {
        final int sx = x;
        
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (chr == '\n') {
                x = sx;
                y += height;
            }
            else if (chr == '\t') {
                x += width * 2;
            }
            else {
                Glyph glyph = getGlyph(chr);
                g.drawImage(
                        glyph.getImage(),
                        x,
                        y,
                        null
                    );

                x += glyph.getWidth();
            }
        }
    }
    
    /**
     * Re-scales all of the {@code Glyph}s within this {@code BitmapFont}
     * to its current size multiplied by <i>scale</i>.
     * <p>
     * 
     * @param   scale 
     *          The scale to resize this {@code BitmapFont} to.
     */
    public void resize(float scale) {
        resize(
                (int) Math.ceil(width * scale),
                (int) Math.ceil(height * scale)
            );
    }
    
    /**
     * Re-scales all of the {@code Glyph}s within this {@code BitmapFont}
     * to the specified width and height.
     * <p>
     * 
     * @param   width
     *          The new width, in pixels, of every {@code Glyph}.
     * @param   height
     *          The new height, in pixels, of every {@code Glyph}.
     */
    public void resize(int width, int height) {
        for (int i = 0; i < glyphs.length; i++) {
            glyphs[i] = glyphs[i].resize(width, height);
        }
    }
    
    /**
     * Returns the {@code Glyph} representing the specified character.
     * <p>
     * 
     * @param   c
     *          The character to get from this {@code BitmapFont}.
     * @return  The {@code Glyph} representing the argument character.
     */
    public Glyph getGlyph(char c) {
        return getGlyph((int) c);
    }
    
    /**
     * Returns the {@code Glyph} representing the character of the
     * specified ASCII code.
     * <p>
     * 
     * @param   code
     *          The character code to get from this {@code BitmapFont}.
     * @return  The {@code Glyph} representing the character of the
     *          argument character code.
     */
    public Glyph getGlyph(int code) {
        return glyphs[code];
    }
    
    /**
     * Returns all of the {@code Glyphs} held within this {@code BitmapFont}
     * as an array.
     * <p>
     * 
     * @return  This {@code BitmapFont}'s {@code Glyph}s.
     */
    public Glyph[] getGlyphs() {
        return glyphs;
    }
    
    /**
     * Returns the height of every {@code Glyph} in this {@code BitmapFont},
     * in pixels.
     * <p>
     * 
     * @return  The height, in pixels, of this font's {@code Glyph}s.
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the name of this {@code BitmapFont}.
     * <p>
     * 
     * @return  This {@code BitmapFont}'s name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the width of every {@code Glyph} in this {@code BitmapFont},
     * in pixels.
     * <p>
     * 
     * @return  The width, in pixels, of this font's {@code Glyph}s.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * <b>Note:</b> When comparing this object to the argument, the
     * name parameter is not checked. For example, if the argument
     * is a {@code BitmapFont} with the same {@code Glyph}s but a
     * different name, the method will still return {@code true}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass().equals(getClass())) {
            BitmapFont cast = (BitmapFont) obj;
            if (cast.glyphs.length != glyphs.length) return false;
            for (int i = 0; i < glyphs.length; i++) {
                if (! glyphs[i].equals(cast.glyphs[i]))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }
    
} 