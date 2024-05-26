package cn.bitoffer.improve.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;
import org.dromara.easyes.annotation.*;
import org.dromara.easyes.annotation.rely.Analyzer;
import org.dromara.easyes.annotation.rely.FieldType;

/**
 * 文章管理 实体类
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Data
@TableName("article")
@IndexName("article")
public class ArticleEntity {

    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Integer articleId;


    @IndexId
    private String id;
    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章摘要
     */
    private String articleSummary;

    /**
     * 文章内容
     */
    @MultiIndexField(mainIndexField = @IndexField(fieldType = FieldType.KEYWORD),
            otherIndexFields = {@InnerIndexField(suffix = "zh", fieldType = FieldType.TEXT, analyzer = Analyzer.IK_SMART),
                    @InnerIndexField(suffix = "pinyin", fieldType = FieldType.TEXT, analyzer = Analyzer.PINYIN)})
    private String articleContent;

    /**
     * 文章浏览量
     */
    private Integer articleViewCount;

    /**
     * 文章点赞量
     */
    private Integer articleLikeCount;

    /**
     * 文章评论数量
     */
    private Integer articleCommentCount;

    /**
     * 创建时间
     */
    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    @IndexField(exist = false)
    private Integer isDeleted;

    /**
     * 外键，对应category_id
     */
    private Integer articleCategoryId;

    /**
     * 外键，对应user_id
     */
    private String articleUserId;

    /**
     * 分类表中对应category_name
     */
    private String articleCategoryName;

}