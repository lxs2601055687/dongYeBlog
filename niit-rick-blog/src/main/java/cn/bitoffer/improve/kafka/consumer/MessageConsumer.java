package cn.bitoffer.improve.kafka.consumer;

import cn.bitoffer.improve.contast.KafkaTopicConstant;
import cn.bitoffer.improve.dao.ArticleDao;
import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.elsmapper.EsArticleMapper;
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
public class MessageConsumer {
    @Autowired
    private ArticleDao articleDao;

    @Resource
    private EsArticleMapper esArticleMapper;


    @KafkaListener(topics = KafkaTopicConstant.TOPIC_ADD, groupId = "ADD_GROUP",concurrency = "1", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void MysqlAdd(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            try {
                // 这里写你对接收到的消息的处理逻辑
                articleDao.insert((ArticleEntity) msg);
                esArticleMapper.insert((ArticleEntity) msg);
                // 手动ACK
                ack.acknowledge();
                log.info("数据同步添加成功! Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("数据添加失败！Topic:" + topic + ",Message:" + msg, e);
            }
        }
    }

    @KafkaListener(topics = KafkaTopicConstant.TOPIC_UPDATE, groupId = "UPDATE_GROUP",concurrency = "1", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void MysqlUpdate(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            try {
                // 这里写你对接收到的消息的处理逻辑
                articleDao.updateById((ArticleEntity) msg);
                // 手动ACK
                ack.acknowledge();
                log.info("数据更新成功! Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("数据更新失败！Topic:" + topic + ",Message:" + msg, e);
            }
        }
    }

    @KafkaListener(topics = KafkaTopicConstant.TOPIC_DELETE, groupId = "DELETE_GROUP",concurrency = "1", containerFactory = "kafkaManualAckListenerContainerFactory")
    public void MysqlDelete(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            try {
                // 这里写你对接收到的消息的处理逻辑
                articleDao.deleteById((ArticleEntity) msg);
                // 手动ACK
                ack.acknowledge();
                log.info("数据删除成功! Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("数据删除失败！Topic:" + topic + ",Message:" + msg, e);
            }
        }
    }
}
