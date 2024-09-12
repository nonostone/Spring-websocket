package boot.spring.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

//import javax.annotation.Resource;

/**
 * mqtt 消息订阅配置
 *
 * @author
 */
@Slf4j
@Configuration
public class MqttSubscriberConfig {

    @Resource
//    @Autowired
    private MqttConfig mqttConfig;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        // clientId相同导致客户端间相互竞争消费
        // 同一时间内只能有一个客户端能拿到消息, 其他客户端不但不能消费消息，而且还在不断的掉线重连：Lost connection: 已断开连接; retrying...。
        MqttPahoMessageDrivenChannelAdapter adapter
                    = new MqttPahoMessageDrivenChannelAdapter(mqttConfig.getClientId() + MqttConstant.MESSAGE_HANDLER_CLIENT_ID_CONSUMER + RandomUtil.getRandomStr(),
                                                                    mqttConfig.mqttClientFactory(),
                                                                    mqttConfig.getDefaultTopic());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * 消息订阅, 处理收到的消息
     */
    @Bean
    @ServiceActivator(inputChannel = MqttConstant.CHANNEL_NAME_IN)
    public MessageHandler mqttInMessageHandler() {
        return message -> {
            try {
                // 消息体
                String payload = message.getPayload().toString();
                log.info("接收到消息, 内容: {}", payload);

                // byte[] bytes = (byte[]) message.getPayload(); // 收到的消息是字节格式
                // 消息的topic
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                log.info("接收到消息, topic: {}", topic);

                // 根据主题分别进行消息处理。
                if (topic.matches(".+/sensor")) { // 匹配：1/sensor
                    String sensorSn = topic.split("/")[0];
                    log.info("传感器" + sensorSn + ": 的消息： " + payload);
                } else if (topic.equals("collector")) {
                    log.info("采集器的消息：" + payload);
                } else {
                    log.info("丢弃消息：主题[" + topic  + "]，负载：" + payload);
                }
            } catch (MessagingException ex) {
                //logger.info(ex.getMessage());
            }
        };

    }
}
