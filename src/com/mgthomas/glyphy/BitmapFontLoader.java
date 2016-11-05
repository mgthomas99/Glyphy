package com.mgthomas.glyphy; 

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Static utility class for loading and parsing bitmap fonts.
 * <p>
 * 
 * @see java.awt.Image
 * @see java.io.File
 */
public final class BitmapFontLoader {
    
    /**
     * Returns a new {@link com.mgthomas.glyphy.BitmapFont} generated
     * from the bitmap found in the specified file.
     * <p>
     * The bitmap read from the file is split into sub images of height
     * <i>glyphHeight</i> and width <i>glyphWidth</i>. The sub images
     * are then translated into {@code Glyph}s and a new {@code BitmapFont}
     * is returned.
     * <p>
     * 
     * @param   name
     *          The name to append to the created {@code BitmapFont}.
     * @param   file
     *          The file containing the bitmap image.
     * @param   glyphWidth
     *          The width, in pixels, of every {@code Glyph}.
     * @param   glyphHeight
     *          The height, in pixels, of every {@code Glyph}.
     * @return  A new {@code BitmapFont} from the specified parameters.
     */
    public static BitmapFont loadBitmapFont(String name, File file, int glyphWidth, int glyphHeight) {
        BufferedImage bmp = (BufferedImage) loadBitmap(file);
        Glyph[] glyphs = parseGlyphs(bmp, glyphWidth, glyphHeight);
        
        return new BitmapFont(name, glyphs);
    }
    
    /**
     * Reads an image from the specified file.
     * <p>
     * An {@link java.io.IOException} may be thrown during the
     * process of reading the file. All error handling is done
     * within this method. Should an exception occur, its stack
     * trace is printed to the standard error stream.
     * <p>
     * 
     * @param   file
     *          The file containing the image.
     * @return  The {@code Image} extracted from the file.
     */
    public static Image loadBitmap(File file) {
        Image bmp = null;
        
        try {
            bmp = ImageIO.read(file);
        } catch (final IOException ex_io) {
            ex_io.printStackTrace();
        }
        
        return bmp;
    }
    
    /**
     * Parses an {@code Image} into a series of {@code Glyph}s based
     * on the size parameters specified.
     * <p>
     * The {@code Image} is split into sub images of height
     * <i>glyphHeight</i> and width <i>glyphWidth</i>. The sub images
     * are then translated into {@code Glyph}s and returned.
     * <p>
     * 
     * @param   bmp
     *          The {@code Image} to parse.
     * @param   glyphWidth
     *          The width, in pixels, of every {@code Glyph}.
     * @param   glyphHeight
     *          The height, in pixels, of every {@code Glyph}.
     * @return  The {@code Glyph}s extracted from the specified
     *          {@code Image}.
     */
    public static Glyph[] parseGlyphs(Image bmp, int glyphWidth, int glyphHeight) {
        BufferedImage img = (BufferedImage) bmp;
        int glyphCountX = img.getWidth(null) / glyphWidth;
        int glyphCountY = img.getHeight(null) / glyphHeight;
        
        Glyph[] glyphs = new Glyph[glyphCountX * glyphCountY + 1];
        glyphs[0] = Glyph.NONE.resize(glyphWidth, glyphHeight);
        
        for (int y = 0; y < glyphCountY; y++) {
            for (int x = 0; x < glyphCountX; x++) {
                int arrIndex = (y * glyphCountX) + x + 1;
                glyphs[arrIndex] = new Glyph(img.getSubimage(x * glyphWidth, y * glyphHeight, glyphWidth, glyphHeight));
            }
        }
        
        return glyphs;
    }
    
    // Do not allow the instantiation of this class!
    // This class is strictly a static utility class.
    private BitmapFontLoader() {}
    
} 