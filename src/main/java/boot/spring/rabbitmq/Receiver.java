package boot.spring.rabbitmq;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        // RabbitMQ 连接配置
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.110.225.44");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 获取 RabbitMQ 链接
        Connection connection = factory.newConnection();

        // 获取频道（Channel）
        com.rabbitmq.client.Channel channel = connection.createChannel();

        // 队列名称
        String queueName = "my_queue";

        // 声明队列
        channel.queueDeclare(queueName, true, false, false, null);

        // 回调函数
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException
            {
                String routingKey = envelope.getRoutingKey();
                String contentType = properties.getContentType();
                long deliveryTag = envelope.getDeliveryTag();
                // (process the message components here ...)
                String message = new String(body);
                System.out.println("Received: " + message);

                channel.basicAck(deliveryTag, false);
            }
        };
        channel.basicConsume(queueName, false, consumer);

    }
}