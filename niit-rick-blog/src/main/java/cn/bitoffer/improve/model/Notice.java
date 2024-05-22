package cn.bitoffer.improve.model;

import lombok.Data;

import java.util.Date;
@Data
public class Notice {

    private int id;

    /**
     * 发送方ID
     */
    private String fromId;

    /**
     * 接收方ID
     */
    private String toId;

    private String content;

    private Date createdDate;

    private int hasRead;

    /**
     * 这次对话的ID
     */
    private String conversationId;
}
