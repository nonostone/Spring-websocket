package boot.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.ExecutorSubscribableChannel;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MyGateway gateway = (MyGateway) context.getBean(MyGateway.class);
        gateway.sendToMqtt("ffff");
        gateway.sendToMqtt("expectedMessage 9898");
        gateway.sendToMqtt("expectedMessage 989890");
        gateway.sendToMqtt("expectedMessage 8888888");
        gateway.sendToMqtt("expectedMessage 0000000");
    }
}