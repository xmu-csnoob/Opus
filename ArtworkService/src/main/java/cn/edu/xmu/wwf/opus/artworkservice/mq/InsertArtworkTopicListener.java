package cn.edu.xmu.wwf.opus.artworkservice.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}",topic = "insert-artwork-topic")
public class InsertArtworkTopicListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }
}
