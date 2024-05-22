package cn.bitoffer.improve.controller;

import cn.bitoffer.improve.domain.form.ArticleAddForm;
import cn.bitoffer.improve.domain.form.ArticleQueryForm;
import cn.bitoffer.improve.domain.form.ArticleUpdateForm;
import cn.bitoffer.improve.domain.vo.ArticleVO;
import cn.bitoffer.improve.service.ArticleService;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 文章管理 Controller
 *
 * @Author 李祥生
 * @Date 2024-05-22 15:28:58
 * @Copyright niit-阿升
 */

@RestController
@Tag(name = "")
@RequestMapping("/blog")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Operation(summary = "分页查询 @author 李祥生")
    @PostMapping("/article/queryPage")
    public ResponseDTO<PageResult<ArticleVO>> queryPage(@RequestBody @Valid ArticleQueryForm queryForm) {
        return ResponseDTO.ok(articleService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 李祥生")
    @PostMapping("/article/add")
    public ResponseDTO<String> add(@RequestBody @Valid ArticleAddForm addForm) {
        return articleService.add(addForm);
    }

    @Operation(summary = "更新 @author 李祥生")
    @PostMapping("/article/update")
    public ResponseDTO<String> update(@RequestBody @Valid ArticleUpdateForm updateForm) {
        return articleService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 李祥生")
    @PostMapping("/article/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Integer> idList) {
        return articleService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 李祥生")
    @GetMapping("/article/delete/{articleId}")
    public ResponseDTO<String> batchDelete(@PathVariable Integer articleId) {
        return articleService.delete(articleId);
    }
}
