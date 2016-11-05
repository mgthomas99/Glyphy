package com.mgthomas.glyphy;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Immutable {@link java.awt.Image} wrapper specifically designed
 * for ease of use with the {@link com.mgthomas.glyphy.BitmapFont}
 * class.
 * <p>
 * 
 */
public final class Glyph
implements Serializable {
    
    /**
     * An empty, 1x1 {@code Glyph}.
     * <p>
     * This variable is not expected to be used outside of the
     * {@link com.mgthomas.glyphy.BitmapFontLoader}.
     */
    public static final Glyph NONE =
            new Glyph(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    
    private final Image img;
    
    /**
     * Creates a new {@code Glyph}.
     * <p>
     * 
     * @param   img
     *          The {@code Image} to wrap within this {@code Glyph}.
     */
    protected Glyph(Image img) {
        this.img = img;
    }
    
    /**
     * Returns a new {@code Glyph} whose internal {@code Image} is
     * a scaled version of this {@code Glyph}'s image. The new size
     * is equal to this {@code Glyph}'s size multiplied by the
     * <i>scale</i> argument.
     * <p>
     * 
     * @param   scale
     *          The scale to resize this {@code Glyph} by.
     * @return  The new, rescaled {@code Glyph}.
     */
    public Glyph rescale(float scale) {
        return resize(
                (int) Math.floor(getWidth() * scale),
                (int) Math.floor(getHeight() * scale)
            );
    }
    
    /**
     * Returns a new {@code Glyph} whose internal {@code Image} is
     * a scaled version of this Glyph's Image. In rescaling, the
     * {@link java.awt.Image#getScaledInstance} method is called.
     * Uses the default image scaling algorithm ({@link java.awt.Image#SCALE_DEFAULT}).
     * <p>
     * 
     * @param   width
     *          The desired width, in pixels, of the new {@code Glyph}.
     * @param   height
     *          The desired height, in pixels, of the new {@code Glyph}.
     * @return  A new, rescaled {@code Glyph}.
     */
    public Glyph resize(int width, int height) {
        return new Glyph(
                img.getScaledInstance(width, height, Image.SCALE_DEFAULT)
            );
    }
    
    /**
     * Returns the Java {@code Image} wrapped by this {@code Glyph}.
     * <p>
     * 
     * @return  The internal {@code Image}.
     */
    public Image getImage() {
        return img;
    }
    
    /**
     * Returns the height of this {@code Glyph} in pixels.
     * <p>
     * 
     * @return  The height, in pixels, of the {@code Image} internally
     *          stored by this {@code Glyph}.
     */
    public int getHeight() {
        return img.getHeight(null);
    }
    
    /**
     * Returns the width of this {@code Glyph} in pixels.
     * <p>
     * 
     * @return  The width, in pixels, of the {@code Image} internally
     *          stored by this {@code Glyph}.
     */
    public int getWidth() {
        return img.getWidth(null);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass().equals(getClass())) {
            Glyph cast = (Glyph) obj;
            return cast.img.equals(img);
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return img.hashCode();
    }
    
} 