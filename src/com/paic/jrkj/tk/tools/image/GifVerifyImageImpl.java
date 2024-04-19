package com.paic.jrkj.tk.tools.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class GifVerifyImageImpl implements VerifyImage {

    private String[] fontTypes = DEFAULT_FONT_TYPES;
    private int[] fontStyles = DEFAULT_FONT_STYLES;
    private final int width;
    private final int height;
    private String code;
    private int defaultFontSize= 20;

    public GifVerifyImageImpl(int width) {
        this(25, width);
    }

    public GifVerifyImageImpl(String code) {
        this(25, code.length() * 18);
        this.code = code;
    }

    /**
     * @param height image height
     * @param width  image width
     */
    public GifVerifyImageImpl(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void display(OutputStream os)
            throws IOException {
        final int size = this.code.length();
        AnimatedGifEncoder agf = new AnimatedGifEncoder();
        agf.start(os);
        agf.setQuality(10);
        agf.setDelay(200);
        agf.setRepeat(0);
        Color bgcolor = getRandColor(200, 250);
        Color linecolor = getRandColor(180, 200);
        Color fontcolor[] = new Color[size];
        Font font[] = new Font[size];
        for (int i = 0; i < size; i++) {
            font[i] = getRandomFont();
            fontcolor[i] = getRandColor(20, 120);
        }
        BufferedImage frame;
        for (int i = 0; i < size; i++) {
            frame = getImage(bgcolor, linecolor, fontcolor, font, i);
            agf.addFrame(frame);
            frame.flush();
        }
        agf.finish();
    }

    public String getVerifyCode() {
        return this.code;
    }

    public void setVerifyCode(String code) {
        this.code = code;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public void setFontTypeArray(String[] fontTypes) {
        this.fontTypes = fontTypes;
    }

    public void setFontStyleArray(int[] fontStyles) {
        this.fontStyles = fontStyles;
    }

    public void setDefaultFontSize(int defaultFontSize){
        this.defaultFontSize = defaultFontSize;
    }

    private BufferedImage getImage(Color bgcolor, Color linecolor, Color[] fontcolor, Font[] font, int flag) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(bgcolor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(linecolor);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g2d.drawLine(x, y, x + xl, y + yl);
        }
        AlphaComposite ac3;
        final int length = code.length();
        for (int i = 0; i < length; i++) {
            g2d.setFont(font[i]);
            ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i, length * 2));
            g2d.setComposite(ac3);
            g2d.setColor(fontcolor[i]);
            g2d.drawString(code.substring(i, i + 1), 16 * i + 5 + random.nextInt(2), 16);
        }
        g2d.dispose();
        return image;
    }

    private Font getRandomFont() {
        Random random = new Random();
        return new Font(fontTypes[random.nextInt(fontTypes.length)], fontStyles[random.nextInt(fontStyles.length)], defaultFontSize + random.nextInt(6));
    }

    private float getAlpha(int i, int j, int codeLength) {
        return (i + j + 0f) / codeLength;
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        Random random = new Random();
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
