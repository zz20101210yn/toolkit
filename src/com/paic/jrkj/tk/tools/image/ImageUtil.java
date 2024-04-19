package com.paic.jrkj.tk.tools.image;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageUtil {

    static public final String GIF_CONTENT_TYPE = "image/gif";
    static public final String JPEG_CONTENT_TYPE = "image/jpeg";
    static public final String BMP_CONTENT_TYPE = "image/bmp";

    static public final int IMAGE_TYPE_GIF = 1;
    static public final int IMAGE_TYPE_JPEG = 2;
    static public final int IMAGE_TYPE_BMP = 3;

    /**
     * display image,only supports gif,bmp,jpg jpeg!
     * the image type come from file postfix
     *
     * @param response javax.servlet.http.HttpServletResponse
     * @param file     realPath of image.it will throw AppException when is null or no postfix
     * @throws java.io.IOException occurs response.getOutputStream()
     */
    public static void display(HttpServletResponse response, String file)
            throws IOException {
        if (file == null || file.indexOf(".") == -1) {
            throw new NullPointerException("Unknow image type with file " + file);
        }
        String endFix = file.substring(file.indexOf("."));
        if (".gif".equalsIgnoreCase(endFix)) displayGif(response, file);
        else if (".jpg".equalsIgnoreCase(endFix) || ".jpeg".equalsIgnoreCase(endFix)) displayJpeg(response, file);
        else if (".bmp".equalsIgnoreCase(endFix)) displayBmp(response, file);
        else displayJpeg(response, file);
    }

    /**
     * display image,only supports gif,bmp,jpg jpeg!
     *
     * @param response  javax.servlet.http.HttpServletResponse
     * @param file      realPath of image.it will throw AppException when is null or no postfix
     * @param imageType int as gif:1,jpg/jpeg:2, bmp:3
     * @throws java.io.IOException occurs response.getOutputStream()
     */
    public static void display(HttpServletResponse response, String file, int imageType)
            throws IOException {
        if (imageType == IMAGE_TYPE_GIF) displayGif(response, file);
        else if (imageType == IMAGE_TYPE_JPEG) displayJpeg(response, file);
        else if (imageType == IMAGE_TYPE_BMP) displayBmp(response, file);
        else displayJpeg(response, file);
    }

    /**
     * display jpg and jpeg image
     *
     * @param response javax.servlet.http.HttpServletResponse
     * @param f        realPath of image.
     * @throws java.io.IOException occurs response.getOutputStream()
     */
    public static void displayJpeg(HttpServletResponse response, String f)
            throws IOException {
        File file = new File(f);
        if (!file.exists() || file.isDirectory()) {
            throw new NullPointerException("file " + f + " not found or is a directory! please check!");
        }
        BufferedImage image = ImageIO.read(file);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * display bmp image
     * have not test yet!
     *
     * @param response javax.servlet.http.HttpServletResponse
     * @param f        realPath of image.
     * @throws java.io.IOException occurs response.getOutputStream()
     */
    public static void displayBmp(HttpServletResponse response, String f)
            throws IOException {
        File file = new File(f);
        if (!file.exists() || file.isDirectory()) {
            throw new NullPointerException("file " + f + " not found or is a directory! please check!");
        }
        BufferedImage image = ImageIO.read(file);
        ImageIO.write(image, "BMP", response.getOutputStream());
    }

    /**
     * display gif image
     *
     * @param response javax.servlet.http.HttpServletResponse
     * @param f        realPath of image.
     * @throws java.io.IOException occurs response.getOutputStream()
     */
    public static void displayGif(HttpServletResponse response, String f)
            throws IOException {
        displayGif(response, f, 10, 100, 0);
    }

    /**
     * display gif image
     *
     * @param response javax.servlet.http.HttpServletResponse
     * @param f        realPath of image.
     * @param quality  int
     * @param delay    delay
     * @param repeat   repeat
     * @throws java.io.IOException occurs response.getOutputStream()
     */
    public static void displayGif(HttpServletResponse response, String f, int quality, int delay, int repeat)
            throws IOException {
        File file = new File(f);
        if (!file.exists() || file.isDirectory()) {
            throw new NullPointerException("file " + f + " not found or is a directory! please check!");
        }
        OutputStream os = response.getOutputStream();
        AnimatedGifEncoder agf = new AnimatedGifEncoder();
        BufferedImage frame;
        agf.setQuality(quality);
        agf.setDelay(delay);
        agf.setRepeat(repeat);
        agf.start(os);
        GifDecoder gd = new GifDecoder();
        gd.read(new FileInputStream(file));
        for (int i = 0; i < gd.getFrameCount(); i++) {
            frame = gd.getFrame(i);
            agf.addFrame(frame);
            frame.flush();
        }
        agf.finish();
        os.close();
    }

    public static String getCode(int size){
        char[] chararr = new char[size];
        Random random = new Random();
        for(int i=size;i>0;i--){
            chararr[i-1]= CODE_ARRAY[random.nextInt(CODE_ARRAY.length)];
        }
        return new String(chararr);
    }

    private final static char[] CODE_ARRAY= "ABCDEFGHKMNPQRSTUVWXYZabcdeghkmnpqrstuvwxyz23456789".toCharArray();
}