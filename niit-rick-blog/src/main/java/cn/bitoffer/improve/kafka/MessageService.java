package cn.bitoffer.improve.kafka;

import cn.bitoffer.improve.contast.KafkaTopicConstant;
import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.exception.BusinessException;
import cn.bitoffer.improve.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MessageService {

    private final KafkaTemplate<String, ArticleEntity> kafkaTemplate;
    @Autowired
    public MessageService(KafkaTemplate<String, ArticleEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    //发送Mysql写消息
    public void sendDataAddMessage(ArticleEntity article) {
        ListenableFuture<SendResult<String, ArticleEntity>> send = kafkaTemplate.send(KafkaTopicConstant.TOPIC_ADD, article);
        send.addCallback(success -> {
            log.info("消息发送成功-{}",article);
                }, fail -> {
                    throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR);
                }
        );
    };

    //发送ES写消息
    public void sendEsAddMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_ADD, article);
    }
    //发送Mysql更新消息
    public void sendMysqlUpdateMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_UPDATE, article);
    }
    //发送ES更新消息
    public void sendEsUpdateMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_UPDATE, article);
    }
    //发送Mysql删除消息
    public void sendMysqlDeleteMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_DELETE, article);
    }
    //发送ES删除消息
    public void sendEsDeleteMessage(ArticleEntity article){
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_DELETE, article);
    }


}
