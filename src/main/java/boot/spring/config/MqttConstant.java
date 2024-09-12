package boot.spring.config;

/**
 * mqtt常量类
 *
 * @author
 * @since 2024-04-27
 */
public class MqttConstant {

    /**
     * mqtt发布者信道名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";


    /**
     * mqtt接收信道名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInputChannel";

    /**
     * mqtt消息发布者, serverClientId的前缀
     */
    public static final String MESSAGE_HANDLER_CLIENT_ID_PRODUCER = "producer";

    /**
     * mqtt消息接收者, clientId的前缀
     */
    public static final String MESSAGE_HANDLER_CLIENT_ID_CONSUMER = "consumer";
}
