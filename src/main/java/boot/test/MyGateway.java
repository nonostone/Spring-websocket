package boot.test;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MyGateway {
    void sendToMqtt(String data);
//    void sendMessage2Mqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
//    void sendMessage2Mqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

}
