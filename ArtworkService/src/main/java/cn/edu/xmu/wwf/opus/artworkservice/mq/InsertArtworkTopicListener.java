package cn.edu.xmu.wwf.opus.artworkservice.mq;

import cn.edu.xmu.wwf.opus.artworkservice.dao.ArtworkDao;
import cn.edu.xmu.wwf.opus.artworkservice.model.vo.ArtworkPostVo;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}",topic = "insert-artwork-topic")
public class InsertArtworkTopicListener implements RocketMQListener<String> {
    @Autowired
    ArtworkDao artworkDao;
    @Override
    public void onMessage(String s) {
        ArtworkPostVo artworkPostVo= JSON.parseObject(s,ArtworkPostVo.class);
        artworkDao.addArtworkIntoDB(artworkPostVo);
    }
}
