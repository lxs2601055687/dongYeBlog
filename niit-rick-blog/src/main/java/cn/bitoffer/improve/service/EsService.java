package cn.bitoffer.improve.service;


import cn.bitoffer.improve.common.ResponseDTO;
import cn.bitoffer.improve.domain.entity.ArticleEntity;
import cn.bitoffer.improve.domain.form.ArticleQueryForm;
import cn.bitoffer.improve.domain.vo.ArticleVO;
import cn.bitoffer.improve.elsmapper.EsArticleMapper;
import net.lab1024.sa.base.common.domain.PageResult;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EsService {

    @Resource
    private EsArticleMapper esArticleMapper;



    public ResponseDTO<PageResult<ArticleVO>> queryPage(ArticleQueryForm articleQueryForm) {
        LambdaEsQueryWrapper<ArticleEntity> queryWrapper = new LambdaEsQueryWrapper<>();
        queryWrapper.like(ArticleEntity::getArticleContent,articleQueryForm.getKeyword()).limit(Math.toIntExact(articleQueryForm.getPageSize()), Math.toIntExact(articleQueryForm.getPageNum()));
        List<ArticleEntity> list = esArticleMapper.selectList(queryWrapper);
        Integer total = Math.toIntExact(esArticleMapper.selectCount(queryWrapper));
       /* EsPageInfo<ArticleEntity> articleEntityEsPageInfo = esArticleMapper.pageQuery(queryWrapper, Math.toIntExact(articleQueryForm.getPageNum()), Math.toIntExact(articleQueryForm.getPageSize()));*/
        List<ArticleVO> articleEntityList = new ArrayList<>();
       /* System.out.println(articleEntityList);
        List<ArticleEntity> list = articleEntityEsPageInfo.getList();*/
        list.forEach(articleEntity -> {
            ArticleVO build = ArticleVO.builder().articleId(articleEntity.getArticleId())
                    .articleContent(articleEntity.getArticleContent())
                    .articleCategoryName(articleEntity.getArticleCategoryName())
                    .articleTitle(articleEntity.getArticleTitle())
                    .articleViewCount(articleEntity.getArticleViewCount())
                    .articleCommentCount(articleEntity.getArticleCommentCount())
                    .articleViewCount(articleEntity.getArticleViewCount())
                    .createdTime(articleEntity.getCreatedTime())
                    .articleUserId(articleEntity.getArticleUserId())
                    .articleSummary(articleEntity.getArticleSummary())
                    .build();
            articleEntityList.add(build);
        });
        PageResult<ArticleVO> pageResult = new PageResult<>();
        pageResult.setList(articleEntityList);

        pageResult.setTotal(Long.valueOf(total));

        return ResponseDTO.ok(pageResult);
    }



}
