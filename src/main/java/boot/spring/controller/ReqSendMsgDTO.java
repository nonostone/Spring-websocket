package boot.spring.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tl
 * @description: TODO
 * @date 2024/9/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqSendMsgDTO {
    public String topic;
    public String  payload;
}
