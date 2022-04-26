package cn.edu.xmu.wwf.opus.imageservice.mq;

import cn.edu.xmu.wwf.opus.imageservice.dao.ImageDao;
import cn.edu.xmu.wwf.opus.imageservice.model.po.ImagePo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImagePostVo;
import cn.edu.xmu.wwf.opus.imageservice.model.vo.ImageRetVo;
import cn.edu.xmu.wwf.opus.imageservice.utils.RocketMQUtil;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}",topic = "insert-image-topic")
public class InsertImageTopicListener implements RocketMQListener<String> {
    @Autowired
    ImageDao imageDao;
    @Override
    public void onMessage(String str) {
        ImageRetVo imageRetVo= JSON.parseObject(str,ImageRetVo.class);
        imageDao.addImageToDB(imageRetVo);
    }
}
