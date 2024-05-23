package cn.bitoffer.improve.kafka;

import cn.bitoffer.improve.contast.KafkaTopicConstant;
import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final KafkaTemplate<String, ArticleEntity> kafkaTemplate;
    @Autowired
    public MessageService(KafkaTemplate<String, ArticleEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    //发送Mysql写消息
    public void sendMysqlAddMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_MYSQL_ADD, article);
    }
    //发送ES写消息
    public void sendEsAddMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_ES_ADD, article);
    }
    //发送Mysql更新消息
    public void sendMysqlUpdateMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_MYSQL_UPDATE, article);
    }
    //发送ES更新消息
    public void sendEsUpdateMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_ES_UPDATE, article);
    }
    //发送Mysql删除消息
    public void sendMysqlDeleteMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_MYSQL_DELETE, article);
    }
    //发送ES删除消息
    public void sendEsDeleteMessage(ArticleEntity article) {
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_ES_DELETE, article);
    }


}
