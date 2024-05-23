package cn.bitoffer.improve.kafka.consumer;

import cn.bitoffer.improve.contast.KafkaTopicConstant;
import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.elsmapper.EsArticleMapper;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
@Slf4j
public class EscrudConsumer {
    @Resource
    private EsArticleMapper esArticleMapper;


    @KafkaListener(topics = KafkaTopicConstant.TOPIC_ES_ADD, groupId = "ES_GROUP",concurrency = "1", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void MysqlAdd(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            try {
                // 这里写你对接收到的消息的处理逻辑
               esArticleMapper.insert((ArticleEntity) msg);
                // 手动ACK
                ack.acknowledge();
                log.info("ES数据添加成功! Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("ES数据添加失败！Topic:" + topic + ",Message:" + msg, e);
            }
        }
    }
}
