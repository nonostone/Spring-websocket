package boot.spring.rabbitmq;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author tl
 * @description: TODO
 * @date 2024/9/11
 */
public class TestMqtt {
    public static void main(String[] args) throws MqttException {

        String clientId = "clientId-1234";
        String serverUrl = "tcp://10.110.224.142:1883"; // RabbitMQ的端口

        MqttClient client = new MqttClient(serverUrl, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("guest");
        char[] password = {'g','u','e','s','t'};
        options.setPassword(password);

        client.connect(options);

        // 订阅主题
        String topic = "your_topic";
        int qos = 2;
        client.subscribe(topic, qos);

        // 发布消息
        String message = "Hello MQTT!";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish(topic, mqttMessage);

        // 取消订阅
        client.unsubscribe(topic);

        // 断开连接
        client.disconnect();
    }
}
