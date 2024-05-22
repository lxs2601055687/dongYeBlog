package cn.bitoffer.improve.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;


import java.util.Date;


@Data

public class Article {


    @Id

    private int articleId;


    private String articleTitle;


    private String articleSummary;


    private String articleContent;


    private int articleViewCount;


    private int articleLikeCount;


    private int articleCommentCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")

    private Date createdTime;

    /**
     *
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * isDelete表示两种状态，0表示未删除，1表示已删除
     */
    private int isDeleted;

    private int articleCategoryId;


    private String articleCategoryName;

    private String articleUserId;



}
