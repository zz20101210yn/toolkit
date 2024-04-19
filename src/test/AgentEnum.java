package test;

/**
 * 企业微信应用的枚举�??
 * �?有应用必须在config-server做相应的配置�?
 * 因为没有办法通过api得到应用的secret,
 * 如果没有secret就无法生成相应的token�?
 * 也就没办法对该应用发送消�?
 */
public enum AgentEnum{
    MESSAGE_CENTER("message_center", "消息中心"),
    OPEN_TRADE("open_trade", "第三方交易应�?"),
    DATA_CENTER("data_center", "数据中心"),
    ASSISTANT_CENTER("assistant_center","云创hr小助�?"),
    CULTURE_CENTER("culture_center","永辉文化"),
    JT_YC_ALERT_ASSISTANT("jt_yc_alert_assistant", "云创业务信息推�??"),
    FINANCE_ALERT_ASSISTANT("finance_alert_assistant","财务消息推�??");

    private String value;
    private String description;

    AgentEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
