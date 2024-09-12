package boot.spring.controller;


import boot.spring.config.MqttGateway;
//import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;

/**
 *
 * mqtt发送消息控制器
 *
 * @author
 */
@RestController
@RequestMapping("/mqtt-push")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @PostMapping(value = "/sendMessage")
    public String sendMqtt(@RequestBody ReqSendMsgDTO reqSendMsgDTO) {
        mqttGateway.sendMessage2Mqtt(reqSendMsgDTO.getTopic(), reqSendMsgDTO.getPayload());
        return "SUCCESS";
    }
    @GetMapping("/sendMessage_2")
    public String sendMqtt() {
        mqttGateway.sendMessage2Mqtt("sensor/tian/temperature", "nihao ");
        return "SUCCESS";
    }
}
