package boot.spring.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receiver3 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // RabbitMQ 连接配置
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.110.224.98");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 获取 RabbitMQ 链接
        Connection connection = factory.newConnection();

        // 获取频道（Channel）
        Channel channel = connection.createChannel();


        channel.basicQos(1);

        // 指定队列方式消费
        // 队列名称
         String queueName = "queue_tianlei";
        // 声明队列
//        channel.queueDeclare(queueName, true, false, false, null);
//
        // 随机队列消费
//        String queueName  = channel.queueDeclare().getQueue();
//        channel.queueBind(queueName,"test_direct","origin");
//        channel.queueBind(queueName,"test_direct","yellow");
        channel.queueBind(queueName,"logs","black.#");

        // 回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            String routingKey = delivery.getEnvelope().getRoutingKey();
            String contentType = delivery.getProperties().getContentType();
            long deliveryTag = delivery.getEnvelope().getDeliveryTag();
            System.out.println("routingKey: " + routingKey);
            System.out.println("contentType: " + contentType);
            System.out.println("deliveryTag: " + deliveryTag);
//            channel.basicAck(deliveryTag, false);
            try {
                doWork(message);
                if("wewewe".equalsIgnoreCase(message)){
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                channel.basicAck(deliveryTag,false);
            }


        };

//        DefaultConsumer consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag,
//                                       Envelope envelope,
//                                       AMQP.BasicProperties properties,
//                                       byte[] body)
//                    throws IOException
//            {
//                String routingKey = envelope.getRoutingKey();
//                String contentType = properties.getContentType();
//                long deliveryTag = envelope.getDeliveryTag();
//                // (process the message components here ...)
//                String message = new String(body);
//                System.out.println("Received: " + message  );
//                System.out.println("routingKey: " + routingKey);
//                System.out.println("contentType: " + contentType);
//                System.out.println("deliveryTag: " + deliveryTag);
//
//                channel.basicAck(deliveryTag, false);
//            }
//        };
        channel.basicConsume(queueName, false, deliverCallback,consumerTag -> {
            System.out.println("取消了 " + consumerTag);
        });

    }
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}