package com.paic.jrkj.tk.tools.msg.field;

import java.nio.charset.Charset;

import com.paic.jrkj.tk.util.HexUtil;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 15:57:11 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 15:57:11
 */
public abstract class AbstractField implements Field {

    private final String name;
    private boolean logEnabled = true;
    private Charset charset;
    
    protected AbstractField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

	public void setCharset(String charset) {
		this.charset = Charset.forName(charset);
	}

	public Charset charset() {
        return this.charset;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\"").append(name).append("\":");
        if (charset == null) {
            builder.append("\"").append(HexUtil.encode(getData())).append("\"");
		} else {
            builder.append("\"").append(new String(getData(), charset)).append("\"");
		}
        return builder.toString();
    }

}
