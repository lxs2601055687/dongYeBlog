package cn.bitoffer.improve.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.istack.internal.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import net.lab1024.sa.base.common.json.deserializer.DictValueVoDeserializer;

/**
 * 文章管理 新建表单
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Data
public class ArticleAddForm {

    @Schema(description = "文章id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer articleId;

    @Schema(description = "文章标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "文章标题 不能为空")
    private String articleTitle;

    @Schema(description = "文章内容")
    private String articleContent;

    @Schema(description = "文章浏览量")
    private Integer articleViewCount;

    @Schema(description = "文章点赞量")
    private Integer articleLikeCount;

    @Schema(description = "文章评论数量")
    private Integer articleCommentCount;

    @Schema(description = "外键，对应category_id")
    private Integer articleCategoryId;

    @Schema(description = "外键，对应user_id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String articleUserId;

    @Schema(description = "分类表中对应category_name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "分类表中对应category_name 不能为空")
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String articleCategoryName;

}