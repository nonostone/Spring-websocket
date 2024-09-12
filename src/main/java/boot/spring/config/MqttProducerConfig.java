package boot.spring.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

//import javax.annotation.Resource;

/**
 * mqtt 消息发布配置
 * @author
 */
@Configuration
public class MqttProducerConfig {

    @Resource
    private MqttConfig mqttConfig;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * ServiceActivator注解表明：当前方法用于处理MQTT消息，inputChannel参数指定了用于消费消息的channel。
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = MqttConstant.CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        // clientId相同导致客户端间相互竞争消费
        // 同一时间内只能有一个客户端能拿到消息, 其他客户端不但不能消费消息，而且还在不断的掉线重连：Lost connection: 已断开连接; retrying...。
        MqttPahoMessageHandler messageHandler
                = new MqttPahoMessageHandler(mqttConfig.getServerClientId() + MqttConstant.MESSAGE_HANDLER_CLIENT_ID_PRODUCER + RandomUtil.getRandomStr(),
                                                    mqttConfig.mqttClientFactory());
        messageHandler.setAsync(true);
        // MQTT 提供了三种服务质量（QoS），在不同网络环境下保证消息的可靠性。
        // QoS 0：消息最多传送一次。如果当前客户端不可用，它将丢失这条消息。
        // QoS 1：消息至少传送一次。
        // QoS 2：消息只传送一次。
        messageHandler.setDefaultQos(1);
        messageHandler.setDefaultTopic(mqttConfig.getDefaultTopic());

        return messageHandler;
    }
}
