package cn.bitoffer.improve.model;

import lombok.Data;


@Data
public class Message {

    int messageId;

    String fromId;

    String toId;

    String messageContent;

    int hasRead;

    String conversationId;


}
