package cn.bitoffer.improve.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 文章管理 更新表单
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Data
public class ArticleUpdateForm {

    @Schema(description = "文章id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文章id 不能为空")
    private Integer articleId;

}