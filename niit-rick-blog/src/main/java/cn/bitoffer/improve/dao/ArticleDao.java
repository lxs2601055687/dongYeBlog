package cn.bitoffer.improve.dao;

import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.domain.form.ArticleQueryForm;
import cn.bitoffer.improve.domain.vo.ArticleVO;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 文章管理 Dao
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Mapper
@Component
public interface ArticleDao extends BaseMapper<ArticleEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ArticleVO> queryPage(Page page, @Param("queryForm") ArticleQueryForm queryForm);


}
