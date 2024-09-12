package boot.spring.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tl
 * @description: TODO
 * @date 2024/9/3
 */
public class RabbitmqProduct {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // RabbitMQ 连接配置
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.110.224.98");
        factory.setPort(5673);
//        factory.setUsername("guest");
//        factory.setPassword("guest");

        // 获取 RabbitMQ 链接
        Connection connection = factory.newConnection();

        // 获取频道（Channel）
        Channel channel = connection.createChannel();
        channel.confirmSelect();
        channel.exchangeDeclare("logs", "fanout",true);

        // 发送消息到队列中
        for (int i = 0; i < 5000; i++) {
            String message = "Hello, RabbitMQ! " + i ;
            channel.basicPublish("logs", "queue_tianlei", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            channel.waitForConfirmsOrDie(5_000);
            System.out.println("Sent: " + message);
        }

        // 关闭链接和频道
        channel.close();
        connection.close();
    }
}
