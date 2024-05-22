package cn.bitoffer.improve.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文章管理 列表VO
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Data
public class ArticleVO {


    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "文章标题")
    private String articleTitle;

    @Schema(description = "文章摘要")
    private String articleSummary;

    @Schema(description = "文章内容")
    private String articleContent;

    @Schema(description = "文章浏览量")
    private Integer articleViewCount;

    @Schema(description = "文章点赞量")
    private Integer articleLikeCount;

    @Schema(description = "文章评论数量")
    private Integer articleCommentCount;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "外键，对应user_id")
    private String articleUserId;

    @Schema(description = "分类表中对应category_name")
    private String articleCategoryName;

}