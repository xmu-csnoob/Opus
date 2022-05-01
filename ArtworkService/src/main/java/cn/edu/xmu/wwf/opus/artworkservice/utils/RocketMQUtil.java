package cn.edu.xmu.wwf.opus.artworkservice.utils;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class RocketMQUtil {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    public void sendMessage(String topic,String msg){
        rocketMQTemplate.sendOneWay(topic, msg);
    }
}