package com.paic.jrkj.tk.tools.image;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;

public interface VerifyImage {

    String[] DEFAULT_FONT_TYPES = new String[]{
            "Times New Roman",
            "DialogInput",
            "Dialog"
    };

    int[] DEFAULT_FONT_STYLES = new int[]{0,1,2,3};

    void display(OutputStream os) throws IOException;

    String getVerifyCode();

    void setVerifyCode(String code);

    int width();

    int height();

    void setFontTypeArray(String[] fontTypes);

    void setFontStyleArray(int[] fontStyles);

    void setDefaultFontSize(int defaultFontSize);
}
