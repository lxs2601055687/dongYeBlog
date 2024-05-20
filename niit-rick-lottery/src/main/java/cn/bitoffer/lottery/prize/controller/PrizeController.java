package cn.bitoffer.lottery.prize.controller;

import cn.bitoffer.lottery.model.LotteryResult;
import cn.bitoffer.lottery.model.Result;
import cn.bitoffer.lottery.prize.domain.entity.PrizeEntity;
import cn.bitoffer.lottery.prize.domain.form.PrizeAddForm;
import cn.bitoffer.lottery.prize.domain.form.PrizeQueryForm;
import cn.bitoffer.lottery.prize.domain.form.PrizeUpdateForm;
import cn.bitoffer.lottery.prize.domain.vo.PrizeVO;
import cn.bitoffer.lottery.prize.service.PrizeService;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 奖品表 Controller
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@RestController
@Tag(name = "")
@RequestMapping("/lottery")
public class PrizeController {

    @Resource
    private PrizeService prizeService;

    @Operation(summary = "分页查询 @author 李祥生")
    @PostMapping("/prize/queryPage")
    public ResponseDTO<PageResult<PrizeVO>> queryPage(@RequestBody @Valid PrizeQueryForm queryForm) {
        return ResponseDTO.ok(prizeService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 李祥生")
    @PostMapping("/prize/add")
    public ResponseDTO<String> add(@RequestBody @Valid PrizeAddForm addForm) {
        return prizeService.add(addForm);
    }

    @Operation(summary = "更新 @author 李祥生")
    @PostMapping("/prize/update")
    public ResponseDTO<String> update(@RequestBody @Valid PrizeUpdateForm updateForm) {
        return prizeService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 李祥生")
    @PostMapping("/prize/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Integer> idList) {
        return prizeService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 李祥生")
    @GetMapping("/prize/delete/{id}")
    public ResponseDTO<String> batchDelete(@PathVariable Integer id) {
        return prizeService.delete(id);
    }

    @Operation(summary = "查询今天用户抽奖次数 @author 李祥生")
    @GetMapping("/prize/get_lucky_count/{userId}")
    public ResponseDTO<Integer> getLuckyCount(@PathVariable(value = "userId") Integer userId) {
        return prizeService.getLuckyCount(Long.valueOf(userId));
    }
    @Operation(summary = "查询用户获奖结果 @author 李祥生")
    @GetMapping("/prize/get_lucky_result/{userId}")
    public ResponseDTO<List<Result>> getLuckyResult(@PathVariable(value = "userId") Integer userId) {
        return prizeService.getLuckyResult(Long.valueOf(userId));
    }
    @Operation(summary = "查询全部奖品 @author 李祥生")
    @GetMapping("/prize/get_all_prize")
    public ResponseDTO<List<PrizeEntity>> getAllPrize() {
        return prizeService.getAllPrize();
    }
}
