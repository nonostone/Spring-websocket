package boot.spring.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * rabbitmq mqtt协议网关接口
 */
@Component
@MessagingGateway(defaultRequestChannel = MqttConstant.CHANNEL_NAME_OUT)
public interface MqttGateway {

    void sendMessage2Mqtt(String data);

    void sendMessage2Mqtt(String data, @Header(MqttHeaders.TOPIC) String topic);

    void sendMessage2Mqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}
