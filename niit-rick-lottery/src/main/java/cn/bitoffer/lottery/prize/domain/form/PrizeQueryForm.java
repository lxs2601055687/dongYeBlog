package cn.bitoffer.lottery.prize.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.lab1024.sa.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

/**
 * 奖品表 分页查询表单
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Data
public class PrizeQueryForm extends PageParam{

    @Schema(description = "奖品名称")
    @JsonProperty("title")
    private String title;

    @Schema(description = "奖品有效周期：开始时间")
    private Date beginTimeBegin;

    @Schema(description = "奖品有效周期：开始时间")

    private Date beginTimeEnd;

    @Schema(description = "奖品有效周期：结束时间")
    private Date endTimeBegin;

    @Schema(description = "奖品有效周期：结束时间")
    private Date endTimeEnd;


}