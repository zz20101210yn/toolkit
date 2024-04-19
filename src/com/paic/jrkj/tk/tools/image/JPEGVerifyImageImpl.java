package com.paic.jrkj.tk.tools.image;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class JPEGVerifyImageImpl implements VerifyImage {

    private String code;
    private final int width;
    private final int height;
    private String[] fontTypes = DEFAULT_FONT_TYPES;
    private int[] fontStyles = DEFAULT_FONT_STYLES;
    private int defaultFontSize = 26;
    private final Random random = new Random();

    public JPEGVerifyImageImpl(int width) {
        this(25, width);
    }

    public JPEGVerifyImageImpl(String code) {
        this(25, code.length() * 22);
        this.code = code;
    }

    /**
     * @param height image height
     * @param width  image width
     */
    public JPEGVerifyImageImpl(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public String getVerifyCode() {
        return this.code;
    }

    public void setVerifyCode(String code) {
        this.code = code;
    }

    public void display(OutputStream os)
            throws IOException {
        try {
            ImageIO.write(getImage(), "JPEG", os);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
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

    public void setDefaultFontSize(int defaultFontSize) {
        this.defaultFontSize = defaultFontSize;
    }

    private Font getRandomFont() {
        return new Font(fontTypes[random.nextInt(fontTypes.length)],
                fontStyles[random.nextInt(fontStyles.length)],
                defaultFontSize + random.nextInt(2));
    }

    protected final Color getLineColor() {
        return new Color(120 + random.nextInt(110), 120 + random.nextInt(110), 120 + random.nextInt(110));
    }

    private void addLine(Graphics g) {
        g.setColor(getLineColor());
        for (int i = 0; i < 80; i++) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);
            int xl = random.nextInt(30);
            g.drawLine(x, y, x + xl, y);
        }
        g.setColor(getLineColor());
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);
            int yl = random.nextInt(30);
            g.drawLine(x, y, x, y + yl);
        }
        g.setColor(getLineColor());
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);
            int xl = random.nextInt(30);
            int yl = random.nextInt(30);
            g.drawLine(x, y, x + xl, y + yl);
        }
        g.setColor(getLineColor());
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(this.width);
            int y = random.nextInt(this.height);
            int xl = random.nextInt(30);
            int yl = random.nextInt(30);
            g.drawLine(Math.max(0, x - xl), Math.max(0, y - yl), x, y);
        }
    }

    private void addFrontLine(Graphics g){
        int x = random.nextInt(this.width/3);
        int y = random.nextInt(this.height/3);
        for (int i=0;i<10;i++){
            int randomXL = random.nextInt(5)+5;
            int randomYL = 3-random.nextInt(5);
//            g.setColor(Color.white);
            g.drawLine(x, y, x + randomXL, y+randomYL);
            g.drawLine(x+1, y+1, x+randomXL, y+randomYL);
//            g.drawLine(x+2, y+2, x+randomXL, y+randomYL);
            x = x+randomXL;
            y = y+randomYL;
        }
    }

    /**
     * 正弦曲线Wave扭曲图片
     */
    private BufferedImage twistImage(BufferedImage buffImg) {
        double dMultValue = random.nextInt(7) + 3;// 波形的幅度倍数，越大扭曲的程序越高，一般为3
        double dPhase = random.nextInt(6);// 波形的起始相位，取值区间（0-2＊PI）

        BufferedImage destBi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < destBi.getWidth(); i++) {
            for (int j = 0; j < destBi.getHeight(); j++) {
                int nOldX = getXPosition4Twist(dPhase, dMultValue,
                        destBi.getHeight(), i, j);
                int nOldY = j;
                if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0
                        && nOldY < destBi.getHeight()) {
                    destBi.setRGB(nOldX, nOldY, buffImg.getRGB(i, j));
                }
            }
        }
        return destBi;
    }

    /**
     * 获取扭曲后的x轴位置
     */
    private int getXPosition4Twist(double dPhase, double dMultValue,
                                   int height, int xPosition, int yPosition) {
        //double PI = 3.1415926535897932384626433832799; // 此值越大，扭曲程度越大
        double PI = 1.1415926535897932384626433832799; // 此值越大，扭曲程度越大
        double dx = (PI * yPosition) / height + dPhase;
        double dy = Math.sin(dx);
        return xPosition + (int) (dy * dMultValue);
    }

    private BufferedImage getImage()
            throws Exception {
        BufferedImage _image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_USHORT_565_RGB);
        Graphics g = _image.getGraphics();
        g.setColor(new Color(200 + random.nextInt(50), 200 + random.nextInt(50), 200 + random.nextInt(50)));
        g.fillRect(0, 0, this.width, this.height);
//        addLine(g);
        if (this.code != null) {
            int currentPOS = 8;
            for (int i = 0; i < this.code.length(); i++) {
                g.setFont(getRandomFont());
                g.setColor(new Color(random.nextInt(30), random.nextInt(30), random.nextInt(30)));
                g.drawString(this.code.substring(i, i + 1), currentPOS, 20);
                currentPOS = currentPOS + 20;
                //字符位置有一定随机性
//                currentPOS = currentPOS + 19+random.nextInt(3);
            }
        }
        //添加前景干扰线
        addFrontLine(g);
        g.dispose();
        return twistImage(_image);
    }
}
