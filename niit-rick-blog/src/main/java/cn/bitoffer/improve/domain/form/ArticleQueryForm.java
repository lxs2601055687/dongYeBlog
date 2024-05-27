package cn.bitoffer.improve.domain.form;

import net.lab1024.sa.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

/**
 * 文章管理 分页查询表单
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Data
public class ArticleQueryForm extends PageParam{

    @Schema(description = "标题名")
    private String articleTitle;

    @Schema(description = "创建时间")
    private LocalDate createdTimeBegin;

    @Schema(description = "创建时间")
    private LocalDate createdTimeEnd;

    @Schema(description = "关键字")
    private String keyword;

}