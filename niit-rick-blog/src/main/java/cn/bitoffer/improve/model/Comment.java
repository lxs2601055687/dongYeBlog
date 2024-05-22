package cn.bitoffer.improve.model;

import lombok.Data;

import java.util.Date;


@Data
public class Comment {

    private int commentId;

    private int commentArticleId;

    private String commentUserId;

    private String commentContent;

    private int commentLikeCount;

    private int commentCount;

    private Date commentCreatedTime;


}
