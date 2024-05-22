package cn.bitoffer.improve.service;

import cn.bitoffer.improve.dao.ArticleDao;
import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.domain.form.ArticleAddForm;
import cn.bitoffer.improve.domain.form.ArticleQueryForm;
import cn.bitoffer.improve.domain.form.ArticleUpdateForm;
import cn.bitoffer.improve.domain.vo.ArticleVO;
import java.util.List;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章管理 Service
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@Service
public class ArticleService {

    @Resource
    private ArticleDao articleDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<ArticleVO> queryPage(ArticleQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ArticleVO> list = articleDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ArticleAddForm addForm) {
        ArticleEntity articleEntity = SmartBeanUtil.copy(addForm, ArticleEntity.class);
        articleDao.insert(articleEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(ArticleUpdateForm updateForm) {
        ArticleEntity articleEntity = SmartBeanUtil.copy(updateForm, ArticleEntity.class);
        articleDao.updateById(articleEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        articleDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Integer articleId) {
        if (null == articleId){
            return ResponseDTO.ok();
        }

        articleDao.deleteById(articleId);
        return ResponseDTO.ok();
    }
}
